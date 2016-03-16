package dronemis;

import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;
import de.yadrone.base.command.*;
import de.yadrone.base.exception.ARDroneException;
import de.yadrone.base.exception.IExceptionListener;
import de.yadrone.base.video.ImageListener;
import dronemis.GUI.Listeners;

import java.awt.image.BufferedImage;

public class DroneHandler implements IDroneHandler {
    private IARDrone drone;
    private boolean isFront = true;
    private boolean isFlying = false;
    private boolean useMove = false;
    private boolean isSwapping;
    private int speed = 20;
    private int duration = 20;
    private int freezeDuration = 10;
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

    public void init(){
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
                    drone.getCommandManager().init().doFor(1000);
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

    @Override
    public void takeOff() {
        drone.takeOff();
        isFlying = true;
    }

    public void land(){
        drone.landing();
        isFlying = false;
    }

    @Override
    public void moveForward() {
        if(!useMove)
            drone.getCommandManager().forward(speed).doFor(duration);
        else
            drone.getCommandManager().move(0, -speed, 0, 0).doFor(duration);

        drone.getCommandManager().freeze().doFor(freezeDuration);
    }

    @Override
    public void moveBackward() {
        if(!useMove)
            drone.getCommandManager().backward(speed).doFor(duration);
        else
            drone.getCommandManager().move(0, speed, 0, 0).doFor(duration);

        drone.getCommandManager().freeze().doFor(freezeDuration);
    }

    @Override
    public void moveLeft() {
        if(!useMove)
            drone.getCommandManager().goLeft(speed).doFor(duration);
        else
            drone.getCommandManager().move(-speed, 0, 0, 0).doFor(duration);

        drone.getCommandManager().freeze().doFor(freezeDuration);
    }

    @Override
    public void moveRight() {
        if(!useMove)
            drone.getCommandManager().goRight(speed).doFor(duration);
        else
            drone.getCommandManager().move(speed, 0, 0, 0).doFor(duration);

        drone.getCommandManager().freeze().doFor(freezeDuration);
    }

    @Override
    public void moveUp() {
        if(!useMove)
            drone.getCommandManager().up(speed).doFor(duration);
        else
            drone.getCommandManager().move(0, 0, speed, 0).doFor(duration);

        drone.getCommandManager().freeze().doFor(freezeDuration);
    }

    @Override
    public void moveDown() {
        if(!useMove)
            drone.getCommandManager().down(speed).doFor(duration);
        else
            drone.getCommandManager().move(0, 0, -speed, 0).doFor(duration);

        drone.getCommandManager().freeze().doFor(freezeDuration);
    }

    @Override
    public void turnLeft() {
        if(!useMove)
            drone.getCommandManager().spinLeft(speed).doFor(duration);
        else
            drone.getCommandManager().move(0, 0, 0, -speed);

        drone.getCommandManager().freeze().doFor(freezeDuration);
    }

    @Override
    public void turnRight() {
        if(!useMove)
            drone.getCommandManager().spinRight(speed).doFor(duration);
        else
            drone.getCommandManager().move(0, 0, 0, speed);

        drone.getCommandManager().freeze().doFor(freezeDuration);
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

    @Override
    public void emergencyStop() {
        drone.stop();
        drone.stop();
        drone.stop();
        drone.stop();
        isFlying = false;
    }

    public boolean isFlying() {
        return isFlying;
    }
}
