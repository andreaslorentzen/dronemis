package dronemis;

import dronemis.GUI.GUI;

public class Controller implements GUI.CommandListener {

    private GUI gui;
    private IDroneHandler droneHandler;

    private boolean manuelControl = true;

    public Controller() {
        this.gui = new GUI();
        this.droneHandler = new TestHandler();
        gui.setCommandListener(this);
    }

    public void startSystem(){
        droneHandler.init();
    }

    @Override
    public boolean doCommand(String command) {
        if(manuelControl){
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


                case "droneMoveForward":
                    return true;
                case "droneMoveLeft":
                    return true;
                case "droneMoveBackward":
                    return true;
                case "droneMoveRight":
                    return true;
                case "droneTurnLeft":
                    return true;
                case "droneTurnRight":
                    return true;
                case "droneMoveUp":
                    return true;
                case "droneMoveDown":
                    return true;
            }

        }
        return false;
    }
}
