package dronemis;

import dronemis.GUI.GUI;

public class Controller implements GUI.CommandListener {

    private GUI gui;
    private Communicator communicator;

    public Controller() {
        this.gui = new GUI();
        this.communicator = new Communicator();
        gui.setCommandListener(this);
    }

    public void startSystem(){
        communicator.takeOff();
    }

    @Override
    public boolean doCommand(String command) {
        switch (command){
            case "front":
                communicator.useFrontCamera();
                break;
            case "bottom":
                communicator.useBottomCamera();
                break;
            case "stop":
                communicator.land();
                break;
        }
        return false;
    }
}
