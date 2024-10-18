package util;

import java.awt.*;

public class Sprite extends TextureRegion{

    public Sprite() {
        super();
    }
    public Sprite(Texture texture) {
        super(texture);
    }
    public Sprite(Texture texture, int x, int y, int width, int height) {
        super(texture);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public Sprite(TextureRegion textureRegion) {
        super(textureRegion);
    }
    public Sprite(TextureRegion region, int x, int y, int width, int height) {
        super(region);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    //Getters and setters

    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }
    public void draw(Graphics2D g2d){
        g2d.drawImage(image, x, y, width, height, null);
    }
}
