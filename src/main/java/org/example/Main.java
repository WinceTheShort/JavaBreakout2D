package org.example;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {

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
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/minepixel.ttf")));
        } catch (IOException | FontFormatException e) {
            logger.error(e.getMessage());
        }

        GParams gparams = new GParams();

        //Creates JFrame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setLayout(new BorderLayout());


        MenuPanel menuPanel = new MenuPanel(frame);
        frame.add(menuPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        //Displays frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
}