package editor;

import entity.Brick;
import javax.swing.*;
import java.awt.*;

/**
 * The InfoBrick class extends JPanel and handles the visualization and editing functionalities
 * of a Brick entity within an editor environment.
 */
public class InfoBrick extends JPanel {

    /**
     * Represents a brick entity within the InfoBrick class.
     * Manages the brick's visual representation and state within the editor.
     */
    Brick brick;

    EditorBrickFieldPanel editor;

    /**
     * Constructs an InfoBrick with a given EditorBrickFieldPanel.
     * The InfoBrick class extends JPanel and handles the visualization and editing functionalities
     * of a Brick entity within an editor environment.
     *
     * @param editor the EditorBrickFieldPanel responsible for the brick field and editor interactions.
     */
    public InfoBrick(EditorBrickFieldPanel editor) {
        this.editor = editor;
        brick = new Brick(0,0);
    }

    /**
     * Paints the component by first invoking the superclass implementation and subsequently
     * rendering the `Brick` entity using custom drawing logic.
     *
     * @param g the Graphics object used for rendering the component
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        brick.draw(g2d);
        g2d.dispose();
    }

    /**
     * Updates the Brick entity within the InfoBrick panel with the current brick type and size.
     * <ul>
     * <li>Sets the brick's active sprite based on the current selection from the editor's mouse wheel handler.</li>
     * <li>Adjusts the brick's size to match the width of the InfoBrick panel and twice its height.</li>
     * <li>Repaints the InfoBrick panel to reflect these changes.</li>
     * </ul>
     */
    public void updateBrick() {
        brick.setActiveSprite(editor.editorMouseWheelHandler.getBrickType());
        brick.setSize(this.getWidth(), this.getHeight()*2);

        repaint();
    }

}
