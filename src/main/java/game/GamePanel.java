package game;

import entity.Ball;
import entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static org.example.GParams.*;
import static org.example.ActionEventIDs.*;

public class GamePanel extends JPanel implements Runnable, ActionListener {

    transient Logger logger = LoggerFactory.getLogger(GamePanel.class);

    //FPS
    int fps = 120;

    //Game state booleans
    private boolean inGame = false;
    public boolean levelStarted = false;

    private int lives = 0;

    private final transient GameContainerPanel gameContainerPanel;
    private transient GameBrickField gameBrickField;
    private transient Player player;
    private transient Ball ball;
    private transient LevelManager levelManager;

    boolean singleLevel = false;
     transient LevelManager.Level level;

    public GamePanel(LabelManager labelManager, GameContainerPanel gameContainerPanel) {
        this.gameContainerPanel = gameContainerPanel;

        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.GREEN.darker());
        setDoubleBuffered(true);
        setFocusable(true);

        addActionListener(labelManager);

        initComponents();
    }
    private void initComponents() {
        Sprite grid;
        KeyHandler keyHandler = new KeyHandler();
        addKeyListener(keyHandler);

        //Creates the grid sprite to match the grid and field sizes
        grid = new Sprite(new TextureRegion(new Texture("src/images/editorGrid.png"),0,0,32*FIELD_WIDTH,16*FIELD_HEIGHT),
                0, 0, gridWidth * FIELD_WIDTH, gridHeight * FIELD_HEIGHT);
        grid.setLocation((int) ((double) SCREEN_WIDTH / 2 - grid.getWidth() / 2), 0);

        //Creates brick field
        gameBrickField = new GameBrickField(this);

        //Creates player
        player = new Player(this, keyHandler, new TextureRegion(new Texture("src/images/brickSprites.png"), 16, 0, 16, 16));

        ball = new Ball(2);

    }
    public void startGameThread(){
        Thread gameThread = new Thread(this);

        setLevelStartState();
        lives = 3;
        updateLives(lives);

        levelManager = new LevelManager();

        if (singleLevel) {
            gameBrickField.load(level.getLevelFile());
            updateLevel(level.getLevelName());
        } else {
            gameBrickField.load(levelManager.getLevel().getLevelFile());
            updateLevel(levelManager.getLevel().getLevelName());
        }


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

        while(inGame){
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
        player.update(ball);
        if (levelStarted) {
            ball.checkCollision(player, gameBrickField);
            ball.update(player);
            if (ball.isBottomCollision()){
                setLevelStartState();
                lives--;
                updateLives(lives);
            }
            if (!singleLevel && gameBrickField.isClear()){
                setLevelStartState();
                levelManager.nextLevel();
                if (!levelManager.noMoreLevels()) gameBrickField.load(levelManager.getLevel().getLevelFile());
                else gameContainerPanel.youWin(levelManager.getLevelId());
                updateLevel(levelManager.getLevel().getLevelName());
            } else if (singleLevel && gameBrickField.isClear()) {
                gameContainerPanel.leaveGame();
            }
        }
        if (!singleLevel && lives < 0){
            gameContainerPanel.gameOver(levelManager.getLevelId());
        } else if (singleLevel && lives < 0){
            gameContainerPanel.leaveGame();
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        gameBrickField.draw(g2d);
        player.draw(g2d);
        if (!levelStarted) {
            ball.drawPointer(g2d);
        }
        ball.draw(g2d);

        g2d.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        inGame = !inGame;

        if (inGame) {
            startGameThread();
            requestFocusInWindow();
            repaint();
        }
    }

    private final transient ArrayList<ActionListener> listeners = new ArrayList<>();

    public void addActionListener(ActionListener listener){
        listeners.add(listener);
    }
    private void notifyListeners(ActionEvent evt){
        for (ActionListener listener : listeners) {
            listener.actionPerformed(evt);
        }
    }
    public void updateLevel(String level){
        notifyListeners(new ActionEvent(this, LEVEL_CHANGED, level));
    }
    public void updateScore(Integer score){
        notifyListeners(new ActionEvent(this, SCORE_CHANGED, score.toString()));
    }
    public void updateLives(Integer lives){
        notifyListeners(new ActionEvent(this, LIVES_CHANGED, lives.toString()));
    }
    private void setLevelStartState(){
        levelStarted = false;
        player.resetPosition();

        int ballX = player.x + player.width/2 - gridHeight/4;
        int ballY = player.y - gridHeight/2;
        ball.setBounds(ballX, ballY, gridHeight / 2,gridHeight / 2);
        ball.resetSpeed();

        updateScore(0);
    }

    public void loadSingleLevel(LevelManager.Level level){
        this.level = level;
        singleLevel = true;
    }
}
