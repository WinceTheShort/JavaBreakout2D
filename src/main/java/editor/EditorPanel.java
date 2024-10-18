package editor;

import entity.BrickField;
import util.Sprite;
import util.Texture;

import javax.swing.*;
import java.awt.*;

public class EditorPanel extends JPanel implements Runnable {

    //Screen settings
    public static final int GRID_WIDTH = 32;
    public static final int GRID_HEIGHT = 16;
    public static final int FIELD_WIDTH = 40;
    public static final int FIELD_HEIGHT = 40;
    public static final int SCREEN_WIDTH = FIELD_WIDTH*GRID_WIDTH;
    public static final int SCREEN_HEIGHT = FIELD_HEIGHT*GRID_HEIGHT;

    //FPS
    int fps = 120;

    private transient Thread editorThread;
    public final transient EditorKeyHandler editorKeyHandler = new EditorKeyHandler();
    public final transient EditorMouseHandler editorMouseHandler = new EditorMouseHandler();
    private final transient Sprite grid = new Sprite(new Texture("src/images/editorGrid.png"));
    private final transient BrickField bricks = new BrickField(this);

    public EditorPanel() {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setDoubleBuffered(true);
        setFocusable(true);
        addKeyListener(editorKeyHandler);
        addMouseListener(editorMouseHandler);
    }

    public void stratEditorThread() {
        editorThread = new Thread(this);
        editorThread.start();
    }

    @Override
    public void run() {


        double drawInterval = (double) 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long startTime;

        long frameTimer = 0;
        int frames = 0;

        while(editorThread.isAlive()){
            startTime = System.nanoTime();
            delta += (startTime - lastTime) / drawInterval;
            frameTimer += startTime - lastTime;
            lastTime = startTime;

            if(delta >= 1){
                //Update assets
                update();

                //Display updated assets
                repaint();

                delta--;
                frames++;
            }

            if (frameTimer >= 1000000000){
                System.out.println("FPS: " + frames);
                frames = 0;
                frameTimer = 0;
            }
        }
    }

    public void update() {
        bricks.update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        grid.draw(g2d);
        bricks.draw(g2d);


        g2d.dispose();
    }
}
