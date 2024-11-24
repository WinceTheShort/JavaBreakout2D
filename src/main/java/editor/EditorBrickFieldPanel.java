package editor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Sprite;
import util.Texture;
import util.TextureRegion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import static org.example.GParams.*;

public class EditorBrickFieldPanel extends JPanel implements Runnable, ActionListener {

    transient Logger logger = LoggerFactory.getLogger(EditorBrickFieldPanel.class);

    private boolean inEditor = false;

    //FPS
    int fps = 60;

    //Handlers
    public final EditorKeyHandler editorKeyHandler = new EditorKeyHandler();
    public final transient EditorMouseHandler editorMouseHandler = new EditorMouseHandler();
    public final transient EditorMouseWheelHandler editorMouseWheelHandler = new EditorMouseWheelHandler();

    //Creates the grid sprite to match the grid and field sizes
    private final transient Sprite grid = new Sprite(new TextureRegion(new Texture("src/images/editorGrid.png"),0,0,32*FIELD_WIDTH,16*FIELD_HEIGHT),
            0,0, gridWidth * FIELD_WIDTH, gridHeight * FIELD_HEIGHT);
    //Brick field
    private final transient EditorBrickField bricks = new EditorBrickField(this);
    private InfoBrick infoBrick;

    public EditorBrickFieldPanel() {

        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setDoubleBuffered(true);
        setFocusable(true);
        setBackground(Color.WHITE);
        addKeyListener(editorKeyHandler);
        addMouseListener(editorMouseHandler);
        addMouseWheelListener(editorMouseWheelHandler);
        grid.setLocation((int) ((double) SCREEN_WIDTH / 2 - grid.getWidth() / 2), 0);
    }

    public void startEditorThread() {
        Thread editorThread = new Thread(this);
        editorThread.start();
    }

    public void setInfoBrick(InfoBrick infoBrick) {
        this.infoBrick = infoBrick;
    }

    @Override
    public void run() {


        double drawInterval = (double) 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long startTime;

        long frameTimer = 0;
        int frames = 0;

        while(inEditor){
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
                logger.debug("FPS: {}", frames);
                frames = 0;
                frameTimer = 0;
            }
        }
    }

    public void update() {
        bricks.update();
        if (editorKeyHandler.keys[KeyEvent.VK_S] && editorKeyHandler.keys[KeyEvent.VK_CONTROL]){
            bricks.saveBoard();
        }
        if (editorKeyHandler.keys[KeyEvent.VK_O] && editorKeyHandler.keys[KeyEvent.VK_CONTROL]){
            bricks.openBoard();
        }
        if (editorKeyHandler.keys[KeyEvent.VK_N] && editorKeyHandler.keys[KeyEvent.VK_CONTROL]){
            bricks.newBoard();
        }
        if (infoBrick != null){
            infoBrick.updateBrick();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        grid.draw(g2d);
        bricks.draw(g2d);


        g2d.dispose();
    }

    public void manualStart(){
        startEditorThread();
        requestFocusInWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        inEditor = !inEditor;

        if(inEditor){
            startEditorThread();
            requestFocusInWindow();
            repaint();
        }
    }

    public EditorBrickField getBricks() {
        return bricks;
    }
}
