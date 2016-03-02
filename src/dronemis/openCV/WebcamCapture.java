package dronemis.openCV;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.Videoio;
import org.opencv.videoio.VideoCapture;

public class WebcamCapture {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    static private JFrame frame;
    static private JLabel imageLabel;

    public static void main(String[] args) throws InterruptedException {

        initGUI();
        runMainLoop(args);
    }

    static private void initGUI() {
        frame = new JFrame("Camera Input Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 200);
        imageLabel = new JLabel();
        frame.add(imageLabel);
        frame.setVisible(true);
    }

    static private void runMainLoop(String[] args) throws InterruptedException {
        ImageProcessor imageProcessor = new ImageProcessor();
        Mat webcamMatImage = new Mat();
        Image tempImage;
        VideoCapture capture = new VideoCapture(0);
        capture.set(Videoio.CAP_PROP_FRAME_WIDTH, 640);
        capture.set(Videoio.CAP_PROP_FRAME_HEIGHT, 480);
        if (capture.isOpened()) {
            while (true) {
                capture.read(webcamMatImage);
                filter(webcamMatImage);
                if (!webcamMatImage.empty()) {
                    tempImage = imageProcessor.toBufferedImage(webcamMatImage);
                    ImageIcon imageIcon = new ImageIcon(tempImage, "Captured video");
                    imageLabel.setIcon(imageIcon);
                    frame.pack(); //this will resize the window to fit the image
                } else {
                    System.out.println(" -- Frame not captured -- Break!");
                    break;
                }
            }
        } else {
            System.out.println("Couldn't open capture.");
        }
    }

    static public void filter(Mat image) {
        int totalBytes = (int) (image.total() * image.elemSize());
        byte buffer[] = new byte[totalBytes];
        image.get(0, 0, buffer);
        for (int i = 0; i < totalBytes; i++) {
            if (i % 3 == 1) {
                buffer[i] = 0;
            }
//            if (i % 3 == 1) {
//                buffer[i] = 0;
//            }
        }
        image.put(0, 0, buffer);
    }
}
