package entity;

import game.KeyHandler;
import game.GamePanel;
import util.Texture;
import util.TextureRegion;

import java.awt.event.KeyEvent;

public class Player extends Entity {

    protected GamePanel gamePanel;
    protected transient KeyHandler keyHandler;

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
        x = 100;
        y = 100;
        height = 36;
        width = 36;
        speed = 3;
    }
    public void update(){
        if (keyHandler.keys[KeyEvent.VK_W]) y -= speed;
        if (keyHandler.keys[KeyEvent.VK_S]) y += speed;
        if (keyHandler.keys[KeyEvent.VK_D]) x += speed;
        if (keyHandler.keys[KeyEvent.VK_A]) x -= speed;
    }
}
