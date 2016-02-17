
package dronemis;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.net.URL;

public class openCV {


    public static void main(String[] args) {

        System.load(getLibPath());
        Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
        System.out.println("mat = " + mat.dump());
    }

    private static String getLibPath() {

        final String dir = System.getProperty("user.dir");
        String OS = System.getProperty("os.name").toLowerCase();

        if (OS.contains("windows"))
            return dir + "\\lib\\openCV\\java\\x64\\opencv_java310.dll";
        else if (OS.contains("mac"))
            return dir + "/lib/openCV/java/mac/libopencv_java310.dylib";
        else
            System.err.println("Unable to determine OS!");

        return null;
    }
}
