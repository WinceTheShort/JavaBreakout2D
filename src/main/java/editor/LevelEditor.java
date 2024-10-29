package editor;

import com.formdev.flatlaf.FlatDarculaLaf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class LevelEditor {

    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(LevelEditor.class);

        try {
            UIManager.setLookAndFeel( new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            logger.error(e.getMessage());
        }

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Editor");

        BrickFieldPanel brickFieldPanel = new BrickFieldPanel(frame, null, null);
        frame.add(brickFieldPanel);
        frame.pack();

        brickFieldPanel.startEditorThread();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
