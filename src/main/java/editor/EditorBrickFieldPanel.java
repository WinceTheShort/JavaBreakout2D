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

/**
 * The EditorBrickFieldPanel class extends JPanel and implements Runnable and ActionListener.
 * It serves as the main panel for the brick field editor, handling user interactions and
 * rendering graphical assets.
 */
public class EditorBrickFieldPanel extends JPanel implements Runnable, ActionListener {

    /**
     * Logger instance used for logging events and debugging information within
     * the EditorBrickFieldPanel class. This logger is transient to avoid
     * serialization issues.
     */
    transient Logger logger = LoggerFactory.getLogger(EditorBrickFieldPanel.class);

    /**
     * Indicates whether the editor mode is currently active.
     * This flag is used to track the state of the editor within the
     * EditorBrickFieldPanel. When set to true, the editor functionalities
     * such as interaction with bricks and handling of user inputs
     * are enabled.
     */
    private boolean inEditor = false;

    /**
     * Represents the desired frames per second (FPS) for rendering or updating the
     * EditorBrickFieldPanel. This value is used to control the refresh rate of the
     * panel, ensuring smooth visual updates and animations within the editor environment.
     */
    //FPS
    int fps = 60;

    /**
     * The editorKeyHandler is an instance of EditorKeyHandler, which manages the keyboard input handling
     * for the editor environment within the EditorBrickFieldPanel. It listens for key events and maintains
     * the state of key presses to facilitate various editing functionalities.
     */
    public final EditorKeyHandler editorKeyHandler = new EditorKeyHandler();

    /**
     * Handles mouse events for the editor within the EditorBrickFieldPanel.
     * This instance of {@link EditorMouseHandler} manages mouse interactions on a grid of rectangles,
     * updating the state of mouse buttons and tracking clicks within the editor.
     */
    public final transient EditorMouseHandler editorMouseHandler = new EditorMouseHandler();

    /**
     * Handles mouse wheel events within the editor.
     * This instance interacts with the EditorMouseWheelHandler class to manage
     * actions such as changing the type of brick based on mouse wheel movements.
     */
    public final transient EditorMouseWheelHandler editorMouseWheelHandler = new EditorMouseWheelHandler();

    /**
     * Creates the grid sprite to match the grid and field sizes.
     * This sprite uses a specified texture region from the "editorGrid.png" image,
     * sized according to the field and grid dimensions.
     */
    private final transient Sprite grid = new Sprite(new TextureRegion(new Texture("src/images/editorGrid.png"),0,0,32*FIELD_WIDTH,16*FIELD_HEIGHT),
            0,0, gridWidth * FIELD_WIDTH, gridHeight * FIELD_HEIGHT);

    /**
     * Represents the field of bricks in the EditorBrickFieldPanel.
     * This field is integral to the editing functionalities of the brick editor.
     * It is declared as transient to avoid serialization issues and is final
     * to ensure its immutability within the context of the panel.
     */
    private final transient EditorBrickField bricks = new EditorBrickField(this);

    /**
     * A reference to the InfoBrick component of the editor.
     * Manages the visualization and editing functionalities of a Brick entity within the editor environment.
     */
    private InfoBrick infoBrick;

    /**
     * Default constructor for the EditorBrickFieldPanel class.
     * Initializes the panel with preferred dimensions, buffering, and focusability settings.
     * Sets the background color to white and attaches necessary event handlers including key,
     * mouse, and mouse wheel listeners. Adjusts the location of the grid within the panel.
     */
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

    /**
     * Starts a new thread to run the editor functionality. The method creates an
     * instance of Thread, passing the current instance (this) which implies that the
     * containing class implements Runnable. It then starts the thread, which triggers
     * the run method of the containing class.
     */
    public void startEditorThread() {
        Thread editorThread = new Thread(this);
        editorThread.start();
    }

    /**
     * Sets the InfoBrick instance for the EditorBrickFieldPanel.
     *
     * @param infoBrick the InfoBrick instance to be set for the EditorBrickFieldPanel
     */
    public void setInfoBrick(InfoBrick infoBrick) {
        this.infoBrick = infoBrick;
    }

    /**
     * The run method is executed in a separate thread to manage the main loop
     * of the editor. This loop handles rendering and updating processes at a
     * consistent frame rate defined by the fps (frames per second) value.
     * <p>
     * In each iteration of the loop, the following steps occur:
     * - The time taken for each frame is calculated.
     * - The delta is incremented based on the time difference and the target frame interval.
     * - The assets are updated and repainted once the delta reaches or exceeds 1.
     * - The frame count is logged every second to track the performance in terms of FPS.
     * <p>
     * The method relies on several class fields including `inEditor`, `fps`,
     * `logger`, and methods like `update()` and `repaint()` to function correctly.
     */
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

    /**
     * Updates the state of the bricks and handles user inputs for saving, opening,
     * and creating new boards within the editor. Also updates the state of the infoBrick
     * if it is present.
     */
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

    /**
     * Paints this component by first invoking the superclass's paintComponent implementation
     * and then rendering custom graphics for the grid and bricks.
     *
     * @param g the Graphics object to protect and constrain its drawing area
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        grid.draw(g2d);
        bricks.draw(g2d);


        g2d.dispose();
    }

    /**
     * Manually starts the editor by initiating the editor thread and requesting focus.
     * <ul>
     *   <li>Calls {@link #startEditorThread()} to start the thread associated with the editor.</li>
     *   <li>Requests focus in the window to ensure the editor is ready for user input.</li>
     * </ul>
     */
    public void manualStart(){
        startEditorThread();
        requestFocusInWindow();
    }

    /**
     * This method is invoked when an action occurs. It toggles the editing mode
     * and initiates the editor thread if switching into the editor state.
     *
     * @param e the ActionEvent that triggered this method
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        inEditor = !inEditor;

        if(inEditor){
            startEditorThread();
            requestFocusInWindow();
            repaint();
        }
    }

    /**
     * Retrieves the EditorBrickField associated with this panel.
     *
     * @return the EditorBrickField instance representing the field of bricks in the editor.
     */
    public EditorBrickField getBricks() {
        return bricks;
    }
}
