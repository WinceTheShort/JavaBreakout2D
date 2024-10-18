package entity;

import editor.EditorPanel;

import java.awt.*;
import java.io.Serializable;

public class BrickField implements Serializable {
    private final Brick[][] field;
    public final boolean[][] wasChanged;
    private final transient EditorPanel editor;


    public BrickField(EditorPanel editorPanel) {
        field = new Brick[EditorPanel.FIELD_WIDTH][EditorPanel.FIELD_HEIGHT];
        for (int x = 0; x < EditorPanel.FIELD_WIDTH; x++){
            for (int y = 0; y < EditorPanel.FIELD_HEIGHT; y++){
                field[x][y] = new Brick(x*EditorPanel.GRID_WIDTH, y*EditorPanel.GRID_HEIGHT, EditorPanel.GRID_WIDTH, EditorPanel.GRID_HEIGHT*2);
            }
        }
        wasChanged = new boolean[EditorPanel.FIELD_WIDTH][EditorPanel.FIELD_HEIGHT];
        editor = editorPanel;
    }
    public void update() {
        if ((editor.editorMouseHandler.isLeftClickPressedBool() || editor.editorMouseHandler.isRightClickPressedBool()) && editor.getMousePosition() != null) {
            mousePressedAction();
        }
        if (editor.editorMouseHandler.isLeftClickReleasedBool() || editor.editorMouseHandler.isRightClickReleasedBool()) {
            mouseReleasedAction();
        }
    }
    public void draw(Graphics2D g2d){
        for (Brick[] bricks : field) {
            for (Brick brick : bricks) {
                brick.draw(g2d);
            }
        }
    }
    private void mousePressedAction(){
        int gridX = editor.getMousePosition().x/EditorPanel.GRID_WIDTH;
        int gridY = editor.getMousePosition().y/EditorPanel.GRID_HEIGHT;
        if (gridX >= 0 && gridX < EditorPanel.FIELD_WIDTH && gridY >= 0 && gridY < EditorPanel.FIELD_HEIGHT && !wasChanged[gridX][gridY]) {
            if (editor.editorMouseHandler.isLeftClickPressedBool()) { //If left-clicked set to current brick type and set alive true
                field[gridX][gridY].setActiveSprite(editor.editorMouseWheelHandler.getBrickType());
                field[gridX][gridY].setAlive(true);
            } else if (editor.editorMouseHandler.isRightClickPressedBool()) { //Else if right-clicked set to empty sprite and set alive to false
                field[gridX][gridY].setActiveSprite(0);
                field[gridX][gridY].setAlive(false);
            }

            wasChanged[gridX][gridY] = true;
        }
    }
    private void mouseReleasedAction(){
        for (int x = 0; x < EditorPanel.FIELD_WIDTH; x++){
            for (int y = 0; y < EditorPanel.FIELD_HEIGHT; y++){
                wasChanged[x][y] = false;
            }
        }
        editor.editorMouseHandler.rightClickActionDone();
        editor.editorMouseHandler.leftClickActionDone();
    }
}
