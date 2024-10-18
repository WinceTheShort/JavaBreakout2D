package editor;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EditorMouseHandler implements MouseListener {
    Rectangle[][] field;
    public final boolean[][] fieldClicked;
    private boolean leftClickPressedBool;
    private boolean leftClickReleasedBool;
    private boolean rightClickPressedBool;
    private boolean rightClickReleasedBool;

    public EditorMouseHandler() {
        field = new Rectangle[EditorPanel.FIELD_WIDTH][EditorPanel.FIELD_HEIGHT];
        for (int x = 0; x < EditorPanel.FIELD_WIDTH; x++){
            for (int y = 0; y < EditorPanel.FIELD_HEIGHT; y++){
                field[x][y] = new Rectangle(x*EditorPanel.GRID_WIDTH, y*EditorPanel.GRID_HEIGHT, EditorPanel.GRID_WIDTH, EditorPanel.GRID_HEIGHT);
            }
        }
        fieldClicked = new boolean[EditorPanel.FIELD_WIDTH][EditorPanel.FIELD_HEIGHT];
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO document why this method is empty
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {leftClickPressedBool = true;}
        if (e.getButton() == MouseEvent.BUTTON3) {rightClickPressedBool = true;}


    }

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

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO document why this method is empty
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO document why this method is empty
    }

    public boolean isLeftClickPressedBool() {
        return leftClickPressedBool;
    }
    public boolean isLeftClickReleasedBool() {
        return leftClickReleasedBool;
    }
    public void leftClickActionDone() {
        leftClickReleasedBool = false;
    }
    public boolean isRightClickPressedBool() {return rightClickPressedBool;}
    public boolean isRightClickReleasedBool() {return rightClickReleasedBool;}
    public void rightClickActionDone() {rightClickReleasedBool = false;}
}
