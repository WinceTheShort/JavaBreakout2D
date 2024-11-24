
package entity;


import util.Sprite;
import util.Texture;
import util.TextureRegion;

/**
 * This class represents an entity that extends the Sprite class.
 */
public class Entity extends Sprite {

    /**
     * Speed of the entity.
     */
    protected int speed = 0;

    /**
     * Constructs an empty Entity.
     */
    public Entity() {
        super();
    }

    /**
     * Constructs an Entity with a specified texture.
     *
     * @param texture The texture to be applied to the entity.
     */
    public Entity(Texture texture) {
        super(texture);
    }

    /**
     * Constructs an Entity with a specified texture region.
     *
     * @param textureRegion The texture region to be applied to the entity.
     */
    public Entity(TextureRegion textureRegion) {
        super(textureRegion);
    }

    /**
     * Constructs an Entity with a specified sprite.
     *
     * @param sprite The sprite to be used to create the entity.
     */
    public Entity(Sprite sprite){
        super(sprite);
    }

    // Getters and setters

    /**
     * Gets the speed of the entity.
     *
     * @return the speed of the entity.
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Sets the speed of the entity.
     *
     * @param speed The new speed of the entity.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
