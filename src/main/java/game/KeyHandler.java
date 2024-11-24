package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles keyboard input for the game.
 */
public class KeyHandler implements KeyListener{

    public final boolean[] keys = new boolean[256];
    public final boolean[] keyTypedBooleans = new boolean[256];

    /**
     * Invoked when a key has been typed.
     * Not used in this implementation.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
        //unused
    }

    /**
     * Invoked when a key has been pressed.
     * Updates the `keys` array to mark the key as pressed.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keys[keyCode] = true;
    }

    /**
     * Invoked when a key has been released.
     * Updates the `keys` and `keyTyped` arrays to mark the key as released and typed.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keys[keyCode]) {
            keyTypedBooleans[keyCode] = true;
        }
        keys[keyCode] = false;
    }
}
