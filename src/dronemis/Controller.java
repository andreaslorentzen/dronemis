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
                case "takeOffOrLand":
                    if (droneHandler.isFlying()){
                        droneHandler.land();
                    }else {
                        droneHandler.takeOff();
                    }
                    return true;
                case "droneMoveForward":
                    droneHandler.moveForward();
                    return true;
                case "droneMoveLeft":
                    droneHandler.moveLeft();
                    return true;
                case "droneMoveBackward":
                    droneHandler.moveBackward();
                    return true;
                case "droneMoveRight":
                    droneHandler.moveRight();
                    return true;
                case "droneTurnLeft":
                    droneHandler.turnLeft();
                    return true;
                case "droneTurnRight":
                    droneHandler.turnRight();
                    return true;
                case "droneMoveUp":
                    droneHandler.moveUp();
                    return true;
                case "droneMoveDown":
                    droneHandler.moveDown();
                    return true;
            }

        }
        return false;
    }
}
