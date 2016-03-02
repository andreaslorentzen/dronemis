package dronemis.openCV;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class openCV {

    static private JFrame frame;
    static private JLabel imageLabel;

    public openCV () throws InterruptedException {
        initLib();
        initGUI();
        webcamCapture();
    }

    public void testCV(){
        final String cwd = System.getProperty("user.dir");

        Mat image = Imgcodecs.imread(cwd + "/src/dronemis/openCV/image.JPG");
        List<MatOfPoint> contours = new ArrayList<>();
        convertToEdges(image);
        Mat hir = new Mat();
        Imgproc.findContours(image, contours, hir, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        Imgproc.drawContours(image, contours,-1, new Scalar(255),-1);

        //compareToCascade(cwd + "/src/dronemis/openCV/haarcascade_frontalface_alt.xml", image);
        ImageViewer iv = new ImageViewer();
        iv.show(image,"test");

        /*
        String filename = "filtered.png";
        System.out.println("Writing output to: " + filename);
        Imgcodecs.imwrite(filename, image);
        */
    }

    private boolean initLib() {

        final String OS = System.getProperty("os.name").toLowerCase();
        final String bitness = System.getProperty("sun.arch.data.model");
        String cwd = System.getProperty("user.dir");

        if (OS.contains("windows")){
            if (bitness.equals("64"))
                cwd = cwd + "\\lib\\openCV\\x64\\opencv_java310.dll";
            else
                cwd = cwd + "\\lib\\openCV\\x86\\opencv_java310.dll";
        }
        else if (OS.contains("mac"))
            cwd = cwd + "/lib/openCV/Mac/libopencv_java310.so";
        else {
            System.err.println("Unable to determine OS!");
            return false;
        }

        System.load(cwd);
        return true;
    }

    private void initGUI() {
        frame = new JFrame("Camera Input Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 200);
        imageLabel = new JLabel();
        frame.add(imageLabel);
        frame.setVisible(true);
    }

    private void webcamCapture() {
        ImageProcessor imageProcessor = new ImageProcessor();
        Mat webcamMatImage = new Mat();
        Image tempImage;
        VideoCapture capture = new VideoCapture(0);
        capture.set(Videoio.CAP_PROP_FRAME_WIDTH, 640);
        capture.set(Videoio.CAP_PROP_FRAME_HEIGHT, 480);
        if (capture.isOpened()) {
            while (true) {
                capture.read(webcamMatImage);
                convertToEdges(webcamMatImage);
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

    public Mat convertToEdges(Mat img) {

        // Convert to greyscale:

        //Imgproc.cvtColor(img,img,Imgproc.COLOR_BGR2GRAY);

        // Add Gaussian noise:
        //Imgproc.GaussianBlur(img, img, new Size(3, 3), 0);

        // Paint edges:

        //Imgproc.threshold(img,img,100,255,Imgproc.THRESH_TRUNC);
        //Imgproc.Canny(img, img, 250, 0);

        return img;
    }


    public Mat compareToCascade(String cascade, Mat image) {

        CascadeClassifier faceDetector = new CascadeClassifier(cascade);
        MatOfRect cascadeDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, cascadeDetections);

        System.out.println("Detected cascades: " + cascadeDetections.toArray().length);

        for (Rect rect : cascadeDetections.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 0, 0));
        }

        return image;
    }


}
