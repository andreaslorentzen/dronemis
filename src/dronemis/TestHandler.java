package dronemis;


import dronemis.GUI.Listeners;
import dronemis.openCV.ImageProcessor;
import dronemis.openCV.OpenCVHelper;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import java.awt.image.BufferedImage;

public class TestHandler implements IDroneHandler {

    private boolean isFront = true;

    public TestHandler() {
        OpenCVHelper.initLib();
    }

    ImageProcessor imageProcessor;
    VideoCapture capture;
    Mat webcamMatImage;

    @Override
    public void takeOff() {
        imageProcessor = new ImageProcessor();
        webcamMatImage = new Mat();
        capture = new VideoCapture(0);
        capture.set(Videoio.CAP_PROP_FRAME_WIDTH, 640);
        capture.set(Videoio.CAP_PROP_FRAME_HEIGHT, 480);
        if (capture.isOpened()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    BufferedImage tempImage;
                    while (true) {
                        capture.read(webcamMatImage);

                        if (!webcamMatImage.empty()) {

                            tempImage = imageProcessor.toBufferedImage(webcamMatImage);
                            if(isFront)
                                Listeners.getInstance().updateImageFront(tempImage);
                            else
                                Listeners.getInstance().updateImageBottom(tempImage);

                        } else {
                            System.out.println(" -- Frame not captured -- Break!");
                            break;
                        }
                        try {
                            Thread.sleep(60);
                        } catch (InterruptedException e) {
                            System.out.println("not sleep");
                        }
                    }
                }
            }).start();
        } else {
            System.out.println("Couldn't open capture.");
        }
    }

    @Override
    public void useFrontCamera() {
        isFront = true;
        System.out.println("Now using FrontCamera");
    }

    @Override
    public void useBottomCamera() {
        isFront = false;
        System.out.println("Now using BottomCamera");
    }

    @Override
    public void land() {

    }
}
