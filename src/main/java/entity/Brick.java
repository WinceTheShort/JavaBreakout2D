package entity;

import util.Sprite;
import util.SpriteSheet;
import util.Texture;
import util.TextureRegion;

import java.io.Serializable;


public class Brick extends SpriteSheet implements Serializable {
    protected boolean isAlive;
    private static final String BRICK_SPRITES_PNG = "src/images/brickSprites.png";
    private static final int BRICK_SPRITES_SIZE = 16;

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

    @Override
    public boolean equals(Object o) {
        if (o instanceof Brick b) {
            return (isAlive == b.isAlive && super.equals(o));
        }
        else return super.equals(o);
    }
}
