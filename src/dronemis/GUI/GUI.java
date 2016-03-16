package dronemis.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class GUI {

    public static final double SCALE = 0.7;
    private JLabel filteredImage;
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
        Listeners.UpdateImageListener frontBottomImageLitener = new Listeners.UpdateImageListener() {
            @Override
            public void updateImage(BufferedImage image) {
                ImageIcon tempImage = new ImageIcon(image.getScaledInstance(640, 360, Image.SCALE_SMOOTH));
                frontImage.setIcon(tempImage);
            }
        };
        Listeners.getInstance().addUpdateFrontImageListener(frontBottomImageLitener);
        Listeners.getInstance().addUpdateBottomImageListener(frontBottomImageLitener);

        Listeners.getInstance().addImageListener("filtered", new Listeners.UpdateImageListener() {
            @Override
            public void updateImage(BufferedImage image) {
                ImageIcon tempImage = new ImageIcon(image.getScaledInstance(640, 360, Image.SCALE_SMOOTH));
                filteredImage.setIcon(tempImage);
            }
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
        c.fill = GridBagConstraints.HORIZONTAL;


        frontImage = createImageDisplayer();
        c.gridx = 0;
        c.gridy = 0;
        pane.add(frontImage, c);

        filteredImage = createImageDisplayer();
        c.gridx = 3;
        pane.add(filteredImage, c);

        textArea = new JTextArea(29, 62);
        textArea.setFocusable(false);
        c.gridwidth = 2;
        c.gridheight = 2;
        c.gridx = 1;
        c.gridy = 0;

        scrollPane = new JScrollPane (textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane.add(scrollPane, c);
        textArea.append("GUI START");


        JLabel label = createImageDisplayer();
        c.gridwidth = 1;
        c.gridheight = 2;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(label, c);
        label.setText("placeholder");

        label = createImageDisplayer();
        c.gridx = 3;
        pane.add(label, c);
        label.setText("placeholder2");


        textField = new JTextField();
        c.gridx = 1;
        c.gridy = 2;

        c.ipady = 10;
        c.gridwidth = 2;
        c.gridheight = 1;
        pane.add(textField, c);

        textField.addKeyListener(new  KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    String command = textField.getText();

                    if(command == null || command.equals(""))
                        return;

                    textField.setText("");

                    sendCommand(command);

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        JPanel panel2 = new JPanel();
        c.ipady = 0;
        c.gridx = 0;
        c.gridy = 3;
        c.gridheight = 1;
        c.gridwidth = 4;
        pane.add(panel2, c);

        panel2.setLayout(new GridBagLayout());
        panel2.setBackground(Color.black);

        JButton button = new JButton("Front Camera");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendCommand("frontCamera");
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
                sendCommand("bottomCamera");
            }
        });
        button.setFocusable(false);
        c.gridy = 1;
        panel2.add(button, c);



        button = new JButton("Keyboard");
        button.setFocusable(true);
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 2;
        panel2.add(button, c);
        button.addKeyListener(new  KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_W:
                        commandListener.doCommand("droneMoveForward");
                        break;
                    case KeyEvent.VK_A:
                        commandListener.doCommand("droneMoveLeft");
                        break;
                    case KeyEvent.VK_S:
                        commandListener.doCommand("droneMoveBackward");
                        break;
                    case KeyEvent.VK_D:
                        commandListener.doCommand("droneMoveRight");
                        break;

                    case KeyEvent.VK_LEFT:
                        commandListener.doCommand("droneTurnLeft");
                        break;
                    case KeyEvent.VK_RIGHT:
                        commandListener.doCommand("droneTurnRight");
                        break;
                    case KeyEvent.VK_UP:
                        commandListener.doCommand("droneMoveUp");
                        break;
                    case KeyEvent.VK_DOWN:
                        commandListener.doCommand("droneMoveDown");
                        break;

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
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

    private boolean sendCommand(String command){
        if(commandListener != null && commandListener.doCommand(command)){
            textArea.append("\n"+command);
            return true;
        }
        else{
            textArea.append("\nCommand '"+command+"' was not executed");
            return false;
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
