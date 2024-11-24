package editor;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static org.example.GParams.*;

/**
 * Handles mouse events for the editor, managing mouse clicks on a grid of rectangles.
 * Keeps track of mouse button states and updates the state upon mouse interactions.
 */
public class EditorMouseHandler implements MouseListener {
    Rectangle[][] field;
    public final boolean[][] fieldClicked;
    private boolean leftClickPressedBool;
    private boolean leftClickReleasedBool;
    private boolean rightClickPressedBool;
    private boolean rightClickReleasedBool;

    /**
     * Constructs an instance of the EditorMouseHandler class, initializing a grid of Rectangle objects.
     * Each Rectangle corresponds to a specific position within a field defined by the constants FIELD_WIDTH and FIELD_HEIGHT.
     * The dimensions of each Rectangle are determined by the constants gridWidth and gridHeight.
     * Also initializes a boolean array to keep track of which fields have been clicked.
     */
    public EditorMouseHandler() {
        field = new Rectangle[FIELD_WIDTH][FIELD_HEIGHT];
        for (int x = 0; x < FIELD_WIDTH; x++){
            for (int y = 0; y < FIELD_HEIGHT; y++){
                field[x][y] = new Rectangle(x* gridWidth, y* gridHeight, gridWidth, gridHeight);
            }
        }
        fieldClicked = new boolean[FIELD_WIDTH][FIELD_HEIGHT];
    }

    /**
     * Handles mouse click events.
     *
     * @param e the MouseEvent object containing details of the mouse click.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // Unused event
    }

    /**
     * Handles the mouse pressed events.
     *
     * @param e the MouseEvent object containing details about the mouse press.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {leftClickPressedBool = true;}
        if (e.getButton() == MouseEvent.BUTTON3) {rightClickPressedBool = true;}


    }

    /**
     * Handles the release of mouse buttons.
     * Updates the state of leftClickPressedBool and rightClickPressedBool to false,
     * and sets leftClickReleasedBool and rightClickReleasedBool to true based on the mouse button released.
     *
     * @param e the MouseEvent object containing details of the mouse release event.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClickReleasedBool = true;
            leftClickPressedBool = false;
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            rightClickReleasedBool = true;
            rightClickPressedBool = false;
        }
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the MouseEvent object containing details about the mouse enter event.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // Unused event
    }

    /**
     * Handles the mouse exited events.
     *
     * @param e the MouseEvent object containing details about the mouse exit event.
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // Unused event
    }

    /**
     * Checks if the left mouse button is currently pressed.
     *
     * @return true if the left mouse button is pressed, false otherwise.
     */
    public boolean isLeftClickPressedBool() {
        return leftClickPressedBool;
    }

    /**
     * Checks if the left mouse button has been released.
     *
     * @return true if the left mouse button has been released, false otherwise.
     */
    public boolean isLeftClickReleasedBool() {
        return leftClickReleasedBool;
    }

    /**
     * Resets the state of the left mouse button release action.
     * This method sets the variable `leftClickReleasedBool` to `false`, indicating
     * that the left mouse button release action has been processed or acknowledged.
     */
    public void leftClickActionDone() {
        leftClickReleasedBool = false;
    }

    /**
     * Checks whether the right mouse button is currently pressed.
     *
     * @return {@code true} if the right mouse button is pressed; {@code false} otherwise.
     */
    public boolean isRightClickPressedBool() {return rightClickPressedBool;}

    /**
     * Checks if the right mouse button has been released.
     *
     * @return true if the right mouse button is released, false otherwise.
     */
    public boolean isRightClickReleasedBool() {return rightClickReleasedBool;}

    /**
     * Resets the state of the right-click action.
     * This method sets the rightClickReleasedBool field to false,
     * indicating that a right-click action has been completed.
     */
    public void rightClickActionDone() {rightClickReleasedBool = false;}
}
