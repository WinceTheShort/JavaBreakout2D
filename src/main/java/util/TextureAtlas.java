package util;

import java.awt.*;

/**
 * A class that represents a texture atlas which consists of a texture and its subdivided regions.
 */
public class TextureAtlas {

    /**
     * The texture that the atlas is based on.
     */
    Texture texture;

    /**
     * The 2D array of rectangles representing regions of the texture.
     */
    Rectangle[][] regions;

    /**
     * Constructs a TextureAtlas with specified texture and grid dimensions.
     *
     * @param texture the base texture.
     * @param cellWidth the width of each cell in the grid.
     * @param cellHeight the height of each cell in the grid.
     * @param columns the number of columns in the grid.
     * @param rows the number of rows in the grid.
     */
    public TextureAtlas(Texture texture, int cellWidth, int cellHeight, int columns, int rows) {
        this.texture = texture;
        regions = new Rectangle[rows][columns];
        //Set the regions cords
        for (int column = 0; column < columns; column++) {
            for (int row = 0; row < rows; row++) {
                regions[row][column] = new Rectangle(column*cellWidth, row*cellHeight, cellWidth, cellHeight);
            }
        }
    }

    /**
     * Returns the texture region at the specified row and column.
     *
     * @param row the row index.
     * @param column the column index.
     * @return the texture region or null if indices are invalid.
     */
    public TextureRegion getTextureRegion(int row, int column) {
        //If region index is invalid return
        if (column > regions.length-1 || column < 0 || row > regions.length-1 || row < 0) return null;
        //Else return indexed texture region
        return new TextureRegion(texture, regions[column][row].x,regions[column][row].y,(int)regions[column][row].getWidth(),(int)regions[column][row].getHeight());
    }
}
