
package level_select;


import net.miginfocom.swing.MigLayout;
import org.example.GParams;
import util.LevelManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


/**
 * The LevelCheckbox class represents a graphical checkbox component for level selection in a game or application.
 * It supports clickable and selectable states, with different visual indications for each state (pressed, entered, selected).
 * It also notifies registered listeners about selection actions.
 */
public class LevelCheckbox extends JPanel implements MouseListener, ActionListener{

    /**
     * Calculates font size from screen width so it is consistent across multiple resolutions
     */
    private static final int FONT_SIZE = (int)(GParams.SCREEN_WIDTH * 0.012);

    /**
     * The name of the font used for level labels.
     */
    private static final String FONT_NAME = "minepixel";

    /**
     * A list of action listeners registered for level selection events.
     */
    private final transient ArrayList<ActionListener> listeners = new ArrayList<>();

    /**
     * The label displaying the level number.
     */
    JLabel numLabel;

    /**
     * The label displaying the level name.
     */
    JLabel levelNameLabel;

    /**
     * The level being represented by this checkbox.
     */
    transient LevelManager.Level level;

    /**
     * Indicates whether the checkbox is selectable.
     */
    boolean selectable;

    /**
     * Indicates whether the checkbox is currently selected.
     */
    boolean selected = false;

    /**
     * Indicates whether the mouse is currently over the checkbox.
     */
    private boolean mouseEntered = false;

    /**
     * Indicates whether the mouse button is currently pressed.
     */
    private boolean mousePressed = false;

    /**
     * Constructs a LevelCheckbox with a header.
     *
     * @param header The header text for the checkbox.
     */
    public LevelCheckbox(String header) {
        super();

        initPanel();
        setBackground(Color.gray.darker().darker());

        numLabel = new JLabel("#");
        levelNameLabel = new JLabel(" " + header);

        initLabels();

        selectable = false;
    }
    /**
     * Constructs a LevelCheckbox with a specified level and number.
     *
     * @param level The LevelManager.Level object representing the level.
     * @param num The number associated with the level.
     */
    public LevelCheckbox(LevelManager.Level level, int num) {
        super();
        this.level = level;

        initPanel();

        numLabel = new JLabel(String.valueOf(num));
        levelNameLabel = new JLabel(" " + level.getLevelName());

        initLabels();

        selectable = true;
    }
    /**
     * Initializes the panel settings including layout and listeners.
     */
    private void initPanel(){
        MigLayout layout = new MigLayout(
                "gap 0, ins 0",
                "[10%, fill|90%, fill]",
                "[100%, fill]"
        );
        setLayout(layout);
        setFocusable(true);
        addMouseListener(this);
        setBackground(Color.gray.brighter());
    }

    /**
     * Initializes the labels for the level number and level name.
     */
    private void initLabels(){
        numLabel.setHorizontalAlignment(SwingConstants.CENTER);
        numLabel.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
        numLabel.setForeground(Color.BLACK);
        numLabel.setBorder(BorderFactory.createMatteBorder(2, 4, 2, 2, Color.BLACK));
        add(numLabel);

        levelNameLabel.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
        levelNameLabel.setForeground(Color.BLACK);
        levelNameLabel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 4, Color.BLACK));
        add(levelNameLabel);
    }

    /**
     * Paints the component with appropriate background colors based on its state (selectable, pressed, entered, selected).
     *
     * @param g The Graphics object used for painting.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (selectable) {
            if (mousePressed) {
                setBackground(Color.gray.darker());
            } else if (mouseEntered) {
                setBackground(Color.gray);
            } else {
                setBackground(Color.gray.brighter());
            }
        }
        if (selected){
            setBackground(Color.gray);
            setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.gray.darker().darker().darker()));
        } else if (selectable){
            setBackground(Color.gray.brighter());
            setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray.darker().darker().darker()));
        }
    }

    /**
     * Adds an ActionListener to the list of listeners interested in level selection events.
     *
     * @param listener The ActionListener to be added.
     */
    public void addActionListener(ActionListener listener){
        listeners.add(listener);
    }
    /**
     * Notifies all registered listeners about a level selection event.
     *
     * @param e The input event that triggered the notification.
     */
    private void notifyListeners(InputEvent e){
        ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, level.getLevelName(), e.getWhen(), e.getModifiersEx());
        for (ActionListener listener : listeners) {
            listener.actionPerformed(evt);
        }
    }


    /**
     * Handles action events for the checkbox, updating the selected state based on the event's action command.
     *
     * @param e The ActionEvent triggered by an action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (selectable) {
            selected = e.getActionCommand().equals(level.getLevelName());
        }
    }

    /**
     * Not used mouse event handler.
     *
     * @param e The mouse event.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // Not used event
    }

    /**
     * Handles the mouse pressed event, updating the pressed state and repainting the component.
     *
     * @param e The mouse event.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
        repaint();
    }

    /**
     * Handles the mouse released event, updating the pressed state,
     * notifying listeners if needed, and repainting the component.
     *
     * @param e The mouse event.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
        if (mouseEntered) {
            mouseEntered = false;
            notifyListeners(e);
        }
        repaint();
    }

    /**
     * Handles the mouse entered event, updating the entered state,
     * changing the cursor, and repainting the component if it's selectable.
     *
     * @param e The mouse event.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        if (selectable) {
            mouseEntered = true;
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            repaint();
        }
    }

    /**
     * Handles the mouse exited event, updating the entered state,
     * changing the cursor to default, and repainting the component.
     *
     * @param e The mouse event.
     */
    @Override
    public void mouseExited(MouseEvent e) {
        mouseEntered = false;
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        repaint();
    }

    public boolean isSelectable() {
        return selectable;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getLevelName() {
        return level.getLevelName();
    }
}
