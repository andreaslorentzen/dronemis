package dronemis.openCV;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class OpenCVHelper {

    static private final String cwd = System.getProperty("user.dir");

    public OpenCVHelper() throws InterruptedException {
        initLib();
    }

    public static boolean initLib() {
        String cwdLib;
        final String OS = System.getProperty("os.name").toLowerCase();
        final String bitness = System.getProperty("sun.arch.data.model");

        if (OS.contains("windows")){
            if (bitness.equals("64"))
                cwdLib = cwd + "\\lib\\openCV\\x64\\opencv_java310.dll";
            else
                cwdLib = cwd + "\\lib\\openCV\\x86\\opencv_java310.dll";
        }
        else if (OS.contains("mac"))
            cwdLib = cwd + "/lib/openCV/Mac/libopencv_java310.so";
        else {
            System.err.println("Unable to determine OS!");
            return false;
        }

        System.load(cwdLib);
        return true;
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
                    new Scalar(0, 255, 0));
        }

        return image;
    }


}
