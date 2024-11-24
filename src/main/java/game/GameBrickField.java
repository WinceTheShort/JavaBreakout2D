package game;

import entity.Ball;
import entity.Brick;
import entity.BrickField;


import java.awt.geom.Rectangle2D;
import java.io.File;

/**
 * GameBrickField is a specialized field for managing bricks in the game.
 * It extends BrickField and includes methods for collision detection and scoring.
 */
public class GameBrickField extends BrickField {
    private final GamePanel panel;

    /**
     * Constructor for GameBrickField.
     *
     * @param panel The GamePanel instance that manages the user interface and score updating.
     */
    public GameBrickField(GamePanel panel) {
        super();
        this.panel = panel;
    }

    /**
     * Loads the brick field from a specified file.
     *
     * @param file The file from which to load the brick field.
     */
    @Override
    public void load(File file) {
            super.load(file);
        }

    /**
     * Checks for a collision between the ball and the top of any bricks.
     *
     * @param ball The ball used in the collision detection.
     * @return true if a top collision occurred; false otherwise.
     */
    public boolean checkTopCollision(Ball ball){
        for (Brick[] bricks : field){
            for (Brick brick : bricks){
                if ((brick.isAlive() || brick.isWall()) && brick.hitbox.intersects(ball.getBounds())){
                    Rectangle2D intersection = brick.hitbox.createIntersection(ball.getBounds());
                    if (intersection.getWidth() > intersection.getHeight()){
                        brickDamage(brick, panel);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     * Checks for a collision between the ball and the sides of any bricks.
     *
     * @param ball The ball used in the collision detection.
     * @return true if a side collision occurred; false otherwise.
     */
    public boolean checkSideCollision(Ball ball){
        for (Brick[] bricks : field){
            for (Brick brick : bricks){
                if ((brick.isAlive() || brick.isWall()) && brick.hitbox.intersects(ball.getBounds())){
                    Rectangle2D intersection = brick.hitbox.createIntersection(ball.getBounds());
                    if (intersection.getHeight() > intersection.getWidth()){
                        brickDamage(brick, panel);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     * Applies damage to a brick and updates the panel's score.
     * If the brick is destroyed, triggers the brickDied method.
     *
     * @param brick The brick to which damage is to be applied.
     * @param panel The GamePanel instance that manages the score updating.
     */
    private void brickDamage(Brick brick, GamePanel panel){
        panel.updateScore(brick.damage());
        if (!brick.isAlive() && !brick.isWall()) brickDied();
    }
}
