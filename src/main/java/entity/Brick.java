package entity;

import util.Sprite;
import util.SpriteSheet;
import util.Texture;
import util.TextureRegion;

import java.awt.*;
import java.io.Serializable;


/**
 * The Brick class represents a brick entity that utilizes sprites from a spritesheet.
 * It supports functionalities like setting and checking if the brick is a wall,
 * checking if the brick is alive, reloading the brick texture, and handling brick damage.
 */
public class Brick extends SpriteSheet implements Serializable {
    private static final String BRICK_SPRITES_PNG = "src/images/brickSprites.png";
    private static final int BRICK_SPRITES_SIZE = 16;

    public Rectangle hitbox;

    protected boolean isAlive;

    /**
     * Checks if the brick is part of the wall.
     *
     * @return {@code true} if the brick is a wall, otherwise {@code false}.
     */
    public boolean isWall() {
        return isWall;
    }

    /**
     * Sets the brick as a wall or not.
     *
     * @param wall {@code true} to set the brick as a wall, otherwise {@code false}.
     */
    public void setWall(boolean wall) {
        isWall = wall;
    }

    protected boolean isWall;

    /**
     * Default constructor which initializes a Brick with default size and texture.
     */
    public Brick() {
        super(new Texture(BRICK_SPRITES_PNG), BRICK_SPRITES_SIZE, BRICK_SPRITES_SIZE);
        width = 32;
        height = 32;
    }

    /**
     * Constructs a Brick at a specified (x, y) position with default size.
     *
     * @param x the x-coordinate of the brick's position.
     * @param y the y-coordinate of the brick's position.
     */
    public Brick(int x, int y) {
        super(new Texture(BRICK_SPRITES_PNG), BRICK_SPRITES_SIZE, BRICK_SPRITES_SIZE);
        this.x = x;
        this.y = y;
        width = 32;
        height = 32;
    }

    /**
     * Constructs a Brick at a specified (x, y) position with a given width and height.
     *
     * @param x      the x-coordinate of the brick's position.
     * @param y      the y-coordinate of the brick's position.
     * @param width  the width of the brick.
     * @param height the height of the brick.
     */
    public Brick(int x, int y, int width, int height) {
        super(new Texture(BRICK_SPRITES_PNG), BRICK_SPRITES_SIZE, BRICK_SPRITES_SIZE);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        hitbox = new Rectangle(x, y, width, height / 2);
    }

    /**
     * Checks if the brick is alive.
     *
     * @return {@code true} if the brick is alive, otherwise {@code false}.
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Sets the brick as alive or dead.
     *
     * @param alive {@code true} to set the brick as alive, otherwise {@code false}.
     */
    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    /**
     * Reloads the brick texture from the spritesheet,
     * recreating sprite objects based on the texture regions.
     */
    public void reloadTexture() {
        Texture texture = new Texture(BRICK_SPRITES_PNG);
        sprites = new Sprite[BRICK_SPRITES_SIZE*BRICK_SPRITES_SIZE];
        for (int i = 0; i <  texture.getWidth()/BRICK_SPRITES_SIZE; i++) {
            for (int j = 0; j < texture.getHeight()/BRICK_SPRITES_SIZE; j++) {
                sprites[i+j] = new Sprite (new TextureRegion(texture, i*BRICK_SPRITES_SIZE, j*BRICK_SPRITES_SIZE, BRICK_SPRITES_SIZE, BRICK_SPRITES_SIZE));
            }
        }
    }

    /**
     * Inflicts damage to the brick by decreasing the active sprite.
     * If the active sprite drops below a threshold, the brick dies.
     * Returns the damage points based on the current active sprite.
     *
     * @return the damage points.
     */
    public int damage(){
        if (activeSprite > 4 || activeSprite < 1) return 0;
        activeSprite--;
        if (activeSprite < 1) isAlive = false;
        return switch (activeSprite){
            case 0 -> 100;
            case 1 -> 40;
            case 2 -> 20;
            case 3 -> 10;
            default -> 0;
        };
    }
}
