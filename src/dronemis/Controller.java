package dronemis;

import dronemis.GUI.GUI;

public class Controller implements GUI.CommandListener {

    private GUI gui;
    private IDroneHandler droneHandler;

    private boolean manuelControl = true;

    public Controller() {
        this.gui = new GUI();
        this.droneHandler = new DroneHandler();
        gui.setCommandListener(this);
    }

    public void startSystem(){
        droneHandler.takeOff();
    }

    @Override
    public boolean doCommand(String command) {
        if(manuelControl)
        switch (command){
            case "frontCamera":
                droneHandler.useFrontCamera();
                return true;
            case "bottomCamera":
                droneHandler.useBottomCamera();
                return true;
            case "land":
                droneHandler.land();
                return true;
        }
        return false;
    }
}
