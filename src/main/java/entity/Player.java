package entity;

import game.KeyHandler;
import game.GamePanel;
import org.example.GParams;
import util.Texture;
import util.TextureRegion;

import java.awt.event.KeyEvent;

/**
 * Represents a player in the game.
 */
public class Player extends Entity {

    protected GamePanel gamePanel;
    protected transient KeyHandler keyHandler;
    public boolean isMovingLeft = false;
    public boolean isMovingRight = false;

    /**
     * Constructs a new Player with a specified GamePanel, KeyHandler, and Texture.
     *
     * @param gamePanel the game panel where the player is displayed
     * @param keyHandler the key handler to manage the player's key events
     * @param texture the texture of the player
     */
    public Player(GamePanel gamePanel, KeyHandler keyHandler, Texture texture) {
        super(texture);
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        setDefaultValues();
    }

    /**
     * Constructs a new Player with a specified GamePanel, KeyHandler, and TextureRegion.
     *
     * @param gamePanel the game panel where the player is displayed
     * @param keyHandler the key handler to manage the player's key events
     * @param textureRegion the texture region of the player
     */
    public Player(GamePanel gamePanel, KeyHandler keyHandler, TextureRegion textureRegion){
        super(textureRegion);
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        setDefaultValues();
    }

    /**
     * Sets the default values for the player's attributes.
     */
    private void setDefaultValues(){
        width = GParams.gridWidth * 2;
        height = GParams.gridHeight * 2;
        x = GParams.fieldDimension.width / 2 - height / 2;
        y = (int)(GParams.SCREEN_HEIGHT * 0.95 - GParams.gridHeight * 1.5);
        speed = (int)(width * 0.05);
    }

    /**
     * Updates the player's state based on the key events and game state.
     *
     * @param ball the ball object to be updated in the game
     */
    public void update(Ball ball){

        if (gamePanel.levelStarted) {
            if (keyHandler.keys[KeyEvent.VK_D] && x < GParams.SCREEN_WIDTH - width){
                x += speed;
                isMovingRight = true;
            } else isMovingRight = false;
            if (keyHandler.keys[KeyEvent.VK_A] && x > 0){
                x -= speed;
                isMovingLeft = true;
            } else isMovingLeft = false;
        } else {
            if (keyHandler.keys[KeyEvent.VK_SPACE]) gamePanel.levelStarted = true;
            if (keyHandler.keyTypedBooleans[KeyEvent.VK_A]){
                ball.rotateLeft();
                keyHandler.keyTypedBooleans[KeyEvent.VK_A] = false;
            }
            if (keyHandler.keyTypedBooleans[KeyEvent.VK_D]){
                ball.rotateRight();
                keyHandler.keyTypedBooleans[KeyEvent.VK_D] = false;
            }
        }

        setBounds(x, y, width, height);
    }

    /**
     * Resets the player's position to the default starting position.
     */
    public void resetPosition(){
        x = GParams.fieldDimension.width / 2 - width / 2;
    }
}
