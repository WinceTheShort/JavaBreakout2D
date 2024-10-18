package util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TextureRegion extends Rectangle {
    protected transient BufferedImage image = null;

    //Constructors
    public TextureRegion() {}
    public TextureRegion(Texture texture, int x, int y, int width, int height) {
        image = texture.getSubimage(x, y, width, height);
        this.width = width;
        this.height = height;
    }
    public TextureRegion(TextureRegion region) {
        this.image = region.image;
        this.width = region.width;
        this.height = region.height;
    }
    public TextureRegion(Texture texture) {
        this.image = texture.getSubimage(0, 0, texture.getWidth(), texture.getHeight());
        this.width = texture.getWidth();
        this.height = texture.getHeight();
    }

    //Getters and setters
    public BufferedImage getImage() {
        return image;
    }
    public void setTexture(Texture texture) {
        this.image = texture.getSubimage(0, 0, texture.getWidth(), texture.getHeight());
    }

}
