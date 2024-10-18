package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Game");

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.pack();

        gamePanel.startGameThread();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
}