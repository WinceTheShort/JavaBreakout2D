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

/**
 * GamePanel is the primary panel for the game, handling the main game loop, input, and rendering.
 */
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

    /**
     * Constructs a GamePanel with the specified label manager and game container panel.
     *
     * @param labelManager        the label manager handling label events
     * @param gameContainerPanel  the game container panel managing the game display
     */
    public GamePanel(LabelManager labelManager, GameContainerPanel gameContainerPanel) {
        this.gameContainerPanel = gameContainerPanel;

        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.GREEN.darker());
        setDoubleBuffered(true);
        setFocusable(true);

        addActionListener(labelManager);

        initComponents();
    }

    /**
     * Initializes the components of the GamePanel, including the grid sprite, brick field,
     * player, and ball.
     */
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

        ball = new Ball();

    }

    /**
     * Starts the game thread, initializes the game state, and loads the level.
     */
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
            gameBrickField.resetAliveCount();
            updateLevel(levelManager.getLevel().getLevelName());
        }


        gameThread.start();
    }

    /**
     * Runs the main game loop, which updates and repaints the game state based on
     * the current frames per second settings.
     */
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

    /**
     * Updates the game state by moving the player, checking collisions, updating levels,
     * and managing lives.
     */
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

    /**
     * Paints the game components, including the brick field, player, and ball.
     *
     * @param g the Graphics context in which to paint
     */
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

    /**
     * Toggles the game state between running and not running whenever an action event occurs.
     *
     * @param e the action event triggering this method
     */
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

    /**
     * Adds an action listener to listen for game events.
     *
     * @param listener the ActionListener to be added
     */
    public void addActionListener(ActionListener listener){
        listeners.add(listener);
    }

    /**
     * Notifies all registered listeners of the specified action event.
     *
     * @param evt the action event to be propagated
     */
    private void notifyListeners(ActionEvent evt){
        for (ActionListener listener : listeners) {
            listener.actionPerformed(evt);
        }
    }

    /**
     * Updates the current level and notifies listeners of the level change.
     *
     * @param level the new level name
     */
    public void updateLevel(String level){
        notifyListeners(new ActionEvent(this, LEVEL_CHANGED, level));
    }

    /**
     * Updates the current score and notifies listeners of the score change.
     *
     * @param score the new score
     */
    public void updateScore(Integer score){
        notifyListeners(new ActionEvent(this, SCORE_CHANGED, score.toString()));
    }

    /**
     * Updates the number of lives and notifies listeners of the lives change.
     *
     * @param lives the new number of lives
     */
    public void updateLives(Integer lives){
        notifyListeners(new ActionEvent(this, LIVES_CHANGED, lives.toString()));
    }

    /**
     * Resets the state of the level, including the player's position, ball position, and speed.
     */
    private void setLevelStartState(){
        levelStarted = false;
        player.resetPosition();

        int ballX = player.x + player.width/2 - gridHeight/4;
        int ballY = player.y - gridHeight/2;
        ball.setBounds(ballX, ballY, gridHeight / 2,gridHeight / 2);
        ball.resetSpeed();

        updateScore(0);
    }

    /**
     * Loads a single level for gameplay.
     *
     * @param level the level to be loaded
     */
    public void loadSingleLevel(LevelManager.Level level){
        this.level = level;
        singleLevel = true;
    }
}
