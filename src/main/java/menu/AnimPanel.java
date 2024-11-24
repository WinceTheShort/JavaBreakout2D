
package menu;



import editor.EditorBrickFieldPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The AnimPanel class provides the functionality for animating a panel by
 * alternating its background color between red and blue.
 * This class implements Runnable to allow it to run in a separate thread and
 * ActionListener to respond to action events.
 */
public class AnimPanel extends JPanel implements Runnable, ActionListener {

    /**
     * A logger instance to log messages.
     */
    transient Logger logger = LoggerFactory.getLogger(AnimPanel.class);

    /**
     * Indicates whether the animation is running.
     */
    private boolean running;

    /**
     * A flag to determine the color to be used.
     */
    boolean col = false;

    /**
     * The current color of the panel.
     */
    Color color = Color.BLACK;


    /**
     * Default constructor for AnimPanel.
     */
    AnimPanel() {}

    /**
     * Paints the component with the specified Graphics object.
     * @param g the Graphics object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Starts the animation thread and sets the running flag to true.
     */
    public void start() {
        Thread animThread = new Thread(this);
        animThread.start();
        running = true;
    }

    /**
     * The run method for the animation thread.
     * It waits for 500 milliseconds, updates the color, and repaints the panel.
     */
    @Override
    public void run() {
        while (true){
            try {
                synchronized (this) {
                    wait(500);

                    while (!running) {
                        wait();
                    }

                    update();

                }
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * Updates the color of the panel and repaints it.
     */
    void update(){
        if (col) {
            color = Color.RED;
            col = false;
        } else {
            color = Color.BLUE;
            col = true;
        }
        repaint();
    }

    /**
     * Handles the action event and toggles the running flag.
     * @param e the ActionEvent object
     */
    @Override
    public synchronized void actionPerformed(ActionEvent e) {

        running = !running;

        if (running) {
            notifyAll();
        }
    }
}
