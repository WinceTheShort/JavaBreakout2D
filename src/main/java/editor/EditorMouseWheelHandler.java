package editor;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * The EditorMouseWheelHandler class implements the MouseWheelListener interface
 * and provides functionality to change the type of brick by rotating the mouse wheel.
 */
public class EditorMouseWheelHandler implements MouseWheelListener {
    private int brickType = 1;


    /**
     * Handles the mouse wheel movement event and updates the brick type accordingly.
     * If the wheel is rotated upwards, increments the brickType if it's not 5, otherwise resets it to 1.
     * If the wheel is rotated downwards, decrements the brickType if it's not 1, otherwise sets it to 5.
     *
     * @param e the MouseWheelEvent triggered by the user rotating the mouse wheel
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) {
            if (brickType != 5) {brickType ++;}
            else {brickType = 1;}
        }
        if (e.getWheelRotation() > 0) {
            if (brickType != 1) {brickType --;}
            else {brickType = 5;}
        }
    }

    /**
     * Retrieves the current brick type.
     *
     * @return the integer value representing the current brick type
     */
    public int getBrickType() {
        return brickType;
    }
}
