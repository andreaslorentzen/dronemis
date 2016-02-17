package dronemis.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by AL on 17-02-2016.
 */
public class GUI extends JFrame {
    private JPanel rootPanel;
    private JLabel frontImage;
    private JLabel bottomImage;

    public GUI() {
        super("openCV Control Center");

        setContentPane(rootPanel);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Listeners.getInstance().addUpdateFrontImageListener(image -> {

            ImageIcon tempImage = new ImageIcon(image.getScaledInstance(300, 300,Image.SCALE_SMOOTH));
            frontImage.setIcon(tempImage);

        });

        Listeners.getInstance().addUpdateBottomImageListener(image -> {

            ImageIcon tempImage = new ImageIcon(image.getScaledInstance(300, 300,Image.SCALE_SMOOTH));
            bottomImage.setIcon(tempImage);

        });

        setSize(600,600);

        setVisible(true);
    }

}
