package input;

import game.GameState;
import ui.GamePanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Handles keyboard input for the Tetris game
 */
public class KeyHandler extends KeyAdapter {
    private GameState gameState;
    private GamePanel gamePanel;
    
    public KeyHandler(GameState gameState, GamePanel gamePanel) {
        this.gameState = gameState;
        this.gamePanel = gamePanel;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        switch (key) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                gameState.moveLeft();
                break;
                
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                gameState.moveRight();
                break;
                
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                gameState.moveDown();
                break;
                
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
            case KeyEvent.VK_SPACE:
                gameState.rotate();
                break;
                
            case KeyEvent.VK_SHIFT:
            case KeyEvent.VK_ENTER:
                gameState.hardDrop();
                break;
                
            case KeyEvent.VK_P:
            case KeyEvent.VK_ESCAPE:
                gameState.togglePause();
                break;
                
            case KeyEvent.VK_R:
                if (gameState.isGameOver()) {
                    gameState.reset();
                }
                break;
        }
        
        // Trigger repaint and statistics update after any key press
        if (gamePanel != null) {
            gamePanel.repaint();
        }
    }
}