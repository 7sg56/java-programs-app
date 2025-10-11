package utils;

import java.awt.Color;

/**
 * Constants used throughout the Tetris game
 */
public class Constants {
    // Grid dimensions
    public static final int GRID_WIDTH = 10;
    public static final int GRID_HEIGHT = 20;
    
    // Visual settings
    public static final int BLOCK_SIZE = 30;
    public static final int BORDER_WIDTH = 2;
    
    // Game timing
    public static final int INITIAL_DELAY = 500; // milliseconds
    public static final int MIN_DELAY = 100;
    public static final int DELAY_DECREASE = 50;
    
    // Scoring
    public static final int POINTS_SINGLE = 100;
    public static final int POINTS_DOUBLE = 300;
    public static final int POINTS_TRIPLE = 500;
    public static final int POINTS_TETRIS = 800;
    public static final int LINES_PER_LEVEL = 10;
    
    // Tetromino shapes (4x4 grid representation)
    public static final int[][][] SHAPES = {
        // I piece
        {{0,0,0,0}, {1,1,1,1}, {0,0,0,0}, {0,0,0,0}},
        // O piece
        {{0,0,0,0}, {0,1,1,0}, {0,1,1,0}, {0,0,0,0}},
        // T piece
        {{0,0,0,0}, {0,1,1,1}, {0,0,1,0}, {0,0,0,0}},
        // S piece
        {{0,0,0,0}, {0,0,1,1}, {0,1,1,0}, {0,0,0,0}},
        // Z piece
        {{0,0,0,0}, {0,1,1,0}, {0,0,1,1}, {0,0,0,0}},
        // J piece
        {{0,0,0,0}, {0,1,1,1}, {0,1,0,0}, {0,0,0,0}},
        // L piece
        {{0,0,0,0}, {0,1,1,1}, {0,0,0,1}, {0,0,0,0}}
    };
    
    // Tetromino colors
    public static final Color[] COLORS = {
        new Color(0, 240, 240),   // I - Cyan
        new Color(240, 240, 0),   // O - Yellow
        new Color(160, 0, 240),   // T - Purple
        new Color(0, 240, 0),     // S - Green
        new Color(240, 0, 0),     // Z - Red
        new Color(0, 0, 240),     // J - Blue
        new Color(240, 160, 0)    // L - Orange
    };
    
    // UI Colors
    public static final Color BACKGROUND_COLOR = new Color(20, 20, 30);
    public static final Color GRID_COLOR = new Color(40, 40, 60);
    public static final Color TEXT_COLOR = Color.WHITE;
    public static final Color PANEL_BACKGROUND = new Color(30, 30, 45);
    
    private Constants() {} // Prevent instantiation
}