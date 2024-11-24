package entity;

import java.awt.*;
import java.io.*;

import static org.example.GParams.*;

public class BrickField implements Serializable {
    protected Brick[][] field;
    int alive = 0;

    public BrickField() {
        int offset = (SCREEN_WIDTH - FIELD_WIDTH * gridWidth) / 2;

        field = new Brick[FIELD_WIDTH][FIELD_HEIGHT];
        for (int x = 0; x < FIELD_WIDTH; x++){
            for (int y = 0; y < FIELD_HEIGHT; y++){
                field[x][y] = new Brick(x * gridWidth + offset, y * gridHeight, gridWidth, gridHeight *2);
            }
        }
    }
    public void draw(Graphics2D g2d){
        for (Brick[] bricks : field) {
            for (Brick brick : bricks) {
                brick.draw(g2d);
            }
        }
    }
    protected void load(File file){
        Integer[][] brickStates = null;
        //Save field
        try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream(file))) {
            brickStates = (Integer[][]) oos.readObject();
        } catch (Exception e){
            e.printStackTrace();
        }
        //Reload bricks textures
        int i = 0;
        for (Brick[] bricks : field){
            int j = 0;
            for (Brick brick : bricks){
                brick.reloadTexture();
                assert brickStates != null;
                brick.setActiveSprite(brickStates[i][j]);
                if (brick.getActiveSprite() == 0){
                    brick.setAlive(false);
                    brick.setWall(false);
                }
                else if (brick.getActiveSprite() == 5){
                    brick.setAlive(false);
                    brick.setWall(true);
                }
                else {
                    brick.setWall(false);
                    brick.setAlive(true);
                    brick.reloadTexture();
                    alive++;
                }
                j++;
            }
            i++;
        }
    }
    public boolean isClear(){
        return alive == 0;
    }
    public void brickDied(){
        alive--;
    }
}
