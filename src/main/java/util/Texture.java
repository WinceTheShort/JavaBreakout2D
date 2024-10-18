package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Texture {
    protected BufferedImage bufferedImage;

    public Texture(String filename) {

        try {
            bufferedImage = ImageIO.read(new File(filename));
        } catch (IOException e) {
            System.err.println("Error reading image " + filename);
        }
    }

    //Getters and setters
    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }
    public BufferedImage getSubimage(int x, int y, int width, int height) {
        if (bufferedImage == null) { return null; }
        return bufferedImage.getSubimage(x, y, width, height);
    }
    public int getWidth() {
        if (bufferedImage == null) { return 0; }
        return bufferedImage.getWidth();
    }
    public int getHeight() {
        if (bufferedImage == null) { return 0; }
        return bufferedImage.getHeight();
    }
}
