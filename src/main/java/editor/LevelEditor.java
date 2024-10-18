package editor;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;

public class LevelEditor {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel( new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

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
