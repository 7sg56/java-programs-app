package game;

import utils.Constants;
import java.awt.Color;

/**
 * Represents the game grid and handles collision detection and line clearing
 */
public class Grid {
    private Color[][] grid;
    
    public Grid() {
        grid = new Color[Constants.GRID_HEIGHT][Constants.GRID_WIDTH];
    }
    
    /**
     * Checks if a tetromino can be placed at the given position
     */
    public boolean canPlace(Tetromino piece) {
        int[][] shape = piece.getShape();
        int px = piece.getX();
        int py = piece.getY();
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (shape[i][j] != 0) {
                    int gridX = px + j;
                    int gridY = py + i;
                    
                    // Check boundaries
                    if (gridX < 0 || gridX >= Constants.GRID_WIDTH || 
                        gridY >= Constants.GRID_HEIGHT) {
                        return false;
                    }
                    
                    // Check collision with existing blocks (ignore if above grid)
                    if (gridY >= 0 && grid[gridY][gridX] != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    /**
     * Places a tetromino onto the grid permanently
     */
    public void place(Tetromino piece) {
        int[][] shape = piece.getShape();
        int px = piece.getX();
        int py = piece.getY();
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (shape[i][j] != 0) {
                    int gridX = px + j;
                    int gridY = py + i;
                    if (gridY >= 0 && gridY < Constants.GRID_HEIGHT && 
                        gridX >= 0 && gridX < Constants.GRID_WIDTH) {
                        grid[gridY][gridX] = piece.getColor();
                    }
                }
            }
        }
    }
    
    /**
     * Clears completed lines and returns the number cleared
     */
    public int clearLines() {
        int linesCleared = 0;
        
        for (int i = Constants.GRID_HEIGHT - 1; i >= 0; i--) {
            if (isLineFull(i)) {
                removeLine(i);
                linesCleared++;
                i++; // Check same line again after shift
            }
        }
        
        return linesCleared;
    }
    
    /**
     * Checks if a line is completely filled
     */
    private boolean isLineFull(int row) {
        for (int j = 0; j < Constants.GRID_WIDTH; j++) {
            if (grid[row][j] == null) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Removes a line and shifts everything above it down
     */
    private void removeLine(int row) {
        for (int i = row; i > 0; i--) {
            System.arraycopy(grid[i - 1], 0, grid[i], 0, Constants.GRID_WIDTH);
        }
        // Clear top line
        grid[0] = new Color[Constants.GRID_WIDTH];
    }
    
    /**
     * Checks if the game is over (blocks at top)
     */
    public boolean isGameOver() {
        for (int j = 0; j < Constants.GRID_WIDTH; j++) {
            if (grid[0][j] != null) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Clears the entire grid
     */
    public void clear() {
        grid = new Color[Constants.GRID_HEIGHT][Constants.GRID_WIDTH];
    }
    
    public Color[][] getGrid() {
        return grid;
    }
}