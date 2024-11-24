package game;

import entity.Ball;
import entity.Brick;
import entity.BrickField;


import java.awt.geom.Rectangle2D;
import java.io.File;

public class GameBrickField extends BrickField {
    private final GamePanel panel;

    public GameBrickField(GamePanel panel) {
        super();
        this.panel = panel;
    }

    @Override
    public void load(File file) {
        super.load(file);
    }

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
    private void brickDamage(Brick brick, GamePanel panel){
        panel.updateScore(brick.damage());
        if (!brick.isAlive() && !brick.isWall()) brickDied();
    }
}
