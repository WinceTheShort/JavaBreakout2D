package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

    public final boolean[] keys = new boolean[256];
    public final boolean[] keyTyped = new boolean[256];

    @Override
    public void keyTyped(KeyEvent e) {
        //unused
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keys[keyCode] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keys[keyCode]) {
            keyTyped[keyCode] = true;
        }
        keys[keyCode] = false;
    }
}
