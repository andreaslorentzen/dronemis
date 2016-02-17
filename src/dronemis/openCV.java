
package dronemis;

import org.opencv.core.CvType;
import org.opencv.core.Mat;


/**
 *
 * @author AL
 */
public class openCV {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String libopencv_java = "/Users/hippomormor/Desktop/dronemis/lib/openCV/java/mac/libopencv_java310.dylib";
        System.load(libopencv_java);
        Mat mat = Mat.eye( 3, 3, CvType.CV_8UC1 );
        System.out.println( "mat = " + mat.dump() );
    }

}
