package editor;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.MouseWheelEvent;

import static org.junit.jupiter.api.Assertions.*;

class EditorMouseWheelHandlerTest {

    @Test
    void mouseWheelMoved() {
        JLabel label = new JLabel();

        EditorMouseWheelHandler handler = new EditorMouseWheelHandler();
        assertEquals(1, handler.getBrickType());

        handler.mouseWheelMoved(new MouseWheelEvent(label, 1, 200, 0, 0, 0, 1, false, MouseWheelEvent.WHEEL_BLOCK_SCROLL ,1,1));
        assertEquals(5, handler.getBrickType());

        handler.mouseWheelMoved(new MouseWheelEvent(label, 1, 200, 0, 0, 0, 1, false, MouseWheelEvent.WHEEL_BLOCK_SCROLL ,1,-1));
        assertEquals(1, handler.getBrickType());
    }
}