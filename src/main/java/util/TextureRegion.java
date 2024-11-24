package util;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Rectangular region of a texture
 */
public class TextureRegion extends Rectangle {
    protected transient BufferedImage image = null;

    //Constructors
/**
 * Default constructor that creates an empty TextureRegion.
 */
public TextureRegion() {}

/**
 * Constructs a TextureRegion from a given Texture, defined by a rectangular region.
 *
 * @param texture the texture to extract the region from
 * @param x the x-coordinate of the upper-left corner of the region
 * @param y the y-coordinate of the upper-left corner of the region
 * @param width the width of the region
 * @param height the height of the region
 */
public TextureRegion(Texture texture, int x, int y, int width, int height) {
    image = texture.getSubimage(x, y, width, height);
    this.width = width;
    this.height = height;
}

/**
 * Copy constructor that creates a new TextureRegion from an existing one.
 *
 * @param region the texture region to copy
 */
public TextureRegion(TextureRegion region) {
    this.image = region.image;
    this.width = region.width;
    this.height = region.height;
}

/**
 * Constructs a TextureRegion that covers the entire texture.
 *
 * @param texture the texture to extract the region from
 */
public TextureRegion(Texture texture) {
    this.image = texture.getSubimage(0, 0, texture.getWidth(), texture.getHeight());
    this.width = texture.getWidth();
    this.height = texture.getHeight();
}

    //Getters and setters
/**
 * Gets the BufferedImage of this TextureRegion.
 *
 * @return the image of this TextureRegion
 */
public BufferedImage getImage() {
    return image;
}

/**
 * Sets the texture for this TextureRegion to the entire given texture.
 *
 * @param texture the new texture for this region
 */
public void setTexture(Texture texture) {
    this.image = texture.getSubimage(0, 0, texture.getWidth(), texture.getHeight());
}

}
