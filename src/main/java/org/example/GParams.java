package org.example;

import java.awt.*;

public class GParams {
    //Screen settings
    public static final Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int SCREEN_WIDTH = SCREEN_DIMENSION.width;
    public static final int SCREEN_HEIGHT = SCREEN_DIMENSION.height;
    public static final int FIELD_WIDTH = 35;
    public static final int FIELD_HEIGHT = 35;
    public static int gridWidth = 0;
    public static int gridHeight = 0;

    private GParams() {}

    public static void setParams(){
        if (SCREEN_WIDTH > SCREEN_HEIGHT*0.95*2) {
            gridWidth = SCREEN_WIDTH / FIELD_WIDTH;
            gridHeight = gridWidth / 2;
        } else {
            gridHeight = (int)(SCREEN_HEIGHT*0.95 / FIELD_HEIGHT);
            gridWidth = gridHeight * 2;
        }
    }
}
