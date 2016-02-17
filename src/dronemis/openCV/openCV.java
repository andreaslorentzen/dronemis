
package dronemis.openCV;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class openCV {


    public static void main(String[] args) throws Exception {

        final String filePath = "C:\\Users\\hippomormor\\Desktop\\image.jpg";

        initLib();

        Mat testImage = Imgcodecs.imread(filePath);

        filter(testImage);

        if(testImage.dataAddr() == 0)
            System.err.println("Couldn't open file " + filePath);
        else{
            ImageViewer imageViewer = new ImageViewer();
            imageViewer.show(testImage, "Test image");
        }

    }


    private static boolean initLib() {

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

    public static void filter(Mat image){
        int totalBytes = (int)(image.total() * image.elemSize());
        byte buffer[] = new byte[totalBytes];
        image.get(0, 0,buffer);
        for(int i=0;i<totalBytes;i++){
            if(i%3==0) buffer[i]=0;
        }
        image.put(0, 0, buffer);
    }
}
