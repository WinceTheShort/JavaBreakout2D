package editor;

import com.formdev.flatlaf.FlatDarculaLaf;
import org.example.GParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LevelEditor {

    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(LevelEditor.class);

        try {
            UIManager.setLookAndFeel( new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            logger.error(e.getMessage());
        }

        //Registers my custom font
        try {
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/minepixel.ttf")));
        } catch (IOException | FontFormatException e) {
            logger.error(e.getMessage());
        }

        GParams.setParams();

        //Creates JFrame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setLayout(new BorderLayout());

        EditorPanel editorPanel = new EditorPanel(frame,null, null);
        frame.add(editorPanel, BorderLayout.CENTER);
        frame.pack();


        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
