package entity;

import game.KeyHandler;
import game.GamePanel;
import org.example.GParams;
import util.Texture;
import util.TextureRegion;

import java.awt.event.KeyEvent;

public class Player extends Entity {

    protected GamePanel gamePanel;
    protected transient KeyHandler keyHandler;
    public boolean isMovingLeft = false;
    public boolean isMovingRight = false;

    public Player(GamePanel gamePanel, KeyHandler keyHandler, Texture texture) {
        super(texture);
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        setDefaultValues();
    }
    public Player(GamePanel gamePanel, KeyHandler keyHandler, TextureRegion textureRegion){
        super(textureRegion);
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        setDefaultValues();
    }

    private void setDefaultValues(){
        width = GParams.gridWidth * 2;
        height = GParams.gridHeight * 2;
        x = GParams.fieldDimension.width / 2 - height / 2;
        y = (int)(GParams.SCREEN_HEIGHT * 0.95 - GParams.gridHeight * 1.5);
        speed = (int)(width * 0.05);
    }
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
            if (keyHandler.keyTyped[KeyEvent.VK_A]){
                ball.rotateLeft();
                keyHandler.keyTyped[KeyEvent.VK_A] = false;
            }
            if (keyHandler.keyTyped[KeyEvent.VK_D]){
                ball.rotateRight();
                keyHandler.keyTyped[KeyEvent.VK_D] = false;
            }
        }

        setBounds(x, y, width, height);
    }

    public void resetPosition(){
        x = GParams.fieldDimension.width / 2 - width / 2;
    }
}
