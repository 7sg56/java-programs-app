import game.GameState;
import ui.GamePanel;
import ui.NextPiecePanel;
import ui.ScoreBoard;
import input.KeyHandler;
import javax.swing.*;
import java.awt.*;

/**
 * Main class to launch the Tetris game
 * 
 * This application provides a complete Tetris game with:
 * - Classic Tetris gameplay with falling tetrominoes
 * - Score tracking and level progression
 * - Next piece preview
 * - Keyboard controls
 * - Game over and pause functionality
 * 
 * To run this application:
 * 1. Compile: javac -cp "." *.java game/*.java ui/*.java input/*.java utils/*.java
 * 2. Run: java Main
 * 
 * Or use an IDE like IntelliJ IDEA or Eclipse
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Tetris Game...");
        
        // Set look and feel (optional)
        
        // Create the main frame
        JFrame frame = new JFrame("Tetris Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        // Create game state
        GameState gameState = new GameState();
        
        // Create UI components
        GamePanel gamePanel = new GamePanel(gameState);
        NextPiecePanel nextPiecePanel = new NextPiecePanel(gameState);
        ScoreBoard scoreBoard = new ScoreBoard(gameState);
        
        // Connect ScoreBoard and NextPiecePanel to GamePanel for real-time updates
        gamePanel.setScoreBoard(scoreBoard);
        gamePanel.setNextPiecePanel(nextPiecePanel);
        
        // Create key handler
        KeyHandler keyHandler = new KeyHandler(gameState, gamePanel);
        gamePanel.addKeyListener(keyHandler);
        gamePanel.setFocusable(true);
        
        // Set up the layout
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        rightPanel.add(scoreBoard);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(new JLabel("Next Piece:"));
        rightPanel.add(nextPiecePanel);
        rightPanel.add(Box.createVerticalStrut(20));
        
        // Add controls information
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
        controlsPanel.setBorder(BorderFactory.createTitledBorder("Controls"));
        
        JLabel controlsText = new JLabel("<html>" +
            "<b>Controls:</b><br>" +
            "← → Move left/right<br>" +
            "↓ Soft drop<br>" +
            "↑ Hard drop<br>" +
            "Space Rotate<br>" +
            "P Pause/Resume<br>" +
            "R Restart game<br>" +
            "ESC Quit" +
            "</html>");
        controlsPanel.add(controlsText);
        
        rightPanel.add(controlsPanel);
        
        // Add components to frame
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.add(rightPanel, BorderLayout.EAST);
        
        // Pack and center the frame
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        // Start the game
        gamePanel.requestFocus();
        
        System.out.println("Tetris Game is now running!");
        System.out.println("Use arrow keys to move and rotate pieces!");
    }
}
