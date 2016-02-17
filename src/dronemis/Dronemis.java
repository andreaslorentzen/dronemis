/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dronemis;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author AL
 */
public class Dronemis {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        GUI gui = new GUI();
        BufferedImage image = ImageIO.read(new File("test.jpg"));
        for (Listeners.UpdateImageListener listener : Listeners.getInstance().getUpdateFrontImageListener()){
            listener.updateImage(image);
        }
        for (Listeners.UpdateImageListener listener : Listeners.getInstance().getUpdateBottomImageListener()){
            listener.updateImage(image);
        }
    }
    
}
