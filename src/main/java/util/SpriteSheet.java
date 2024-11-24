package util;

import java.awt.*;
import java.io.Serializable;

/**
 * A class representing a sprite sheet, which extends the {@link Rectangle} class and implements
 * the {@link Serializable} interface. It manages an array of {@link Sprite} objects and provides
 * functionality to draw and navigate through the sprites.
 */
public class SpriteSheet extends Rectangle implements Serializable {
    /**
     * An array of sprites contained in this sprite sheet.
     */
    protected transient Sprite[] sprites;

    /**
     * The index of the currently active sprite.
     */
    protected int activeSprite = 0;
    /**
     * The number of sprites in this sprite sheet.
     */
    protected int spriteNum = 0;

    /**
     * Constructs a new SpriteSheet with the specified texture, width, and height.
     *
     * @param texture the texture containing the sprite image
     * @param width   the width of each individual sprite
     * @param height  the height of each individual sprite
     */
    public SpriteSheet(Texture texture, int width, int height) {
        super(width, height);
        sprites = new Sprite[height * width];
        for (int i = 0; i < texture.getWidth() / width; i++) {
            for (int j = 0; j < texture.getHeight() / height; j++) {
                sprites[i + j] = new Sprite(new TextureRegion(texture, i * height, j * width, width, height));
                spriteNum++;
            }
        }
    }

    /**
     * Draws the active sprite at the sprite sheet's location using the provided Graphics2D object.
     *
     * @param g2d the Graphics2D object used for drawing
     */
    public void draw(Graphics2D g2d) {
        //Draws the active sprite at the sprite sheets location
        sprites[activeSprite].setBounds(x, y, width, height);
        sprites[activeSprite].draw(g2d);
    }

    /**
     * Advances the active sprite to the next sprite in the sprite sheet. If the active
     * sprite is the last one, it wraps around to the first sprite.
     */
    public void nextSprite() {
        if (activeSprite != spriteNum - 1) {
            activeSprite++;
        } else activeSprite = 0;
    }

    /**
     * Moves the active sprite to the previous sprite in the sprite sheet. If the active
     * sprite is the first one, it wraps around to the last sprite.
     */
    public void prevSprite() {
        if (activeSprite != 0)
            activeSprite--;
        else activeSprite = spriteNum - 1;
    }

    /**
     * Returns the currently active sprite.
     *
     * @return the active {@link Sprite} object
     */
    public Sprite getSprite() {
        return sprites[activeSprite];
    }

    /**
     * Returns the number of sprites in the sprite sheet.
     *
     * @return the number of sprites
     */
    public int getSpriteNum() {
        return spriteNum;
    }

    /**
     * Returns the index of the currently active sprite.
     *
     * @return the index of the active sprite
     */
    public int getActiveSprite() {
        return activeSprite;
    }

    /**
     * Sets the active sprite to the specified index.
     *
     * @param activeSprite the index of the sprite to be made active
     * @throws IllegalArgumentException if the specified index is out of bounds
     */
    public void setActiveSprite(int activeSprite) {
        if (activeSprite < 0 || activeSprite >= sprites.length) return;
        this.activeSprite = activeSprite;
    }
}
