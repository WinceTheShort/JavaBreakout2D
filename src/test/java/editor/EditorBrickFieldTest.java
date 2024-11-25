package editor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EditorBrickFieldTest {

    @Test
    void new_load_Board() {
        EditorBrickField editorBrickField = new EditorBrickField(new EditorBrickFieldPanel());
        editorBrickField.loadBoard("src/levels/custom/test.save");
        assertTrue(editorBrickField.getBrickAt(0,0).isAlive());
        assertEquals(1, editorBrickField.getBrickAt(1,0).getActiveSprite());

        editorBrickField.newBoard();
        assertFalse(editorBrickField.getBrickAt(0,0).isAlive());
        assertEquals(0, editorBrickField.getBrickAt(1,0).getActiveSprite());

    }


}