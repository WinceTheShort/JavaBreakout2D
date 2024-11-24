
package level_select;


import entity.Brick;
import entity.BrickField;
import util.LevelManager;

import java.awt.*;

import static org.example.GParams.*;


/**
 * This class represents a preview of a BrickField used to display a level's layout in a smaller grid.
 */
public class PreviewBrickField extends BrickField {

    /**
     * Initializes the PreviewBrickField by setting dimensions and placing bricks accordingly.
     */
    public PreviewBrickField() {
        int screenWidth = (int)(SCREEN_WIDTH * 0.7);
        int screenHeight = (int)(SCREEN_HEIGHT * 0.95);
        int newGridWidth = (int)(gridWidth * 0.7);
        int newGridHeight = (int)(gridHeight * 0.7);


        int xOffset = (screenWidth - FIELD_WIDTH * newGridWidth) / 2;
        int yOffset = (screenHeight - FIELD_HEIGHT * newGridHeight) / 2;

        field = new Brick[FIELD_WIDTH][FIELD_HEIGHT];
        for (int x = 0; x < FIELD_WIDTH; x++){
            for (int y = 0; y < FIELD_HEIGHT; y++){
                field[x][y] = new Brick(x * newGridWidth + xOffset, y * newGridHeight + yOffset, newGridWidth, newGridHeight *2);
            }
        }
    }
    /**
     * Loads the given level into the BrickField.
     *
     * @param level The level to be loaded.
     */
    public void load(LevelManager.Level level) {
        super.load(level.getLevelFile());
    }

    /**
     * Draws the BrickField onto the provided Graphics2D object.
     *
     * @param g2d The Graphics2D object used for drawing.
     */
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
    }
}
