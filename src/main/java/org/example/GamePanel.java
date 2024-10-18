package org.example;

import entity.Player;
import util.Texture;
import util.TextureRegion;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    //Screen settings
    public static final int GRID_WIDTH = 32;
    public static final int GRID_HEIGHT = 16;
    private static final int SCREEN_WIDTH = 40*GRID_WIDTH;
    private static final int SCREEN_HEIGHT = 45*GRID_HEIGHT;

    //FPS
    int fps = 120;

    private transient Thread gameThread;
    private transient KeyHandler keyHandler = new KeyHandler();
    private transient Player player = new Player(this, keyHandler, new TextureRegion(new Texture("src/images/brickSprites.png"), 16, 0, 16, 16));


    public GamePanel(){

        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);
        addKeyListener(keyHandler);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = (double) 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long startTime;

        long frameTimer = 0;
        int frames = 0;

        while(gameThread.isAlive()){
            startTime = System.nanoTime();
            delta += (startTime - lastTime) / drawInterval;
            frameTimer += startTime - lastTime;
            lastTime = startTime;

            if(delta >= 1){
                //Update assets
                update();

                //Display updated assets
                repaint();

                delta--;
                frames++;
            }


            if (frameTimer >= 1000000000){
                System.out.println("FPS: " + frames);
                frames = 0;
                frameTimer = 0;
            }

        }
    }

    public void update(){
        player.update();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        player.draw(g2d);

        g2d.dispose();
    }
}