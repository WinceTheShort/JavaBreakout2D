package org.example;

import java.awt.*;

public class GParams {
    //Screen settings
    public static final Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int SCREEN_WIDTH = SCREEN_DIMENSION.width;
    public static final int SCREEN_HEIGHT = SCREEN_DIMENSION.height;
    public static final int FIELD_WIDTH = 35;
    public static final int FIELD_HEIGHT = 35;
    public static int GRID_WIDTH = 0;
    public static int GRID_HEIGHT = 0;

    public GParams() {
        if (SCREEN_WIDTH > SCREEN_HEIGHT*0.9*2) {
            GRID_WIDTH = SCREEN_WIDTH / FIELD_WIDTH;
            GRID_HEIGHT = GRID_WIDTH / 2;
        } else {
            GRID_HEIGHT = SCREEN_HEIGHT / FIELD_HEIGHT;
            GRID_WIDTH = GRID_HEIGHT * 2;
        }
    }
}
