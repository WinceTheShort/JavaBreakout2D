package entity;

import util.Sprite;
import util.SpriteSheet;
import util.Texture;
import util.TextureRegion;

import java.awt.*;
import java.io.Serializable;


public class Brick extends SpriteSheet implements Serializable {
    private static final String BRICK_SPRITES_PNG = "src/images/brickSprites.png";
    private static final int BRICK_SPRITES_SIZE = 16;

    public Rectangle hitbox;

    protected boolean isAlive;

    public boolean isWall() {
        return isWall;
    }

    public void setWall(boolean wall) {
        isWall = wall;
    }

    protected boolean isWall;

    public Brick() {
        super(new Texture(BRICK_SPRITES_PNG), BRICK_SPRITES_SIZE, BRICK_SPRITES_SIZE);
        width = 32;
        height = 32;
    }
    public Brick(int x, int y) {
        super(new Texture(BRICK_SPRITES_PNG), BRICK_SPRITES_SIZE, BRICK_SPRITES_SIZE);
        this.x = x;
        this.y = y;
        width = 32;
        height = 32;
    }
    public Brick(int x, int y, int width, int height) {
        super(new Texture(BRICK_SPRITES_PNG), BRICK_SPRITES_SIZE, BRICK_SPRITES_SIZE);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        hitbox = new Rectangle(x, y, width, height / 2);
    }

    public boolean isAlive() {
        return isAlive;
    }
    public void setAlive(boolean alive) {
        isAlive = alive;
    }
    public void reloadTexture() {
        Texture texture = new Texture(BRICK_SPRITES_PNG);
        sprites = new Sprite[BRICK_SPRITES_SIZE*BRICK_SPRITES_SIZE];
        for (int i = 0; i <  texture.getWidth()/BRICK_SPRITES_SIZE; i++) {
            for (int j = 0; j < texture.getHeight()/BRICK_SPRITES_SIZE; j++) {
                sprites[i+j] = new Sprite (new TextureRegion(texture, i*BRICK_SPRITES_SIZE, j*BRICK_SPRITES_SIZE, BRICK_SPRITES_SIZE, BRICK_SPRITES_SIZE));
            }
        }
    }

    public int damage(){
        if (activeSprite > 4 || activeSprite < 1) return 0;
        activeSprite--;
        if (activeSprite < 1) isAlive = false;
        return switch (activeSprite){
            case 0 -> 100;
            case 1 -> 40;
            case 2 -> 20;
            case 3 -> 10;
            default -> 0;
        };
    }
}
