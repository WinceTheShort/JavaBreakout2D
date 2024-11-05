package util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CustomButton extends JComponent implements MouseListener {

    private static String fontName = "minepixel";

    private transient ArrayList<ActionListener> listeners = new ArrayList<>();

    private boolean mouseEntered = false;
    private boolean mousePressed = false;

    private Color color = Color.GRAY;

    private String label = "";
    private int fontSize = 12;

    public CustomButton() {
        super();

        setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
        addMouseListener(this);

    }
    public CustomButton(int width, int height) {
        super();

        setPreferredSize(new Dimension(width, height));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        addMouseListener(this);

    }

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

        g2d.setFont(new Font(fontName, Font.PLAIN, fontSize));
        g2d.setColor(color.darker().darker().darker());
        g2d.drawString(label, getWidth()/20+fontSize/15, getHeight()/2+fontSize/2+fontSize/15);
        g2d.setColor(Color.BLACK);
        g2d.drawString(label, getWidth()/20, getHeight()/2+fontSize/2);

        g2d.dispose();
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
        mouseEntered = true;
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseEntered = false;
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        repaint();
    }

    public void addActionListener(ActionListener listener){
        listeners.add(listener);
    }
    private void notifyListeners(InputEvent e){
        ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "", e.getWhen(), e.getModifiersEx());
        for (ActionListener listener : listeners) {
            listener.actionPerformed(evt);
        }
    }
    public void setColor(Color color) {
        this.color = color;
    }

    public void setLabel(String label) {
        this.label = label;
        repaint();
    }

    public void setFontSize(int size) {
        fontSize = size;
        setBorder(BorderFactory.createLineBorder(Color.BLACK, (int)(fontSize*0.15)));
    }
}
