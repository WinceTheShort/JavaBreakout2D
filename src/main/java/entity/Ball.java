package entity;

import game.GameBrickField;
import org.example.GParams;
import util.Sprite;

import java.awt.*;

public class Ball extends Sprite {
    private int xSpeed;
    private int ySpeed;

    boolean playerCollision = false;
    boolean topCollision = false;
    boolean sideCollision = false;
    boolean bottomCollision = false;

    int[][] startSpeeds = {{-3,-1}, {-2, -2}, {-1, -3}, {1, -3}, {2, -2}, {3, -1}};
    int currentSpeed = 0;
    double speedScale;

    public Ball(double speedScale) {
        this.speedScale = speedScale;
    }
    public void update(Player player) {
        if (x < 0 || x > GParams.SCREEN_WIDTH - width || sideCollision) {
            xSpeed *= -1;
        }
        x += xSpeed;
        if (y < 0 || y > GParams.SCREEN_HEIGHT * 0.95 - height || playerCollision || topCollision) {
            ySpeed *= -1;
            if (playerCollision && (xSpeed < 0 && player.isMovingLeft || xSpeed > 0 && player.isMovingRight)) {
                xSpeed *= -1;
            }
            if (y > GParams.SCREEN_HEIGHT * 0.95 - (GParams.SCREEN_HEIGHT * 0.95 - player.getY()) - height) {
                if (y > GParams.SCREEN_HEIGHT * 0.95 - (GParams.SCREEN_HEIGHT * 0.95 - player.getY())) {
                    bottomCollision = true;
                } else if (playerCollision) {
                    y = (int) (player.getY() - height);
                }
            }
        }
        y += ySpeed;
        setBounds(x, y, width, height);
    }

    @Override
    public void draw(Graphics2D g2d) {

        g2d.setColor(Color.ORANGE);
        g2d.fillOval(x, y, width, height);
    }

    public void drawPointer(Graphics2D g2d) {
        g2d.setColor(Color.white);
        int lineX = x + width / 2;
        int lineY = y + height / 2;
        g2d.drawLine(lineX, lineY, lineX + width * xSpeed, lineY + height * ySpeed);
    }

    public void checkCollision(Player player, GameBrickField gameBrickField){
        if (y + height > player.getY() - height)
            playerCollision = player.getBounds().intersects(this.getBounds());
        topCollision = gameBrickField.checkTopCollision(this);
        sideCollision = gameBrickField.checkSideCollision(this);
    }

    public void rotateLeft(){
        if (currentSpeed > 0) currentSpeed--;
        setSpeed();
    }
    public void rotateRight(){
        if (currentSpeed < startSpeeds.length - 1) currentSpeed++;
        setSpeed();
    }

    private void setSpeed() {
        xSpeed = (int)(startSpeeds[currentSpeed][0] * speedScale);
        ySpeed = (int)(startSpeeds[currentSpeed][1] * speedScale);
    }

    public void resetSpeed(){
        currentSpeed = 0;
        setSpeed();
    }

    public boolean isBottomCollision() {
        if (bottomCollision){
            bottomCollision = false;
            return true;
        }
        return false;
    }
}
