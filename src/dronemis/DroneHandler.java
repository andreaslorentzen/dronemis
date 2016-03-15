package dronemis;

import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;
import de.yadrone.base.command.VideoBitRateMode;
import de.yadrone.base.command.VideoChannel;
import de.yadrone.base.command.VideoCodec;
import de.yadrone.base.exception.ARDroneException;
import de.yadrone.base.exception.IExceptionListener;
import de.yadrone.base.video.ImageListener;
import dronemis.GUI.Listeners;

import java.awt.image.BufferedImage;

public class DroneHandler implements IDroneHandler {
    private IARDrone drone;
    private boolean isFront = true;
    private boolean isSwapping;

    public DroneHandler() {
        try {
            // Tutorial Section 1
            this.drone = new ARDrone();
            drone.addExceptionListener(new IExceptionListener() {
                public void exeptionOccurred(ARDroneException exc) {
                    exc.printStackTrace();
                }
            });
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public void takeOff(){
        try{
            drone.start();
            drone.getVideoManager().addImageListener(new ImageListener() {
                public void imageUpdated(BufferedImage newImage) {
                    if(!isSwapping) {
                        if (isFront) {
                            Listeners.getInstance().updateImageFront(newImage);
                        } else {
                            Listeners.getInstance().updateImageBottom(newImage);
                        }
                    }
                }
            });
            drone.getCommandManager().setVideoCodec(VideoCodec.H264_360P);
            drone.getCommandManager().setVideoBitrateControl(VideoBitRateMode.MANUAL);
            drone.getCommandManager().setMaxVideoBitrate(4000);
            drone.getCommandManager().setVideoBitrate(4000);
            drone.getCommandManager().setVideoCodecFps(30);
            drone.getCommandManager().setVideoChannel(VideoChannel.HORI);

                /*
                if (Keyboard.keys[KeyEvent.VK_Q]) {
                    drone.getCommandManager().takeOff().doFor(1000);
                }
                if (Keyboard.keys[KeyEvent.VK_Q]) {
                    drone.getCommandManager().landing();
                }
    */

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
    }

    public void land(){
        drone.stop();
    }

    public void flyToCoordinates(int x, int y, int z){
        // TODO implement this when we have more knowledge about OpenCVHelper
    }

    public void useFrontCamera(){
        isSwapping = true;
        isFront = true;
        drone.getCommandManager().setVideoChannel(VideoChannel.HORI);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isSwapping = false;
    }

    public void useBottomCamera(){
        isSwapping = true;
        isFront = false;
        drone.getCommandManager().setVideoChannel(VideoChannel.VERT);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isSwapping = false;
    }

}
