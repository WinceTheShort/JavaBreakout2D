package entity;

import util.SpriteSheet;
import util.Texture;



public class Brick extends SpriteSheet {
    protected boolean isAlive;
    private static final String BRICK_SPRITES_PNG = "src/images/brickSprites.png";

    public Brick() {
        super(new Texture(BRICK_SPRITES_PNG), 16, 16);
        width = 32;
        height = 32;
    }
    public Brick(int x, int y) {
        super(new Texture(BRICK_SPRITES_PNG), 16, 16);
        this.x = x;
        this.y = y;
        width = 32;
        height = 32;
    }
    public Brick(int x, int y, int width, int height) {
        super(new Texture(BRICK_SPRITES_PNG), 16, 16);
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

    @Override
    public boolean equals(Object o) {
        if (o instanceof Brick b) {
            return (isAlive == b.isAlive && super.equals(o));
        }
        else return super.equals(o);
    }
}
