package entity;

import java.awt.*;
import java.io.*;

import static org.example.GParams.*;

public class BrickField implements Serializable {
    protected Brick[][] field;
    public final boolean[][] wasChanged;

    public BrickField() {
        field = new Brick[FIELD_WIDTH][FIELD_HEIGHT];
        for (int x = 0; x < FIELD_WIDTH; x++){
            for (int y = 0; y < FIELD_HEIGHT; y++){
                field[x][y] = new Brick(x*GRID_WIDTH, y*GRID_HEIGHT, GRID_WIDTH, GRID_HEIGHT*2);
            }
        }
        wasChanged = new boolean[FIELD_WIDTH][FIELD_HEIGHT];
    }
    public void draw(Graphics2D g2d){
        for (Brick[] bricks : field) {
            for (Brick brick : bricks) {
                brick.draw(g2d);
            }
        }
    }
    protected void load(File file){
        try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream(file))) {
            field = (Brick[][]) oos.readObject();
        } catch (Exception e){
            e.printStackTrace();
        }
        for (Brick[] bricks : field){
            for (Brick brick : bricks){
                brick.reloadTexture();
            }
        }
    }
}
