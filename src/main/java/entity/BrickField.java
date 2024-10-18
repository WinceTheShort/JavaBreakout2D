package entity;

import editor.EditorPanel;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class BrickField implements Serializable {
    private Brick[][] field;
    public final boolean[][] wasChanged;
    private final transient EditorPanel editor;
    private File saveFile = null;
    private boolean saved = false;


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
            saved = false;
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
    //Mouse pressed actions
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
    //Mouse release actions
    private void mouseReleasedAction(){
        for (int x = 0; x < EditorPanel.FIELD_WIDTH; x++){
            for (int y = 0; y < EditorPanel.FIELD_HEIGHT; y++){
                wasChanged[x][y] = false;
            }
        }
        editor.editorMouseHandler.rightClickActionDone();
        editor.editorMouseHandler.leftClickActionDone();
    }
    public void newBoard(){
        for (int x = 0; x < EditorPanel.FIELD_WIDTH; x++){
            for (int y = 0; y < EditorPanel.FIELD_HEIGHT; y++){
                field[x][y].setActiveSprite(0);
                field[x][y].setAlive(false);
            }
        }
        saveFile = null;
    }
    public void saveBoard(){
        if (!saved){
            if (saveFile == null){
                JFileChooser fileChooser = new JFileChooser("src/levels");
                if (fileChooser.showSaveDialog(editor) == JFileChooser.APPROVE_OPTION) {
                    saveFile = fileChooser.getSelectedFile();
                    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFile))) {
                        oos.writeObject(field);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            } else {
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFile))) {
                    oos.writeObject(field);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    public void openBoard(){
        JFileChooser fileChooser = new JFileChooser("src/levels");
        if (fileChooser.showOpenDialog(editor) == JFileChooser.APPROVE_OPTION) {
            saveFile = fileChooser.getSelectedFile();
            try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream(saveFile))) {
                field = (Brick[][]) oos.readObject();
            } catch (Exception e){
                e.printStackTrace();
            }
            for (Brick[] bricks : field){
                for (Brick brick : bricks){
                    brick.reloadTexture();
                }
            }
        }
    }
}
