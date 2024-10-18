package editor;

import javax.swing.*;

public class LevelEditor {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Editor");

        EditorPanel editorPanel = new EditorPanel();
        frame.add(editorPanel);
        frame.pack();

        editorPanel.stratEditorThread();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
