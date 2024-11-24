package util;

import java.awt.*;

/**
 * This class represents a Sprite that is a drawable texture region.
 */
public class Sprite extends TextureRegion{

    /**
     * Default constructor for Sprite.
    */
    public Sprite() {
        super();
    }
    /**
    * Constructs a Sprite with the specified Texture.
    *
    * @param texture the texture to be used for the sprite
    */
    public Sprite(Texture texture) {
        super(texture);
    }
    /**
    * Constructs a Sprite with the specified Texture and dimensions.
    *
    * @param texture the texture to be used for the sprite
    * @param x the x-coordinate of the sprite
    * @param y the y-coordinate of the sprite
    * @param width the width of the sprite
    * @param height the height of the sprite
    */
    public Sprite(Texture texture, int x, int y, int width, int height) {
        super(texture);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    /**
    * Constructs a Sprite with the specified TextureRegion.
    *
    * @param textureRegion the texture region to be used for the sprite
    */
    public Sprite(TextureRegion textureRegion) {
        super(textureRegion);
    }
    /**
    * Constructs a Sprite with the specified TextureRegion and dimensions.
    *
    * @param region the texture region to be used for the sprite
    * @param x the x-coordinate of the sprite
    * @param y the y-coordinate of the sprite
    * @param width the width of the sprite
    * @param height the height of the sprite
    */
    public Sprite(TextureRegion region, int x, int y, int width, int height) {
        super(region);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    //Getters and setters

    /**
    * Draws the sprite using the specified Graphics context.
    *
    * @param g the Graphics context to be used for drawing
    */
    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }
    /**
    * Draws the sprite using the specified Graphics2D context.
    *
    * @param g2d the Graphics2D context to be used for drawing
    */
    public void draw(Graphics2D g2d){
        g2d.drawImage(image, x, y, width, height, null);
    }
}
