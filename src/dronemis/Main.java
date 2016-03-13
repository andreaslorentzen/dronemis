package dronemis;

import dronemis.GUI.Listeners;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by AL on 02-03-2016.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        new Controller().startSystem();

        BufferedImage image = ImageIO.read(new File("test.jpg"));
        for (Listeners.UpdateImageListener listener : Listeners.getInstance().getUpdateFrontImageListener()) {
            listener.updateImage(image);
        }
        for (Listeners.UpdateImageListener listener : Listeners.getInstance().getUpdateBottomImageListener()) {
            listener.updateImage(image);
        }
    }
}
