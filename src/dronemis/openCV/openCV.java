package dronemis.openCV;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class openCV {


    public openCV (){

        final String cwd = System.getProperty("user.dir");

        initLib();

        CascadeClassifier faceDetector = new CascadeClassifier(cwd + "/src/dronemis/openCV/haarcascade_frontalface_alt.xml");
        Mat image = Imgcodecs.imread(cwd + "/src/dronemis/openCV/image.JPG");
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        System.out.println("Detected faces: " + faceDetections.toArray().length);

        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 0, 0));
        }

        String filename = "filtered.png";
        System.out.println("Writing output to: " + filename);
        Imgcodecs.imwrite(filename, image);
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
}
