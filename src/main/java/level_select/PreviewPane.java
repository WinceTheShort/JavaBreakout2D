package level_select;

import util.LevelManager;

import javax.swing.*;
import java.awt.*;

public class PreviewPane extends JPanel {

    PreviewBrickField brickField;

    public PreviewPane() {
        super();
        brickField = new PreviewBrickField();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        brickField.draw(g2d);
    }

    public void loadLevel(LevelManager.Level level){
        brickField.load(level);
        repaint();
    }
}
