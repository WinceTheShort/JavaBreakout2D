package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimPanel extends JPanel implements Runnable, ActionListener {

    private transient Thread animThread;
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
        animThread = new Thread(this);
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
                throw new RuntimeException(e);
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
