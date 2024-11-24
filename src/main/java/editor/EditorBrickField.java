package editor;

import entity.BrickField;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.*;

import static org.example.GParams.*;
import static org.example.GParams.FIELD_HEIGHT;

public class EditorBrickField extends BrickField {
    private final transient EditorBrickFieldPanel editor;
    protected File saveFile = null;
    protected boolean saved = false;
    public final boolean[][] wasChanged;


    public EditorBrickField(EditorBrickFieldPanel editorBrickFieldPanel) {
        super();
        wasChanged = new boolean[FIELD_WIDTH][FIELD_HEIGHT];
        editor = editorBrickFieldPanel;

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
    //Mouse pressed actions
    private void mousePressedAction(){
        int gridX = editor.getMousePosition().x/ gridWidth;
        int gridY = editor.getMousePosition().y/ gridHeight;
        if (gridX >= 0 && gridX < FIELD_WIDTH && gridY >= 0 && gridY < FIELD_HEIGHT && !wasChanged[gridX][gridY]) {
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
        for (int x = 0; x < FIELD_WIDTH; x++){
            for (int y = 0; y < FIELD_HEIGHT; y++){
                wasChanged[x][y] = false;
            }
        }
        editor.editorMouseHandler.rightClickActionDone();
        editor.editorMouseHandler.leftClickActionDone();
    }
    public void newBoard(){
        for (int x = 0; x < FIELD_WIDTH; x++){
            for (int y = 0; y < FIELD_HEIGHT; y++){
                field[x][y].setActiveSprite(0);
                field[x][y].setAlive(false);
            }
        }
        saveFile = null;
    }
    public void saveBoard(){
        Integer[][] brickStates = new Integer[FIELD_WIDTH][FIELD_HEIGHT];
        for (int x = 0; x < FIELD_WIDTH; x++){
            for (int y = 0; y < FIELD_HEIGHT; y++){
                brickStates[x][y] = field[x][y].getActiveSprite();
            }
        }

        if (!saved){
            if (saveFile == null){
                saveAs();
            } else {
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFile))) {
                    oos.writeObject(brickStates);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    public void saveAs(){
        Integer[][] brickStates = new Integer[FIELD_WIDTH][FIELD_HEIGHT];
        for (int x = 0; x < FIELD_WIDTH; x++){
            for (int y = 0; y < FIELD_HEIGHT; y++){
                brickStates[x][y] = field[x][y].getActiveSprite();
            }
        }

        JFileChooser fileChooser = getjFileChooser();
        if (fileChooser.showSaveDialog(editor) == JFileChooser.APPROVE_OPTION) {
            saveFile = fileChooser.getSelectedFile();
            saveFile = new File(saveFile.getPath()+".save");
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFile))) {
                oos.writeObject(brickStates);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void openBoard(){
        JFileChooser fileChooser = getjFileChooser();
        if (fileChooser.showOpenDialog(editor) == JFileChooser.APPROVE_OPTION) {
            saveFile = fileChooser.getSelectedFile();
            load(saveFile);
        }
    }
    //Creates file chooser for saving and opening save files
    private static JFileChooser getjFileChooser() {
        JFileChooser fileChooser = new JFileChooser("src/levels");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return (f.isFile() && f.getName().toLowerCase().endsWith(".save")) || f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "save file";
            }
        });
        return fileChooser;
    }

}
