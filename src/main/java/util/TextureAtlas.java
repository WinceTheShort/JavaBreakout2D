package util;

import java.awt.*;

public class TextureAtlas {
    Texture texture;
    Rectangle[][] regions;

    public TextureAtlas(Texture texture, int cellWidth, int cellHeight, int columns, int rows) {
        this.texture = texture;
        regions = new Rectangle[rows][columns];
        for (int column = 0; column < columns; column++) {
            for (int row = 0; row < rows; row++) {
                regions[row][column] = new Rectangle(column*cellWidth, row*cellHeight, cellWidth, cellHeight);
            }
        }
    }

    public TextureRegion getTextureRegion(int row, int column) {
        if (column > regions.length-1 || column < 0 || row > regions.length-1 || row < 0) return null;
        return new TextureRegion(texture, regions[column][row].x,regions[column][row].y,(int)regions[column][row].getWidth(),(int)regions[column][row].getHeight());
    }
}
