package ui;

import game.GameState;
import game.Tetromino;
import utils.Constants;
import javax.swing.*;
import java.awt.*;

/**
 * Panel displaying the next tetromino piece
 */
public class NextPiecePanel extends JPanel {
    private GameState gameState;
    
    public NextPiecePanel(GameState gameState) {
        this.gameState = gameState;
        
        setPreferredSize(new Dimension(200, 150));
        setBackground(Constants.PANEL_BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
    
    /**
     * Updates the next piece display
     */
    public void update() {
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw title
        g2d.setColor(Constants.TEXT_COLOR);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        FontMetrics fm = g2d.getFontMetrics();
        String title = "NEXT PIECE";
        int titleX = (getWidth() - fm.stringWidth(title)) / 2;
        g2d.drawString(title, titleX, 30);
        
        // Draw next piece
        Tetromino next = gameState.getNextPiece();
        if (next != null) {
            // Create a copy of the next piece in default rotation for display
            Tetromino displayPiece = new Tetromino(next.getType());
            int[][] shape = displayPiece.getShape();
            Color color = displayPiece.getColor();
            
            // Calculate centering offset
            int pieceWidth = 0;
            int pieceHeight = 0;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (shape[i][j] != 0) {
                        pieceWidth = Math.max(pieceWidth, j + 1);
                        pieceHeight = Math.max(pieceHeight, i + 1);
                    }
                }
            }
            
            int blockSize = 25;
            int startX = (getWidth() - pieceWidth * blockSize) / 2;
            int startY = 60;
            
            // Draw the piece
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (shape[i][j] != 0) {
                        int x = startX + j * blockSize;
                        int y = startY + i * blockSize;
                        
                        // Draw block with gradient effect
                        g2d.setColor(color);
                        g2d.fillRect(x, y, blockSize, blockSize);
                        
                        // Lighter top-left edge
                        g2d.setColor(color.brighter());
                        g2d.drawLine(x, y, x + blockSize - 1, y);
                        g2d.drawLine(x, y, x, y + blockSize - 1);
                        
                        // Darker bottom-right edge
                        g2d.setColor(color.darker());
                        g2d.drawLine(x + blockSize - 1, y, x + blockSize - 1, y + blockSize - 1);
                        g2d.drawLine(x, y + blockSize - 1, x + blockSize - 1, y + blockSize - 1);
                    }
                }
            }
        }
    }
}