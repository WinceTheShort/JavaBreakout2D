package menu;

import editor.EditorBrickFieldPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimPanel extends JPanel implements Runnable, ActionListener {

    transient Logger logger = LoggerFactory.getLogger(AnimPanel.class);

    private boolean running;
    boolean col = false;
    Color color = Color.BLACK;


    AnimPanel() {}

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    public void start() {
        Thread animThread = new Thread(this);
        animThread.start();
        running = true;
    }

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

    @Override
    public synchronized void actionPerformed(ActionEvent e) {

        running = !running;

        if (running) {
            notifyAll();
        }
    }
}
