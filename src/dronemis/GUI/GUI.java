package dronemis.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by AL on 17-02-2016.
 */
public class GUI extends JFrame {

    private JLabel bottomImage;
    private JLabel frontImage;
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;

    public GUI() {
        prepareGUI();

        Listeners.getInstance().addUpdateFrontImageListener(image -> {

            ImageIcon tempImage = new ImageIcon(image.getScaledInstance(300, 300, Image.SCALE_SMOOTH));
            frontImage.setIcon(tempImage);

        });

        Listeners.getInstance().addUpdateBottomImageListener(image -> {

            ImageIcon tempImage = new ImageIcon(image.getScaledInstance(300, 300, Image.SCALE_SMOOTH));
            bottomImage.setIcon(tempImage);

        });

    }

    public static void main(String[] args) throws IOException {
        GUI swingControlDemo = new GUI();
        //    swingControlDemo.showEventDemo();


        BufferedImage image = ImageIO.read(new File("test.jpg"));
        for (Listeners.UpdateImageListener listener : Listeners.getInstance().getUpdateFrontImageListener()) {
            listener.updateImage(image);
        }
        for (Listeners.UpdateImageListener listener : Listeners.getInstance().getUpdateBottomImageListener()) {
            listener.updateImage(image);
        }

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

        frontImage = createImageDisplayer();

        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;

        pane.add(frontImage, c);

        bottomImage = createImageDisplayer();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        pane.add(bottomImage, c);


        JLabel label = createImageDisplayer();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        pane.add(label, c);

        JButton button;

        button = new JButton("Long-Named Button 4");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 40; //make this component tall
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(button, c);

        button = new JButton("5");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0; //reset to default
        c.weighty = 1.0; //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(10, 0, 0, 0); //top padding
        c.gridx = 1; //aligned with button 2
        c.gridwidth = 2; //2 columns wide
        c.gridy = 2; //third row
        pane.add(button, c);
    }

    private void showEventDemo() {
        JButton okButton = new JButton("OK");
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        okButton.setActionCommand("OK");
        submitButton.setActionCommand("Submit");
        cancelButton.setActionCommand("Cancel");

        okButton.addActionListener(new ButtonClickListener());
        submitButton.addActionListener(new ButtonClickListener());
        cancelButton.addActionListener(new ButtonClickListener());

        controlPanel.add(okButton);
        controlPanel.add(submitButton);
        controlPanel.add(cancelButton);

        mainFrame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("OK")) {
                statusLabel.setText("Ok Button clicked.");
            } else if (command.equals("Submit")) {
                statusLabel.setText("Submit Button clicked.");
            } else {
                statusLabel.setText("Cancel Button clicked.");
            }
        }
    }

    public JLabel createImageDisplayer(){
        JLabel button = new JLabel();
        button.setPreferredSize(new Dimension(300, 300));
        return button;
    }
}