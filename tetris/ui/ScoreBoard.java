package ui;

import game.GameState;
import utils.Constants;
import javax.swing.*;
import java.awt.*;

/**
 * Panel displaying score, lines cleared, and level information
 */
public class ScoreBoard extends JPanel {
    private GameState gameState;
    private JLabel scoreLabel;
    private JLabel linesLabel;
    private JLabel levelLabel;
    
    public ScoreBoard(GameState gameState) {
        this.gameState = gameState;
        
        setPreferredSize(new Dimension(200, 150));
        setBackground(Constants.PANEL_BACKGROUND);
        setLayout(new GridLayout(4, 1, 5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title
        JLabel title = new JLabel("STATISTICS", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(Constants.TEXT_COLOR);
        
        // Score
        scoreLabel = createStyledLabel("Score: 0");
        
        // Lines
        linesLabel = createStyledLabel("Lines: 0");
        
        // Level
        levelLabel = createStyledLabel("Level: 1");
        
        add(title);
        add(scoreLabel);
        add(linesLabel);
        add(levelLabel);
    }
    
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(Constants.TEXT_COLOR);
        return label;
    }
    
    /**
     * Updates the display with current game state
     */
    public void update() {
        scoreLabel.setText("Score: " + gameState.getScore());
        linesLabel.setText("Lines: " + gameState.getLinesCleared());
        levelLabel.setText("Level: " + gameState.getLevel());
    }
}