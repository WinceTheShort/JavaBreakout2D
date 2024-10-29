package editor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;

public class EditorKeyHandler implements KeyListener, Serializable {

    public final boolean[] keys = new boolean[256];

    @Override
    public void keyTyped(KeyEvent e) {
        // Unused event
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
}
