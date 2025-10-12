import ui.LoginFrame;
import db.DatabaseManager;
import javax.swing.*;

/**
 * Main entry point for the Cruise Management System
 * Initializes the database and launches the login screen
 */
public class Main {
    public static void main(String[] args) {
        // Set system look and feel for better UI
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Initialize database
        DatabaseManager.initialize();
        
        // Launch login screen
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}