package dronemis.openCV;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class ImageProcessor {

	public BufferedImage toBufferedImage(Mat matrix){
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if ( matrix.channels() > 1 ) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		int bufferSize = matrix.channels()*matrix.cols()*matrix.rows();
		byte [] buffer = new byte[bufferSize];
		matrix.get(0,0,buffer); // get all the pixels
		BufferedImage image = new BufferedImage(matrix.cols(),matrix.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);  
		return image;
	}

	public Mat toMatImage(BufferedImage image) {
		// Convert to byte array
		byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

		// Create a Matrix
		Mat imageMat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);

		// Fill Matrix
		imageMat.put(0, 0, pixels);

		return imageMat;
	}
}