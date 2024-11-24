package editor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;

/**
 * The EditorKeyHandler class implements KeyListener and Serializable to handle
 * keyboard input for an editor environment. It maintains the state of all key
 * presses in a boolean array.
 */
public class EditorKeyHandler implements KeyListener, Serializable {

    /**
     * An array representing the state of all keyboard keys. Each index corresponds
     * to a key code, and the boolean value at that index indicates whether the
     * key is currently pressed (true) or not (false).
     */
    public final boolean[] keys = new boolean[256];

    /**
     * Handles key typed events for the editor environment.
     * This method is currently not used but is required to fulfill the KeyListener interface contract.
     *
     * @param e the KeyEvent object containing information about the key typed event
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Unused event
    }

    /**
     * Handles the event when a key is pressed. This method updates the state of the keys array to reflect
     * that a specific key, represented by its key code, is currently being pressed.
     *
     * @param e the KeyEvent object that contains details about the key press event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    /**
     * Handles the release of a key event. When a key is released, the corresponding
     * entry in the boolean array `keys` is set to `false` to indicate that the key
     * is no longer pressed.
     *
     * @param e the KeyEvent that triggered this method
     */
    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
}
