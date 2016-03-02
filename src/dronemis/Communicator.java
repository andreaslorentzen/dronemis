package dronemis;

import de.yadrone.base.IARDrone;
import de.yadrone.base.command.VideoChannel;
import de.yadrone.base.video.ImageListener;

/**
 * Created by mathias on 02/03/16.
 */
public class Communicator {
    private IARDrone drone;

    public Communicator(IARDrone drone) {
        this.drone = drone;
    }

    public void flyToCoordinates(int x, int y, int z){
        // TODO implement this when we have more knowledge about openCV
    }

    public void useFrontCamera(ImageListener imageListener){
        drone.getCommandManager().setVideoChannel(VideoChannel.HORI);
        try {
            this.wait(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void useBottomCamera(){
        drone.getCommandManager().setVideoChannel(VideoChannel.VERT);
        try {
            this.wait(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addImageListener(ImageListener imageListener){
        drone.getVideoManager().addImageListener(imageListener);
    }
}
