
package util;

/**
 * A custom button component that extends JComponent and implements MouseListener.
 * This component can change color on mouse events and notify action listeners.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * A custom button component that extends JComponent and implements MouseListener.
 * This component can change color on mouse events and notify action listeners.
 */
public class CustomButton extends JComponent implements MouseListener {

/**
  * The name of the font used in the button label.
  */
private static final String FONT_NAME = "minepixel";

/**
  * A list of action listeners registered to this button.
  */
private final transient ArrayList<ActionListener> listeners = new ArrayList<>();

/**
  * Indicates whether the mouse pointer is over the button.
  */
private boolean mouseEntered = false;

/**
  * Indicates whether the mouse button is pressed down.
  */
private boolean mousePressed = false;

/**
  * The color of the button.
  */
private Color color = Color.GRAY;

/**
  * The text label displayed on the button.
  */
private String label = "";

/**
  * The font size of the button label.
  */
private int fontSize = 12;

/**
  * Default constructor that initializes the custom button with default settings.
  */
public CustomButton() {
        super();

        setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
        addMouseListener(this);

    }
/**
  * Constructor that initializes the custom button with a specific size.
  *
  * @param width  The width of the button.
  * @param height The height of the button.
  */
public CustomButton(int width, int height) {
        super();

        setPreferredSize(new Dimension(width, height));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        addMouseListener(this);

    }

/**
  * Paints the button component with its current state.
  *
  * @param g The Graphics object to protect.
  */
@Override
public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(color);
        if (mousePressed) {
            g2d.setColor(color.darker().darker());
            g2d.fillRect(0, 0, getWidth(), getHeight());
        } else if (mouseEntered) {
            g2d.setColor(color.darker());
            g2d.fillRect(0, 0, getWidth(), getHeight());
        } else {
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }

        g2d.setFont(new Font(FONT_NAME, Font.PLAIN, fontSize));
        g2d.setColor(color.darker().darker().darker());
        g2d.drawString(label, getWidth()/20+fontSize/15, getHeight()/2+fontSize/2+fontSize/15);
        g2d.setColor(Color.BLACK);
        g2d.drawString(label, getWidth()/20, getHeight()/2+fontSize/2);

        g2d.dispose();
    }

/**
  * Handles the mouse clicked event.
  *
  * @param e The mouse event.
  */
@Override
public void mouseClicked(MouseEvent e) {
        // Not used event
    }

/**
  * Handles the mouse pressed event.
  *
  * @param e The mouse event.
  */
@Override
public void mousePressed(MouseEvent e) {
        mousePressed = true;
        repaint();
    }

/**
  * Handles the mouse released event.
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
  * Handles the mouse entered event.
  *
  * @param e The mouse event.
  */
@Override
public void mouseEntered(MouseEvent e) {
        mouseEntered = true;
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        repaint();
    }

/**
  * Handles the mouse exited event.
  *
  * @param e The mouse event.
  */
@Override
public void mouseExited(MouseEvent e) {
        mouseEntered = false;
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        repaint();
    }

/**
 * Adds an action listener to the button.
 *
 * @param listener The action listener to add.
 */
public void addActionListener(ActionListener listener){
        listeners.add(listener);
    }
/**
 * Notifies all registered action listeners when an action occurs.
 *
 * @param e The input event triggering the notification.
 */
private void notifyListeners(InputEvent e){
        ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "", e.getWhen(), e.getModifiersEx());
        for (ActionListener listener : listeners) {
            listener.actionPerformed(evt);
        }
    }
/**
 * Sets the color of the button.
 *
 * @param color The new color.
 */
public void setColor(Color color) {
        this.color = color;
    }

/**
 * Sets the label of the button.
 *
 * @param label The new label.
 */
public void setLabel(String label) {
        this.label = label;
        repaint();
    }

/**
 * Sets the font size of the button label and adjusts the border accordingly.
 *
 * @param size The new font size.
 */
public void setFontSize(int size) {
        fontSize = size;
        setBorder(BorderFactory.createLineBorder(Color.BLACK, (int)(fontSize*0.15)));
    }
/**
 * Simulates a button click by notifying all action listeners.
 */
public void doClick(){
        notifyListeners(new MouseEvent(this, 0, 0, 0, 0, 0, 0, false));
    }
}
