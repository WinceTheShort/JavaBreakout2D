package editor;

import entity.Brick;
import entity.EditorBrickField;
import org.example.AnimPanel;
import org.example.MenuPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.CustomButton;
import util.Sprite;
import util.Texture;
import util.TextureRegion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import static org.example.GParams.*;

public class BrickFieldPanel extends JPanel implements Runnable, ActionListener {

    transient Logger logger = LoggerFactory.getLogger(BrickFieldPanel.class);

    JFrame frame;
    private boolean inEditor = false;

    //Screen settings
    public static final int INFO_PANEL_HEIGHT = GRID_HEIGHT*4;
    public static final int SCREEN_WIDTH = FIELD_WIDTH * GRID_WIDTH;
    public static final int SCREEN_HEIGHT = FIELD_HEIGHT * GRID_HEIGHT + INFO_PANEL_HEIGHT;

    //FPS
    int fps = 120;

    //Handlers
    public final EditorKeyHandler editorKeyHandler = new EditorKeyHandler();
    public final transient EditorMouseHandler editorMouseHandler = new EditorMouseHandler();
    public final transient EditorMouseWheelHandler editorMouseWheelHandler = new EditorMouseWheelHandler();

    private transient Thread editorThread;
    //Creates the grid sprite to match the grid and field sizes
    private final transient Sprite grid = new Sprite(new TextureRegion(new Texture("src/images/editorGrid.png"),0,0,32*FIELD_WIDTH,16*FIELD_HEIGHT), 0, 0, GRID_WIDTH * FIELD_WIDTH, GRID_HEIGHT * FIELD_HEIGHT);
    //Brick field
    private final transient EditorBrickField bricks = new EditorBrickField(this);
    private final transient Brick infoBrick = new Brick(0,FIELD_HEIGHT * GRID_HEIGHT, GRID_WIDTH*4, GRID_WIDTH*4);

    private transient CustomButton backButton;


    public BrickFieldPanel(JFrame frame, MenuPanel menuPanel, AnimPanel animPanel) {
        this.frame = frame;

        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setDoubleBuffered(true);
        setFocusable(true);
        setBackground(Color.WHITE);
        addKeyListener(editorKeyHandler);
        addMouseListener(editorMouseHandler);
        addMouseWheelListener(editorMouseWheelHandler);

        if (menuPanel != null) {
            backButton = new CustomButton(50,50);
            add(backButton);
            backButton.addActionListener(this);
            backButton.addActionListener(menuPanel);
            backButton.addActionListener(animPanel);
        }
    }

    public void startEditorThread() {
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
                logger.debug("FPS: {}", frames);
                frames = 0;
                frameTimer = 0;
            }
        }
    }

    public void update() {
        bricks.update();
        infoBrick.setActiveSprite(editorMouseWheelHandler.getBrickType());
        if (editorKeyHandler.keys[KeyEvent.VK_S] && editorKeyHandler.keys[KeyEvent.VK_CONTROL]){
            bricks.saveBoard();
        }
        if (editorKeyHandler.keys[KeyEvent.VK_O] && editorKeyHandler.keys[KeyEvent.VK_CONTROL]){
            bricks.openBoard();
        }
        if (editorKeyHandler.keys[KeyEvent.VK_N] && editorKeyHandler.keys[KeyEvent.VK_CONTROL]){
            bricks.newBoard();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        grid.draw(g2d);
        bricks.draw(g2d);
        infoBrick.draw(g2d);


        g2d.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        inEditor = !inEditor;

        if(inEditor){
            startEditorThread();
            frame.add(this, BorderLayout.CENTER);
            requestFocusInWindow();
            repaint();
            frame.pack();
        } else {
            frame.remove(this);
        }

        frame.repaint();
    }
}
