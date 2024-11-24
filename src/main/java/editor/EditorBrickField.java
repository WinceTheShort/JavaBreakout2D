package editor;

import entity.BrickField;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.*;

import static org.example.GParams.*;
import static org.example.GParams.FIELD_HEIGHT;

/**
 * The EditorBrickField class represents a specialized BrickField used for editing purposes.
 * It includes functionalities for handling mouse interactions, saving, and loading the brick field's state.
 */
public class EditorBrickField extends BrickField {
    /**
     * An EditorBrickFieldPanel representing the editor component associated with the
     * EditorBrickField. This component is used for various editing functionalities related
     * to the brick field.
     */
    private final transient EditorBrickFieldPanel editor;
    /**
     * Represents the file where the current state of the editor is saved.
     * This file can be used to persist the state and be reloaded later.
     * It is managed by various methods in the EditorBrickField such as saveBoard(),
     * saveAs(), and openBoard().
     */
    protected File saveFile = null;
    /**
     * Indicates whether the current state of the editor has been saved.
     * Used to track if unsaved changes exist within the EditorBrickField.
     */
    protected boolean saved = false;
    /**
     * A two-dimensional array representing the changed state of each cell in the editor.
     * Each element of the array is a boolean indicating whether a specific cell has been modified.
     * This is used to track edits made to the content.
     */
    public final boolean[][] wasChanged;


    /**
     * Constructs an EditorBrickField with the specified EditorBrickFieldPanel.
     * Initializes the wasChanged boolean array and sets the editor.
     *
     * @param editorBrickFieldPanel the panel associated with this EditorBrickField
     */
    public EditorBrickField(EditorBrickFieldPanel editorBrickFieldPanel) {
        super();
        wasChanged = new boolean[FIELD_WIDTH][FIELD_HEIGHT];
        editor = editorBrickFieldPanel;

    }

    /**
     * Updates the state of the editor field based on mouse interactions.
     * <ul>
     *   <li>If either the left or right mouse button is pressed and the mouse position is not null,
     *       the mousePressedAction method is triggered, and the 'saved' flag is set to false.</li>
     *   <li>If either the left or right mouse button is released, the mouseReleasedAction method is called.</li>
     * </ul>
     */
    public void update() {
        if ((editor.editorMouseHandler.isLeftClickPressedBool() || editor.editorMouseHandler.isRightClickPressedBool()) && editor.getMousePosition() != null) {
            mousePressedAction();
            saved = false;
        }
        if (editor.editorMouseHandler.isLeftClickReleasedBool() || editor.editorMouseHandler.isRightClickReleasedBool()) {
            mouseReleasedAction();
        }
    }

    /**
     * Handles the logic for mouse press actions within the editor brick field.
     * <p>
     * This method determines the grid cell based on the current mouse position
     * and updates the cell's state according to the mouse button pressed:
     * <p>
     * - If the left mouse button is pressed, sets the cell to the current brick type and marks it alive.
     * - If the right mouse button is pressed, sets the cell to an empty sprite and marks it dead.
     * <p>
     * The method ensures that the cell state is only changed if it is within
     * the valid boundaries and has not been previously altered in the current operation.
     */
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

    /**
     * Handles actions upon releasing the mouse button.
     * <p>
     * Resets the `wasChanged` flag for all elements within the field.
     * Invokes the necessary methods in `editor.editorMouseHandler` to signal
     * the completion of right-click and left-click actions.
     */
    private void mouseReleasedAction(){
        for (int x = 0; x < FIELD_WIDTH; x++){
            for (int y = 0; y < FIELD_HEIGHT; y++){
                wasChanged[x][y] = false;
            }
        }
        editor.editorMouseHandler.rightClickActionDone();
        editor.editorMouseHandler.leftClickActionDone();
    }

    /**
     * Initializes a new game board by resetting each cell's state.
     * <ul>
     *   <li>Sets all cells' active sprites to the index 0.</li>
     *   <li>Sets all cells as not alive.</li>
     *   <li>Resets the save file to null.</li>
     * </ul>
     */
    public void newBoard(){
        for (int x = 0; x < FIELD_WIDTH; x++){
            for (int y = 0; y < FIELD_HEIGHT; y++){
                field[x][y].setActiveSprite(0);
                field[x][y].setAlive(false);
            }
        }
        saveFile = null;
    }

    /**
     * Saves the current state of the brick field to a file.
     * <p>
     * This method captures the active sprite states of the bricks on the field
     * into a 2D array and writes this state to a file. If the board has not been
     * previously saved, it prompts the user to specify a save location using the
     * saveAs() method. If a save file is already specified, it writes the state
     * directly to that file.
     * <p>
     * The state of each brick is represented by an integer, which corresponds to
     * the active sprite index of that brick.
     * <p>
     * The method handles exceptions that may occur during the file write process
     * and prints the stack trace if any exception is caught.
     */
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

    /**
     * Saves the current state of the brick field to a file. This method captures the state of all bricks,
     * opens a file chooser for the user to select or specify a file location, and then writes the state
     * to the selected file.
     * <p>
     * The method will:
     * 1. Create a 2D array representing the current state of the bricks.
     * 2. Open a save file dialog for the user to specify the file location.
     * 3. Write the 2D array to the specified file using object serialization.
     */
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

    /**
     * Opens a file chooser dialog for the user to select a file and loads the
     * selected file into the editor.
     * <ul>
     *   <li>Displays an open dialog using a {@link JFileChooser} instance.</li>
     *   <li>If the user approves the file selection, retrieves the selected file.</li>
     *   <li>Loads the state from the selected file into the editor.</li>
     * </ul>
     */
    public void openBoard(){
        JFileChooser fileChooser = getjFileChooser();
        if (fileChooser.showOpenDialog(editor) == JFileChooser.APPROVE_OPTION) {
            saveFile = fileChooser.getSelectedFile();
            load(saveFile);
        }
    }

    /**
     * Creates a JFileChooser configured to select files from the "src/levels" directory.
     * The file chooser is set to only accept files with a ".save" extension and directories.
     *
     * @return a configured JFileChooser instance for selecting ".save" files.
     */
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
