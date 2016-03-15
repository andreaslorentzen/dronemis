package dronemis;

import dronemis.GUI.Listeners;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        new Controller().startSystem();

        BufferedImage image = ImageIO.read(new File("test.jpg"));
        Listeners.getInstance().updateImageFront(image);
        Listeners.getInstance().updateImageBottom(image);
    }

}
