package dronemis.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by AL on 17-02-2016.
 */
public class GUI implements ActionListener {

    public static final double SCALE = 0.7;
    private JLabel bottomImage;
    private JLabel frontImage;
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private Keyboard keyboard;

    private JTextField textField;
    private JButton commandButton;
    private JTextArea textArea;
    private JScrollPane scrollPane;

    public GUI() {
        prepareGUI();
        keyboard = new Keyboard();
        mainFrame.addKeyListener(new KeyListener() {

            private int KEY_COUNT = 256;
            public boolean[] keys = null;

            @Override
            public synchronized void keyTyped(KeyEvent e) {
            }

            @Override
            public synchronized void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode >= 0 && keyCode < KEY_COUNT) {
                    keys[keyCode] = true;
                    System.out.println("keyPressed: " + keyCode + " " + keys[keyCode]);
                }
            }

            @Override
            public synchronized void keyReleased(KeyEvent e) {

                int keyCode = e.getKeyCode();
                if (keyCode >= 0 && keyCode < KEY_COUNT) {
                    keys[keyCode] = false;
                    System.out.println("keyReleased: " + keyCode + " " + keys[keyCode]);
                }
            }
        });
        Listeners.getInstance().addUpdateFrontImageListener(image -> {

            ImageIcon tempImage = new ImageIcon(image.getScaledInstance(640, 360, Image.SCALE_SMOOTH));
            frontImage.setIcon(tempImage);

        });

        Listeners.getInstance().addUpdateBottomImageListener(image -> {

            ImageIcon tempImage = new ImageIcon(image.getScaledInstance(640, 360, Image.SCALE_SMOOTH));
            bottomImage.setIcon(tempImage);

        });

    }

    private void prepareGUI() {
        mainFrame = new JFrame("Java SWING Examples");
        mainFrame.setResizable(false);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {

                System.exit(0);
            }
        });

        addComponentsToPane(mainFrame.getContentPane());

        mainFrame.pack();
        mainFrame.setVisible(true);

    }

    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;

    public void addComponentsToPane(Container pane) {

        pane.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        // First Grid Row
        frontImage = createImageDisplayer();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(frontImage, c);

        bottomImage = createImageDisplayer();
        c.gridx = 1;
        pane.add(bottomImage, c);

        JLabel label = createImageDisplayer();
        c.gridx = 2;
        pane.add(label, c);
        label.setText("placeholder");



        // 2. grid row
        textArea = new JTextArea(30, 30);
        textArea.setFocusable(false);
        c.ipady = 0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;

        scrollPane = new JScrollPane (textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane.add(scrollPane, c);

        textArea.append("GUI START");



        // 3. grid row
        textField = new JTextField();
        c.ipady = 10;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 2;
        pane.add(textField, c);

        commandButton = new JButton("Do command");
        commandButton.setFocusable(false);
        c.ipady = 5;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 2;
        pane.add(commandButton, c);

        commandButton.addActionListener(this);

        JPanel panel2 = new JPanel();
        c.ipady = 1;
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 1;
        pane.add(panel2, c);
        panel2.setLayout(new GridBagLayout());

        JButton button = new JButton("Front Camera");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(commandListener != null && commandListener.doCommand("frontCamera")){

                }
            }
        });
        button.setFocusable(false);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        panel2.add(button, c);

        button = new JButton("Bottom Camera");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(commandListener != null && commandListener.doCommand("bottomCamera")){

                }
            }
        });
        button.setFocusable(false);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        panel2.add(button, c);
/*


        button = new JButton("5");
        button.setFocusable(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0; //reset to default
        c.weighty = 1.0; //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(10, 0, 0, 0); //top padding
        c.gridx = 0; //aligned with button 2
        c.gridwidth = 1; //2 columns wide
        c.gridy = 2; //third row
        pane.add(button, c);

        button = new JButton("5");
        button.setFocusable(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0; //reset to default
        c.weighty = 1.0; //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(10, 0, 0, 0); //top padding
        c.gridx = 1; //aligned with button 2
        c.gridwidth = 1; //2 columns wide
        c.gridy = 2; //third row
        pane.add(button, c);
        */
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == commandButton){

            String command = textField.getText();

            if(command == null || command.equals(""))
                return;

            textField.setText("");

            if(commandListener != null && commandListener.doCommand(command)){
                textArea.append("\n"+command);

                JScrollBar vertical = scrollPane.getVerticalScrollBar();
                vertical.setValue( vertical.getMaximum() );
            }
            else{
                textArea.append("\nCommand '"+command+"' was not executed");
            }
        }
    }

    public JLabel createImageDisplayer() {
        JLabel button = new JLabel();
        button.setPreferredSize(new Dimension((int)(640*SCALE), (int)(360*SCALE)));
        return button;
    }

    private CommandListener commandListener;

    public void setCommandListener(CommandListener commandListener) {
        this.commandListener = commandListener;
    }

    public interface CommandListener {
        boolean doCommand(String command);
    }
}
