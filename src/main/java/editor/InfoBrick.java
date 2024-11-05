package editor;

import entity.Brick;
import javax.swing.*;
import java.awt.*;

public class InfoBrick extends JPanel {
    Brick brick;
    EditorBrickFieldPanel editor;
    public InfoBrick(EditorBrickFieldPanel editor) {
        this.editor = editor;
        brick = new Brick(0,0);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        brick.draw(g2d);
        g2d.dispose();
    }

    public void updateBrick() {
        brick.setActiveSprite(editor.editorMouseWheelHandler.getBrickType());
        brick.setSize(this.getWidth(), this.getHeight()*2);

        repaint();
    }

}
