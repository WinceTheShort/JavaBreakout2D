package util;

import java.awt.*;
import java.io.Serializable;
import java.util.Arrays;

public class SpriteSheet extends Rectangle implements Serializable {
    protected transient Sprite[] sprites;
    protected int activeSprite = 0;
    protected int spriteNum = 0;

    public SpriteSheet(Texture texture, int width, int height) {
        super(width, height);
        sprites = new Sprite[height*width];
        for (int i = 0; i <  texture.getWidth()/width; i++) {
            for (int j = 0; j < texture.getHeight()/height; j++) {
                sprites[i+j] = new Sprite (new TextureRegion(texture, i*height, j*width, width, height));
                spriteNum++;
            }
        }
    }
    public void draw(Graphics2D g2d) {
        sprites[activeSprite].setBounds(x, y, width, height);
        sprites[activeSprite].draw(g2d);
    }
    public void nextSprite() {
        if (activeSprite != spriteNum-1) {
            activeSprite++;
        } else activeSprite = 0;
    }
    public void prevSprite() {
        if (activeSprite != 0)
            activeSprite--;
        else activeSprite = spriteNum-1;
    }

    public Sprite getSprite() {
        return sprites[activeSprite];
    }
    public int getSpriteNum() {
        return spriteNum;
    }
    public int getActiveSprite(){
        return activeSprite;
    }
    public void setActiveSprite(int activeSprite) {
        if (activeSprite < 0 || activeSprite >= sprites.length) return;
        this.activeSprite = activeSprite;
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof SpriteSheet s){
            return (spriteNum == s.spriteNum && activeSprite == s.activeSprite && Arrays.equals(sprites, s.sprites) && super.equals(o));
        }
        return super.equals(o);
    }
}
