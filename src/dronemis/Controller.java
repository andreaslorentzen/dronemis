package dronemis;

import dronemis.GUI.GUI;

public class Controller implements GUI.CommandListener {

    private GUI gui;

    public Controller() {
        this.gui = new GUI();
        gui.setCommandListener(this);
    }

    public void startSystem(){

    }

    @Override
    public boolean doCommand(String command) {
        return false;
    }
}
