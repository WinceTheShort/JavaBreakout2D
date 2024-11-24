package level_select;

import util.LevelManager;

import javax.swing.*;
import java.awt.*;

/**
 * The PreviewPane class is responsible for rendering a preview of the level
 * including the brick layout, using the PreviewBrickField component.
 */
public class PreviewPane extends JPanel {

    PreviewBrickField brickField;

    /**
     * Constructs a PreviewPane instance and initializes the PreviewBrickField component.
     */
    public PreviewPane() {
        super();
        brickField = new PreviewBrickField();
    }

    /**
     * Paints the component by rendering the PreviewBrickField.
     *
     * @param g the Graphics context in which to paint
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        brickField.draw(g2d);
    }

     /**
      * Loads the specified level into the PreviewBrickField and repaints the component.
      *
      * @param level the level to load
      */
     public void loadLevel(LevelManager.Level level){
        brickField.load(level);
        repaint();
    }
}
