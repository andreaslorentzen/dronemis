package dronemis.openCV;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.util.ArrayList;
import java.util.List;

public class openCV {


    public openCV (){

        final String cwd = System.getProperty("user.dir");

        initLib();

        Mat image = Imgcodecs.imread(cwd + "/src/dronemis/openCV/image.JPG");

        convertToEdges(image);

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
                cwd = cwd + "\\lib\\openCV\\java\\x64\\opencv_java310.dll";
            else
                cwd = cwd + "\\lib\\openCV\\java\\x86\\opencv_java310.dll";
        }
        else if (OS.contains("mac"))
            cwd = cwd + "/lib/openCV/java/mac/libopencv_java310.dylib";
        else {
            System.err.println("Unable to determine OS!");
            return false;
        }

        System.load(cwd);
        return true;
    }


    public Mat convertToEdges(Mat img) {

        // Convert to greyscale:
        Imgproc.cvtColor(img,img,Imgproc.COLOR_BGR2GRAY);

        // Add Gaussian noise:
        Imgproc.GaussianBlur(img, img, new Size(3.0, 3.0), 7);

        // Paint edges:
        Imgproc.Canny(img, img, 100, 100);
       // List<MatOfPoint> mop = new ArrayList<>();


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
