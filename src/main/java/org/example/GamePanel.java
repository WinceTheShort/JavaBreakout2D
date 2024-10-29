package org.example;

import entity.Player;
import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.example.GParams.*;

public class GamePanel extends StatePanel implements Runnable, ActionListener {

    transient Logger logger = LoggerFactory.getLogger(GamePanel.class);


    //Screen settings
    public static final int EXTRA_HEIGHT = 5;
    private static final int SCREEN_WIDTH = FIELD_WIDTH*GRID_WIDTH;
    private static final int SCREEN_HEIGHT = FIELD_HEIGHT*(GRID_HEIGHT+EXTRA_HEIGHT);

    //FPS
    int fps = 120;

    private transient Thread gameThread;
    private final transient KeyHandler keyHandler = new KeyHandler();
    //Creates the grid sprite to match the grid and field sizes
    private final transient Sprite grid = new Sprite(new TextureRegion(new Texture("src/images/editorGrid.png"),0,0,32*FIELD_WIDTH,16*FIELD_HEIGHT),
            0, 0, GRID_WIDTH * FIELD_WIDTH, GRID_HEIGHT * FIELD_HEIGHT);
    //Creates brick field
    private final transient GameBrickField brickField = new GameBrickField();
    //Creates player
    private final transient Player player = new Player(this, keyHandler, new TextureRegion(new Texture("src/images/brickSprites.png"), 16, 0, 16, 16));
    private final transient CustomButton button = new CustomButton(50,50);


    public GamePanel(JFrame frame, MenuPanel menuPanel, AnimPanel animPanel) {
        super(frame);

        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);
        addKeyListener(keyHandler);


        MigLayout migLayout = new MigLayout(
                "wrap 1"
        );
        setLayout(migLayout);
        add(button);
        button.addActionListener(this);
        button.addActionListener(menuPanel);
        button.addActionListener(animPanel);
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

        while(inState){
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
                logger.debug("FPS: {}", frames);
                frames = 0;
                frameTimer = 0;
            }

        }
        System.out.println("quit game state");
    }

    public void update(){
        player.update();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        grid.draw(g2d);
        player.draw(g2d);
        repaint();

        g2d.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        inState = !inState;

        if(inState){
            startGameThread();
            frame.add(this, BorderLayout.CENTER);
            requestFocusInWindow();
            repaint();

        } else {
            frame.remove(this);
        }

        frame.repaint();
    }
}
