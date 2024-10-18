package editor;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class EditorMouseWheelHandler implements MouseWheelListener {
    private int brickType = 1;


    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) {
            if (brickType != 5) {brickType ++;}
            else {brickType = 1;}
        }
        if (e.getWheelRotation() > 0) {
            if (brickType != 1) {brickType --;}
            else {brickType = 5;}
        }
    }

    public int getBrickType() {
        return brickType;
    }
}
