package level_select;

import net.miginfocom.swing.MigLayout;
import org.example.GParams;
import util.LevelManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LevelCheckbox extends JPanel implements MouseListener, ActionListener{
    //Calculates font size from screen width so its consistent across multiple resolutions
    private static final int FONT_SIZE = (int)(GParams.SCREEN_WIDTH * 0.012);
    private static final String FONT_NAME = "minepixel";

    private final transient ArrayList<ActionListener> listeners = new ArrayList<>();

    JLabel numLabel;
    JLabel levelNameLabel;
    LevelManager.Level level;
    boolean selectable;
    boolean selected = false;

    private boolean mouseEntered = false;
    private boolean mousePressed = false;

    public LevelCheckbox(String header) {
        super();

        initPanel();
        setBackground(Color.gray.darker().darker());

        numLabel = new JLabel("#");
        levelNameLabel = new JLabel(" " + header);

        initLabels();

        selectable = false;
    }
    public LevelCheckbox(LevelManager.Level level, int num) {
        super();
        this.level = level;

        initPanel();

        numLabel = new JLabel(String.valueOf(num));
        levelNameLabel = new JLabel(" " + level.getLevelName());

        initLabels();

        selectable = true;
    }
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
            setBorder(BorderFactory.createMatteBorder(2, 4, 2, 2, Color.gray.darker().darker().darker()));
        }
    }

    public void addActionListener(ActionListener listener){
        listeners.add(listener);
    }
    private void notifyListeners(InputEvent e){
        ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, level.getLevelName(), e.getWhen(), e.getModifiersEx());
        for (ActionListener listener : listeners) {
            listener.actionPerformed(evt);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (selectable) {
            selected = e.getActionCommand().equals(level.getLevelName());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Not used event
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
        if (mouseEntered) {
            mouseEntered = false;
            notifyListeners(e);
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (selectable) {
            mouseEntered = true;
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            repaint();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseEntered = false;
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        repaint();
    }
}
