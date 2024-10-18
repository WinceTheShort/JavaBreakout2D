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
                field[x][y] = new Brick(x*EditorPanel.GRID_WIDTH, y*EditorPanel.GRID_HEIGHT);
            }
        }
        wasChanged = new boolean[EditorPanel.FIELD_WIDTH][EditorPanel.FIELD_HEIGHT];
        editor = editorPanel;
    }
    public void update() {
        if (editor.editorMouseHandler.isMousePressedBool() && editor.getMousePosition() != null) {
            int gridX = editor.getMousePosition().x/EditorPanel.GRID_WIDTH;
            int gridY = editor.getMousePosition().y/EditorPanel.GRID_HEIGHT;
            if (gridX > 0 && gridX < EditorPanel.FIELD_WIDTH && gridY > 0 && gridY < EditorPanel.FIELD_HEIGHT && !wasChanged[gridX][gridY]) {
                field[gridX][gridY].nextSprite();
                field[gridX][gridY].setAlive(field[gridX][gridY].getActiveSprite() != 0);
                wasChanged[gridX][gridY] = true;
            }
        }
        if (editor.editorMouseHandler.isMouseReleasedBool()){
            for (int x = 0; x < EditorPanel.FIELD_WIDTH; x++){
                for (int y = 0; y < EditorPanel.FIELD_HEIGHT; y++){
                    wasChanged[x][y] = false;
                }
            }
            editor.editorMouseHandler.mouseReleasedActionDone();
        }

    }
    public void draw(Graphics2D g2d){
        for (int x = 0; x < field.length; x++){
            for (int y = 0; y < field[x].length; y++){
                field[x][y].draw(g2d);
            }
        }
    }
}
