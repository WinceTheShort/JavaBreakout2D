package entity;

import util.SpriteSheet;
import util.Texture;



public class Brick extends SpriteSheet {
    protected boolean isAlive;

    public Brick(int x, int y) {
        super(new Texture("src/images/brickSprites.png"), 16, 16);
        this.x = x;
        this.y = y;
        width = 32;
        height = 32;
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
