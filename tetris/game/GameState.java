package game;

import utils.Constants;
import java.util.Random;

/**
 * Manages the overall game state including score, level, and pieces
 */
public class GameState {
    private Grid grid;
    private Tetromino currentPiece;
    private Tetromino nextPiece;
    private int score;
    private int linesCleared;
    private int level;
    private boolean gameOver;
    private boolean paused;
    private Random random;
    
    public GameState() {
        this.grid = new Grid();
        this.random = new Random();
        this.score = 0;
        this.linesCleared = 0;
        this.level = 1;
        this.gameOver = false;
        this.paused = false;
        
        // Initialize pieces
        this.nextPiece = createRandomPiece();
        spawnNewPiece();
    }
    
    /**
     * Creates a random tetromino
     */
    private Tetromino createRandomPiece() {
        Tetromino.Type[] types = Tetromino.Type.values();
        return new Tetromino(types[random.nextInt(types.length)]);
    }
    
    /**
     * Spawns a new piece from the next piece
     */
    public void spawnNewPiece() {
        currentPiece = nextPiece;
        nextPiece = createRandomPiece();
        
        if (!grid.canPlace(currentPiece)) {
            gameOver = true;
        }
    }
    
    /**
     * Moves the current piece down and handles placement
     */
    public boolean moveDown() {
        if (gameOver || paused) return false;
        
        currentPiece.moveDown();
        
        if (!grid.canPlace(currentPiece)) {
            currentPiece.setY(currentPiece.getY() - 1); // Move back up
            grid.place(currentPiece);
            
            int lines = grid.clearLines();
            updateScore(lines);
            
            if (grid.isGameOver()) {
                gameOver = true;
                return false;
            }
            
            spawnNewPiece();
            return false;
        }
        return true;
    }
    
    /**
     * Attempts to move the piece left
     */
    public void moveLeft() {
        if (gameOver || paused) return;
        
        currentPiece.moveLeft();
        if (!grid.canPlace(currentPiece)) {
            currentPiece.moveRight(); // Undo
        }
    }
    
    /**
     * Attempts to move the piece right
     */
    public void moveRight() {
        if (gameOver || paused) return;
        
        currentPiece.moveRight();
        if (!grid.canPlace(currentPiece)) {
            currentPiece.moveLeft(); // Undo
        }
    }
    
    /**
     * Attempts to rotate the piece
     */
    public void rotate() {
        if (gameOver || paused) return;
        
        currentPiece.rotate();
        if (!grid.canPlace(currentPiece)) {
            currentPiece.rotateBack(); // Undo
        }
    }
    
    /**
     * Hard drop - instantly drop piece to bottom
     */
    public void hardDrop() {
        if (gameOver || paused) return;
        
        while (moveDown()) {
            score += 2; // Bonus points for hard drop
        }
    }
    
    /**
     * Updates score based on lines cleared
     */
    private void updateScore(int lines) {
        if (lines == 0) return;
        
        linesCleared += lines;
        
        // Award points based on lines cleared
        switch (lines) {
            case 1: score += Constants.POINTS_SINGLE * level; break;
            case 2: score += Constants.POINTS_DOUBLE * level; break;
            case 3: score += Constants.POINTS_TRIPLE * level; break;
            case 4: score += Constants.POINTS_TETRIS * level; break;
        }
        
        // Update level
        level = 1 + (linesCleared / Constants.LINES_PER_LEVEL);
    }
    
    /**
     * Toggles pause state
     */
    public void togglePause() {
        if (!gameOver) {
            paused = !paused;
        }
    }
    
    /**
     * Resets the game
     */
    public void reset() {
        grid.clear();
        score = 0;
        linesCleared = 0;
        level = 1;
        gameOver = false;
        paused = false;
        nextPiece = createRandomPiece();
        spawnNewPiece();
    }
    
    /**
     * Gets the current game speed based on level
     */
    public int getGameSpeed() {
        int speed = Constants.INITIAL_DELAY - ((level - 1) * Constants.DELAY_DECREASE);
        return Math.max(speed, Constants.MIN_DELAY);
    }
    
    // Getters
    public Grid getGrid() { return grid; }
    public Tetromino getCurrentPiece() { return currentPiece; }
    public Tetromino getNextPiece() { return nextPiece; }
    public int getScore() { return score; }
    public int getLinesCleared() { return linesCleared; }
    public int getLevel() { return level; }
    public boolean isGameOver() { return gameOver; }
    public boolean isPaused() { return paused; }
}