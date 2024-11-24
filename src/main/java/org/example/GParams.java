
package org.example;

import java.awt.*;

/*
 * Utility class for handling screen dimensions and grid parameters.
 */
/**
 * This class contains parameters for screen settings and field dimensions.
 */
public class GParams {
    //Screen settings
    /**
     * The dimensions of the screen.
     */
    public static final Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * The width of the screen.
     */
    public static final int SCREEN_WIDTH = SCREEN_DIMENSION.width;

    /**
     * The height of the screen.
     */
    public static final int SCREEN_HEIGHT = SCREEN_DIMENSION.height;

    /**
     * The width of a field.
     */
    public static final int FIELD_WIDTH = 20;

    /**
     * The height of a field.
     */
    public static final int FIELD_HEIGHT = 17;

    /**
     * The width of the grid.
     */
    public static int gridWidth = 0;

    /**
     * The height of the grid.
     */
    public static int gridHeight = 0;

    /**
     * The dimensions of the field.
     */
    public static Dimension fieldDimension;

    /**
     * Private constructor to prevent instantiation.
     */
    private GParams() {}

    /**
     * Sets the parameters for grid width, grid height, and field dimensions based on screen size.
     */
    public static void setParams(){
        double z = Math.min((double) SCREEN_WIDTH / (FIELD_WIDTH * 2), (double) SCREEN_HEIGHT / FIELD_HEIGHT);
        gridWidth = (int)(z * 2);
        gridHeight = (int)(z);
        fieldDimension = new Dimension(gridWidth * FIELD_WIDTH, gridHeight * FIELD_HEIGHT);
    }
}
