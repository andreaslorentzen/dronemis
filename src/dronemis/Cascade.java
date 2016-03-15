package dronemis;

import dronemis.GUI.GUI;
import dronemis.GUI.Listeners;
import dronemis.openCV.ImageProcessor;
import dronemis.openCV.OpenCVHelper;
import org.opencv.core.Mat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


/**
 * Created by AL on 15-03-2016.
 */
public class Cascade {

    static private final String cwd = System.getProperty("user.dir");

    public static void main(String[] args) throws InterruptedException {

        ImageProcessor imageProcessor = new ImageProcessor();
        OpenCVHelper cv = new OpenCVHelper();
        //new TestHandler().init();
        new TestHandler().init();
        new GUI();
        //Listeners.getInstance().addUpdateBottomImageListener(new Listeners.UpdateImageListener() {
        Listeners.getInstance().addUpdateFrontImageListener(new Listeners.UpdateImageListener() {
            public int imageI = 0;
            public int imageSaveRate = 30;

            @Override
            public void updateImage(BufferedImage image) {
                Mat imageMat = imageProcessor.toMatImage(image);
                cv.compareToCascade(cwd + "/src/dronemis/openCV/haarcascade_redCube.xml", imageMat);
                image = imageProcessor.toBufferedImage(imageMat);

                /*** ANDREAS HAR SAT DET HER IND **/
                Listeners.getInstance().updateImageListener("filtered", image);
                /*** ANDREAS HAR SAT DET HER IND **/

                if (imageSaveRate > 0){
                    try {
                        if (imageI % imageSaveRate == 0){
                            File outputfile = new File(cwd + "/savedImages/cascade" + imageI/imageSaveRate + ".bmp");
                            ImageIO.write(image, "bmp", outputfile);
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
