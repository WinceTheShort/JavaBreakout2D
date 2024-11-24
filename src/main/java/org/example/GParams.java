package org.example;

import java.awt.*;

public class GParams {
    //Screen settings
    public static final Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int SCREEN_WIDTH = SCREEN_DIMENSION.width;
    public static final int SCREEN_HEIGHT = SCREEN_DIMENSION.height;
    public static final int FIELD_WIDTH = 20;
    public static final int FIELD_HEIGHT = 17;
    public static int gridWidth = 0;
    public static int gridHeight = 0;
    public static Dimension fieldDimension;

    private GParams() {}

    public static void setParams(){
        double z = Math.min((double) SCREEN_WIDTH / (FIELD_WIDTH * 2), (double) SCREEN_HEIGHT / FIELD_HEIGHT);
        gridWidth = (int)(z * 2);
        gridHeight = (int)(z);
        fieldDimension = new Dimension(gridWidth * FIELD_WIDTH, gridHeight * FIELD_HEIGHT);
    }
}
