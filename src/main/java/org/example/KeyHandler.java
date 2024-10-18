package org.example;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;

public class KeyHandler implements KeyListener, Serializable {

    public final boolean[] keys = new boolean[256];

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO document why this method is empty
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keys[keyCode] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keys[keyCode] = false;
    }
}
