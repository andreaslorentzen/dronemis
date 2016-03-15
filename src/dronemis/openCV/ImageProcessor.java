package dronemis.openCV;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import javax.swing.*;

public class ImageProcessor {

	private JLabel imageView;

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
		// Convert bufferedimage to byte array
		byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

		// Create a Matrix the same size of image
		Mat imageMat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);

		// Fill Matrix with image values
		imageMat.put(0, 0, pixels);

		return imageMat;
	}

	public void show(Mat image,String windowName){
		setSystemLookAndFeel();
		JFrame frame = createJFrame(windowName);
		Image loadedImage = toBufferedImage(image);
		imageView.setIcon(new ImageIcon(loadedImage));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}


	private JFrame createJFrame(String windowName) {
		JFrame frame = new JFrame(windowName);
		imageView = new JLabel();
		final JScrollPane imageScrollPane = new JScrollPane(imageView);
		imageScrollPane.setPreferredSize(new Dimension(640, 480));
		frame.add(imageScrollPane, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		return frame;
	}


	private void setSystemLookAndFeel() {
		try {
			UIManager.setLookAndFeel
					(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
        
}