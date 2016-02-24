package dronemis.GUI;

import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;
import de.yadrone.base.command.VideoBitRateMode;
import de.yadrone.base.command.VideoChannel;
import de.yadrone.base.command.VideoCodec;
import de.yadrone.base.exception.ARDroneException;
import de.yadrone.base.exception.IExceptionListener;
import de.yadrone.base.video.ImageListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

public class TutorialMain {

    static public boolean camera = true;
    static public boolean requestSwap;
    static public int number = 30;


    public TutorialMain() {
        IARDrone drone = null;
        try {
            // Tutorial Section 1
            drone = new ARDrone();
            drone.addExceptionListener(new IExceptionListener() {
                public void exeptionOccurred(ARDroneException exc) {
                    exc.printStackTrace();
                }
            });

            drone.start();

            // Tutorial Section 2
            // new TutorialAttitudeListener(drone);
            // Tutorial Section 3
            //new TutorialVideoListener(drone);
            new GUI();
            
            drone.getVideoManager().addImageListener(new ImageListener() {
                public void imageUpdated(BufferedImage newImage) {
                   // System.out.println(newImage.);
//                    if(requestSwap){
//                       number++;
//                       if(number > 15)
//                           return;
//                    }
//                    if (camera) {
//                        for (Listeners.UpdateImageListener object : Listeners.getInstance().getUpdateFrontImageListener()) {
//                            object.updateImage(newImage);
//                        }
//                    } else {
//                        for (Listeners.UpdateImageListener object : Listeners.getInstance().getUpdateBottomImageListener()) {
//                            object.updateImage(newImage);
//                        }
//                    }
                    for (Listeners.UpdateImageListener object : Listeners.getInstance().getUpdateFrontImageListener()) {
                            object.updateImage(newImage);
                        }
                    
                }
            });

            drone.getCommandManager().setVideoCodec(VideoCodec.H264_360P);
            drone.getCommandManager().setVideoBitrateControl(VideoBitRateMode.MANUAL);
            drone.getCommandManager().setMaxVideoBitrate(4000);
            drone.getCommandManager().setVideoBitrate(4000);
            drone.getCommandManager().setVideoCodecFps(30);
            drone.getCommandManager().setVideoChannel(VideoChannel.NEXT);
//            while (true) {
//                if(number >= 30){
//                    number = 0;
//                    camera = !camera;
//                    requestSwap = false;
//                    Thread.sleep(200);
//                    drone.getCommandManager().setVideoChannel(VideoChannel.NEXT);
//                    requestSwap = true;
//                }
//                Thread.sleep(50);
//            }
            // Tutorial Section 4
            // TutorialCommander commander = new TutorialCommander(drone);
            // commander.animateLEDs();
            // commander.takeOffAndLand();
            // commander.leftRightForwardBackward();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        /*finally {
			if (drone != null)
				drone.stop();

			System.exit(0);
		}*/
    }

    public static void main(String[] args) {
        new TutorialMain();
    }
}
