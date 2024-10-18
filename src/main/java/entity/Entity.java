package entity;

import util.Sprite;
import util.Texture;
import util.TextureRegion;

public class Entity extends Sprite {
    protected int speed = 0;

    public Entity() {
        super();
    }
    public Entity(Texture texture) {
        super(texture);
    }
    public Entity(TextureRegion textureRegion) {
        super(textureRegion);
    }
    public Entity(Sprite sprite){
        super(sprite);
    }

    //Getters and setters
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
