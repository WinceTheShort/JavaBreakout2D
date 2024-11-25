package entity;

import game.GameBrickField;
import org.example.GParams;
import util.Sprite;

import java.awt.*;

/**
 * Represents a ball entity in the game which extends the Sprite class.
 * Handles movement and collision with player and bricks.
 *
 */
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

    /**
     * Constructor to initialize the Ball with a specific speed scale.
     *
     */
    public Ball() {
        this.speedScale = (double) GParams.gridHeight / 38;
    }

    /**
     * Updates the ball's position based on collisions and player movements.
     *
     * @param player the player object to check collisions with
     */
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

    /**
     * Draws the ball on the screen.
     *
     * @param g2d the Graphics2D object to draw the ball
     */
    @Override
    public void draw(Graphics2D g2d) {

        g2d.setColor(Color.ORANGE);
        g2d.fillOval(x, y, width, height);
    }

    /**
     * Draws a pointer line originating from the center of the ball to indicate the direction of movement.
     *
     * @param g2d the Graphics2D object to draw the pointer
     */
    public void drawPointer(Graphics2D g2d) {
        g2d.setColor(Color.white);
        int lineX = x + width / 2;
        int lineY = y + height / 2;
        g2d.drawLine(lineX, lineY, lineX + width * xSpeed, lineY + height * ySpeed);
    }

    /**
     * Checks collisions with the player and game brick field.
     *
     * @param player the player to check collision with
     * @param gameBrickField the game brick field to check collision with
     */
    public void checkCollision(Player player, GameBrickField gameBrickField){
        if (y + height > player.getY() - height)
            playerCollision = player.getBounds().intersects(this.getBounds());
        topCollision = gameBrickField.checkTopCollision(this);
        sideCollision = gameBrickField.checkSideCollision(this);
    }

    /**
     * Rotates the ball's speed vector to the left.
     */
    public void rotateLeft(){
        if (currentSpeed > 0) currentSpeed--;
        setSpeed();
    }

    /**
     * Rotates the ball's speed vector to the right.
     */
    public void rotateRight(){
        if (currentSpeed < startSpeeds.length - 1) currentSpeed++;
        setSpeed();
    }

    /**
     * Sets the ball's horizontal and vertical speed based on the current speed index and speed scale.
     * The speeds are adjusted from the predefined starting speeds.
     */
    private void setSpeed() {
        xSpeed = (int)(startSpeeds[currentSpeed][0] * speedScale);
        ySpeed = (int)(startSpeeds[currentSpeed][1] * speedScale);
    }

    /**
     * Resets the ball's speed to the initial value.
     */
    public void resetSpeed(){
        currentSpeed = 0;
        setSpeed();
    }

    /**
     * Checks if the ball has collided with the bottom of the screen.
     *
     * @return true if the ball collided with the bottom, false otherwise
     */
    public boolean isBottomCollision() {
        if (bottomCollision){
            bottomCollision = false;
            return true;
        }
        return false;
    }
}
