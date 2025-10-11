package ui;

import game.GameState;
import game.Grid;
import game.Tetromino;
import utils.Constants;
import javax.swing.*;
import java.awt.*;

/**
 * Main game panel that renders the Tetris grid and handles the game loop
 */
public class GamePanel extends JPanel {
    private GameState gameState;
    private Timer gameTimer;
    private ScoreBoard scoreBoard;
    private NextPiecePanel nextPiecePanel;
    
    public GamePanel(GameState gameState) {
        this.gameState = gameState;
        
        int width = Constants.GRID_WIDTH * Constants.BLOCK_SIZE + Constants.BORDER_WIDTH * 2;
        int height = Constants.GRID_HEIGHT * Constants.BLOCK_SIZE + Constants.BORDER_WIDTH * 2;
        setPreferredSize(new Dimension(width, height));
        setBackground(Constants.BACKGROUND_COLOR);
        
        initGameLoop();
    }
    
    /**
     * Sets the ScoreBoard reference for updating statistics
     */
    public void setScoreBoard(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }
    
    /**
     * Sets the NextPiecePanel reference for updating next piece display
     */
    public void setNextPiecePanel(NextPiecePanel nextPiecePanel) {
        this.nextPiecePanel = nextPiecePanel;
    }
    
    /**
     * Initializes the game loop timer
     */
    private void initGameLoop() {
        gameTimer = new Timer(gameState.getGameSpeed(), e -> {
            if (!gameState.isPaused() && !gameState.isGameOver()) {
                gameState.moveDown();
                
                // Adjust timer speed based on level
                gameTimer.setDelay(gameState.getGameSpeed());
            }
            
            // Update statistics display
            if (scoreBoard != null) {
                scoreBoard.update();
            }
            
            // Update next piece display
            if (nextPiecePanel != null) {
                nextPiecePanel.update();
            }
            
            repaint();
        });
        gameTimer.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw border
        g2d.setColor(Constants.GRID_COLOR);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        // Draw background
        g2d.setColor(Constants.BACKGROUND_COLOR);
        g2d.fillRect(Constants.BORDER_WIDTH, Constants.BORDER_WIDTH, 
                    Constants.GRID_WIDTH * Constants.BLOCK_SIZE, 
                    Constants.GRID_HEIGHT * Constants.BLOCK_SIZE);
        
        // Draw grid lines
        drawGrid(g2d);
        
        // Draw placed blocks
        drawPlacedBlocks(g2d);
        
        // Draw current piece
        drawCurrentPiece(g2d);
        
        // Draw ghost piece (preview where piece will land)
        drawGhostPiece(g2d);
        
        // Draw game over or pause overlay
        if (gameState.isGameOver()) {
            drawGameOver(g2d);
        } else if (gameState.isPaused()) {
            drawPaused(g2d);
        }
    }
    
    /**
     * Draws grid lines
     */
    private void drawGrid(Graphics2D g2d) {
        g2d.setColor(Constants.GRID_COLOR);
        for (int i = 0; i <= Constants.GRID_HEIGHT; i++) {
            int y = Constants.BORDER_WIDTH + i * Constants.BLOCK_SIZE;
            g2d.drawLine(Constants.BORDER_WIDTH, y, 
                        Constants.BORDER_WIDTH + Constants.GRID_WIDTH * Constants.BLOCK_SIZE, y);
        }
        for (int j = 0; j <= Constants.GRID_WIDTH; j++) {
            int x = Constants.BORDER_WIDTH + j * Constants.BLOCK_SIZE;
            g2d.drawLine(x, Constants.BORDER_WIDTH, 
                        x, Constants.BORDER_WIDTH + Constants.GRID_HEIGHT * Constants.BLOCK_SIZE);
        }
    }
    
    /**
     * Draws all placed blocks on the grid
     */
    private void drawPlacedBlocks(Graphics2D g2d) {
        Grid grid = gameState.getGrid();
        Color[][] gridColors = grid.getGrid();
        
        for (int i = 0; i < Constants.GRID_HEIGHT; i++) {
            for (int j = 0; j < Constants.GRID_WIDTH; j++) {
                if (gridColors[i][j] != null) {
                    drawBlock(g2d, j, i, gridColors[i][j]);
                }
            }
        }
    }
    
    /**
     * Draws the current falling piece
     */
    private void drawCurrentPiece(Graphics2D g2d) {
        Tetromino piece = gameState.getCurrentPiece();
        if (piece == null) return;
        
        int[][] shape = piece.getShape();
        Color color = piece.getColor();
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (shape[i][j] != 0) {
                    int x = piece.getX() + j;
                    int y = piece.getY() + i;
                    if (y >= 0) {
                        drawBlock(g2d, x, y, color);
                    }
                }
            }
        }
    }
    
    /**
     * Draws a ghost piece showing where the current piece will land
     */
    private void drawGhostPiece(Graphics2D g2d) {
        if (gameState.isPaused() || gameState.isGameOver()) return;
        
        Tetromino piece = gameState.getCurrentPiece();
        if (piece == null) return;
        
        // Create a copy to calculate ghost position
        Tetromino ghost = new Tetromino(piece.getType());
        ghost.setX(piece.getX());
        ghost.setY(piece.getY());
        
        // Copy rotation state
        int[][] originalShape = piece.getShape();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ghost.getShape()[i][j] = originalShape[i][j];
            }
        }
        
        // Move ghost down until it can't move anymore
        Grid grid = gameState.getGrid();
        while (grid.canPlace(ghost)) {
            ghost.moveDown();
        }
        ghost.setY(ghost.getY() - 1); // Move back up one
        
        // Draw ghost piece with transparency
        int[][] shape = ghost.getShape();
        Color color = new Color(
            piece.getColor().getRed(),
            piece.getColor().getGreen(),
            piece.getColor().getBlue(),
            60 // Alpha transparency
        );
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (shape[i][j] != 0) {
                    int x = ghost.getX() + j;
                    int y = ghost.getY() + i;
                    if (y >= 0 && y != piece.getY() + i) { // Don't overlap with current piece
                        drawBlock(g2d, x, y, color);
                    }
                }
            }
        }
    }
    
    /**
     * Draws a single block with 3D effect
     */
    private void drawBlock(Graphics2D g2d, int gridX, int gridY, Color color) {
        int x = Constants.BORDER_WIDTH + gridX * Constants.BLOCK_SIZE;
        int y = Constants.BORDER_WIDTH + gridY * Constants.BLOCK_SIZE;
        int size = Constants.BLOCK_SIZE;
        
        // Main block
        g2d.setColor(color);
        g2d.fillRect(x + 1, y + 1, size - 2, size - 2);
        
        // Highlight (top-left)
        g2d.setColor(color.brighter());
        g2d.fillRect(x + 1, y + 1, size - 2, 3);
        g2d.fillRect(x + 1, y + 1, 3, size - 2);
        
        // Shadow (bottom-right)
        g2d.setColor(color.darker());
        g2d.fillRect(x + 1, y + size - 4, size - 2, 3);
        g2d.fillRect(x + size - 4, y + 1, 3, size - 2);
    }
    
    /**
     * Draws game over overlay
     */
    private void drawGameOver(Graphics2D g2d) {
        // Semi-transparent overlay
        g2d.setColor(new Color(0, 0, 0, 180));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        // Game Over text
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Arial", Font.BOLD, 36));
        String gameOver = "GAME OVER";
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(gameOver)) / 2;
        int y = getHeight() / 2 - 20;
        g2d.drawString(gameOver, x, y);
        
        // Instructions
        g2d.setColor(Constants.TEXT_COLOR);
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        String restart = "Press R to Restart";
        fm = g2d.getFontMetrics();
        x = (getWidth() - fm.stringWidth(restart)) / 2;
        y = getHeight() / 2 + 30;
        g2d.drawString(restart, x, y);
    }
    
    /**
     * Draws pause overlay
     */
    private void drawPaused(Graphics2D g2d) {
        // Semi-transparent overlay
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        // Paused text
        g2d.setColor(Color.YELLOW);
        g2d.setFont(new Font("Arial", Font.BOLD, 36));
        String paused = "PAUSED";
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(paused)) / 2;
        int y = getHeight() / 2;
        g2d.drawString(paused, x, y);
    }
    
    /**
     * Stops the game timer
     */
    public void stop() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
    }
}