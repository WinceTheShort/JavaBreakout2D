package util;

import java.awt.*;

public class Sprite extends TextureRegion{

    public Sprite() {
        super();
    }
    public Sprite(Texture texture) {
        super(texture);
    }
    public Sprite(TextureRegion textureRegion) {
        super(textureRegion);
    }

    //Getters and setters

    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }
    public void draw(Graphics2D g2d){
        g2d.drawImage(image, x, y, width, height, null);
    }
}
