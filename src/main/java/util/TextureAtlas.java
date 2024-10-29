package util;

import java.awt.*;

public class TextureAtlas {
    //TODO refactor to use cell width and height instead of rectangles
    Texture texture;
    Rectangle[][] regions;

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

    public TextureRegion getTextureRegion(int row, int column) {
        //If region index is invalid return
        if (column > regions.length-1 || column < 0 || row > regions.length-1 || row < 0) return null;
        //Else return indexed texture region
        return new TextureRegion(texture, regions[column][row].x,regions[column][row].y,(int)regions[column][row].getWidth(),(int)regions[column][row].getHeight());
    }
}
