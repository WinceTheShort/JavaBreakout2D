package entity;

import org.example.GParams;
import util.Sprite;

import java.awt.*;

public class Ball extends Sprite {
    private int xSpeed;
    private int ySpeed;

    boolean playerCollision = false;

    public Ball(int xSpeed, int ySpeed) {
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }
    public void update(){
        if (x < 0 || x > GParams.SCREEN_WIDTH - width) {
            xSpeed *= -1;
        }
        x += xSpeed;
        if (y < 0 || y > GParams.SCREEN_HEIGHT * 0.95 - height || playerCollision) {
            ySpeed *= -1;
            playerCollision = false;
        }
        y += ySpeed;
        setBounds(x, y, width, height);
    }

    @Override
    public void draw(Graphics2D g2d) {

        g2d.setColor(Color.ORANGE);
        g2d.fillRect(x, y, width, height);

        g2d.dispose();
    }

    public void checkCollision(Player player, BrickField brickField){
        if (y + height > player.getY() - height)
            playerCollision = player.getBounds().intersects(this.getBounds());
    }
}
