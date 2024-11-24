package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The {@code Texture} class represents an image loaded from a file.
 * It provides various methods to manipulate and retrieve
 * information about the image.
 */
public class Texture {
    protected BufferedImage bufferedImage;

    Logger logger = LoggerFactory.getLogger(Texture.class);

    /**
     * Loads an image from the specified file.
     *
     * @param filename the name of the file to load the image from
     */
    public Texture(String filename) {

        try {
            bufferedImage = ImageIO.read(new File(filename));
        } catch (IOException e) {
            logger.error("Error reading image {}", filename);
        }
    }

    // Getters and setters

    /**
     * Returns the buffered image.
     *
     * @return the buffered image, or {@code null} if the image failed to load
     */
    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }
    /**
     * Returns a subimage defined by the specified coordinates and dimensions.
     *
     * @param x      the X coordinate of the upper-left corner of the specified rectangular region
     * @param y      the Y coordinate of the upper-left corner of the specified rectangular region
     * @param width  the width of the specified rectangular region
     * @param height the height of the specified rectangular region
     * @return the subimage, or {@code null} if the original image is not loaded
     */
    public BufferedImage getSubimage(int x, int y, int width, int height) {
        if (bufferedImage == null) { return null; }
        return bufferedImage.getSubimage(x, y, width, height);
    }
    /**
     * Returns the width of the image.
     *
     * @return the width of the image, or {@code 0} if the image is not loaded
     */
    public int getWidth() {
        if (bufferedImage == null) { return 0; }
        return bufferedImage.getWidth();
    }
    /**
     * Returns the height of the image.
     *
     * @return the height of the image, or {@code 0} if the image is not loaded
     */
    public int getHeight() {
        if (bufferedImage == null) { return 0; }
        return bufferedImage.getHeight();
    }
}
