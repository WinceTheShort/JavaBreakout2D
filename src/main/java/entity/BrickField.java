package entity;

import java.awt.*;
import java.io.*;

import static org.example.GParams.*;

/**
 * Represents a field of bricks in a grid layout.
 * Handles the creation, drawing, and loading of brick states.
 */
public class BrickField implements Serializable {
    /**
     * The grid of bricks.
     */
    protected Brick[][] field;
    /**
     * The count of alive bricks.
     */
    int alive = 0;
    /**
     * Constructs a BrickField object and initializes the grid of bricks.
     * The bricks are arranged based on screen and field dimensions defined in GParams.
     */
    public BrickField() {
        int offset = (SCREEN_WIDTH - FIELD_WIDTH * gridWidth) / 2;

        field = new Brick[FIELD_WIDTH][FIELD_HEIGHT];
        for (int x = 0; x < FIELD_WIDTH; x++){
            for (int y = 0; y < FIELD_HEIGHT; y++){
                field[x][y] = new Brick(x * gridWidth + offset, y * gridHeight, gridWidth, gridHeight *2);
            }
        }
    }

    /**
     * Draws the bricks on the provided Graphics2D object.
     *
     * @param g2d the Graphics2D object to draw the bricks on
     */
    public void draw(Graphics2D g2d){
        for (Brick[] bricks : field) {
            for (Brick brick : bricks) {
                brick.draw(g2d);
            }
        }
    }

    /**
     * Loads the state of bricks from the specified file.
     *
     * @param file the file containing the saved state of bricks
     */
    protected void load(File file){
        Integer[][] brickStates = null;
        //Save field
        try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream(file))) {
            brickStates = (Integer[][]) oos.readObject();
        } catch (Exception e){
            e.printStackTrace();
        }
        //Reload bricks textures
        int i = 0;
        for (Brick[] bricks : field){
            int j = 0;
            for (Brick brick : bricks){
                brick.reloadTexture();
                assert brickStates != null;
                brick.setActiveSprite(brickStates[i][j]);
                if (brick.getActiveSprite() == 0){
                    brick.setAlive(false);
                    brick.setWall(false);
                }
                else if (brick.getActiveSprite() == 5){
                    brick.setAlive(false);
                    brick.setWall(true);
                }
                else {
                    brick.setWall(false);
                    brick.setAlive(true);
                    brick.reloadTexture();
                    alive++;
                }
                j++;
            }
            i++;
        }
    }

    /**
     * Checks if all bricks are clear (none are alive).
     *
     * @return true if no bricks are alive, false otherwise
     */
    public boolean isClear(){
        return alive == 0;
    }

    /**
     * Decrements the count of alive bricks when a brick dies.
     */
    public void brickDied(){
        alive--;
    }
}
