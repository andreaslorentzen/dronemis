/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dronemis.GUI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Marcus Persson
 */
public class Keyboard implements KeyListener {

    private int KEY_COUNT = 256;
    public boolean[] keys = null;

    @Override
    public synchronized void keyTyped(KeyEvent e) {
    }

    @Override
    public synchronized void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode >= 0 && keyCode < KEY_COUNT) {
            keys[keyCode] = true;
            System.out.println("keyPressed: " + keyCode + " " + keys[keyCode]);
        }
    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode >= 0 && keyCode < KEY_COUNT) {
            keys[keyCode] = false;
            System.out.println("keyReleased: " + keyCode + " " + keys[keyCode]);
        }
    }
}
