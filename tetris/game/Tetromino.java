package game;

import utils.Constants;
import java.awt.Color;

/**
 * Represents a Tetromino piece with its shape, position, and color
 */
public class Tetromino {
    public enum Type {
        I, O, T, S, Z, J, L
    }
    
    private Type type;
    private int[][] shape;
    private int x, y;
    private Color color;
    private int rotation;
    
    // Tetromino shapes (4x4 matrices)
    private static final int[][][] SHAPES = {
        // I piece
        {{0,0,0,0}, {1,1,1,1}, {0,0,0,0}, {0,0,0,0}},
        // O piece
        {{0,0,0,0}, {0,1,1,0}, {0,1,1,0}, {0,0,0,0}},
        // T piece
        {{0,0,0,0}, {0,1,0,0}, {1,1,1,0}, {0,0,0,0}},
        // S piece
        {{0,0,0,0}, {0,1,1,0}, {1,1,0,0}, {0,0,0,0}},
        // Z piece
        {{0,0,0,0}, {1,1,0,0}, {0,1,1,0}, {0,0,0,0}},
        // J piece
        {{0,0,0,0}, {1,0,0,0}, {1,1,1,0}, {0,0,0,0}},
        // L piece
        {{0,0,0,0}, {0,0,1,0}, {1,1,1,0}, {0,0,0,0}}
    };
    
    private static final Color[] COLORS = {
        Color.CYAN,    // I
        Color.YELLOW,  // O
        Color.MAGENTA, // T
        Color.GREEN,   // S
        Color.RED,     // Z
        Color.BLUE,    // J
        Color.ORANGE   // L
    };
    
    public Tetromino(Type type) {
        this.type = type;
        this.x = Constants.GRID_WIDTH / 2 - 2;
        this.y = 0;
        this.rotation = 0;
        this.color = COLORS[type.ordinal()];
        updateShape();
    }
    
    /**
     * Updates the shape based on current rotation
     */
    private void updateShape() {
        int[][] baseShape = SHAPES[type.ordinal()];
        shape = new int[4][4];
        
        // Apply rotation
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                switch (rotation) {
                    case 0: // 0 degrees
                        shape[i][j] = baseShape[i][j];
                        break;
                    case 1: // 90 degrees
                        shape[i][j] = baseShape[3-j][i];
                        break;
                    case 2: // 180 degrees
                        shape[i][j] = baseShape[3-i][3-j];
                        break;
                    case 3: // 270 degrees
                        shape[i][j] = baseShape[j][3-i];
                        break;
                }
            }
        }
    }
    
    /**
     * Rotates the piece clockwise
     */
    public void rotate() {
        rotation = (rotation + 1) % 4;
        updateShape();
    }
    
    /**
     * Rotates the piece counter-clockwise
     */
    public void rotateCounterClockwise() {
        rotation = (rotation + 3) % 4;
        updateShape();
    }
    
    /**
     * Moves the piece by the given offset
     */
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }
    
    /**
     * Moves the piece down
     */
    public void moveDown() {
        y++;
    }
    
    /**
     * Moves the piece left
     */
    public void moveLeft() {
        x--;
    }
    
    /**
     * Moves the piece right
     */
    public void moveRight() {
        x++;
    }
    
    /**
     * Rotates the piece back (undo rotation)
     */
    public void rotateBack() {
        rotation = (rotation + 3) % 4;
        updateShape();
    }
    
    /**
     * Creates a copy of this tetromino
     */
    public Tetromino copy() {
        Tetromino copy = new Tetromino(type);
        copy.x = this.x;
        copy.y = this.y;
        copy.rotation = this.rotation;
        copy.updateShape();
        return copy;
    }
    
    // Getters
    public Type getType() { return type; }
    public int[][] getShape() { return shape; }
    public int getX() { return x; }
    public int getY() { return y; }
    public Color getColor() { return color; }
    public int getRotation() { return rotation; }
    
    // Setters
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}