package org.example;

import com.formdev.flatlaf.FlatDarculaLaf;
import menu.MenuPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * The Main class serves as the entry point for the application.
 * It initializes the look and feel, registers a custom font,
 * sets application parameters, and creates the main application window.
 */
public class Main {

    /**
     * The main method which acts as the entry point for the application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {

        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Main.class);

        //Activates FlatDraculaLaf look and feel
        try {
            UIManager.setLookAndFeel( new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            logger.error(e.getMessage());
        }

        //Registers my custom font
        try {
            /*
             * Registers the custom font by loading it from the specified path.
             * If an error occurs during this process, it is logged.
             */
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


        MenuPanel menuPanel = new MenuPanel(frame);
        frame.add(menuPanel, BorderLayout.CENTER);
        frame.pack();

        //Displays frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
}