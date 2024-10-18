package editor;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EditorMouseHandler implements MouseListener {
    Rectangle[][] field;
    public final boolean[][] fieldClicked;
    private boolean mousePressedBool;
    private boolean mouseReleasedBool;

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
        mousePressedBool = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseReleasedBool = true;
        mousePressedBool = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO document why this method is empty
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO document why this method is empty
    }

    public boolean isMousePressedBool() {
        return mousePressedBool;
    }
    public boolean isMouseReleasedBool() {
        return mouseReleasedBool;
    }
    public void mouseReleasedActionDone() {
        mouseReleasedBool = false;
    }
}
