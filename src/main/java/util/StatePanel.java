package util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//Switchable state panel
public class StatePanel extends JPanel implements ActionListener {
    protected JFrame frame;
    protected boolean inState = false;

    public StatePanel(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        inState = !inState;

        if (inState){
            frame.add(this, BorderLayout.CENTER);
            repaint();
            frame.pack();
        } else {
            frame.remove(this);
        }
        frame.repaint();
    }
}
