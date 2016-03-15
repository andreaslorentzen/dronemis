package dronemis;

import dronemis.GUI.GUI;
import dronemis.GUI.Listeners;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by AL on 15-03-2016.
 */
public class Cascade {
    public static void main(String[] args){
        new TestHandler().init();
        new GUI();
        Listeners.getInstance().addUpdateFrontImageListener(new Listeners.UpdateImageListener() {
            public int imageI = 0;
            public int imageSaveRate = 30;

            @Override
            public void updateImage(BufferedImage image) {
                if (imageSaveRate > 0){
                    try {
                        if (imageI % imageSaveRate == 0){
                            String cwd = System.getProperty("user.dir");
                            File outputfile = new File(cwd + "/savedImages/cascade" + imageI/imageSaveRate + ".png");
                            ImageIO.write(image, "png", outputfile);
                        }
                        imageI++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
        while (true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
