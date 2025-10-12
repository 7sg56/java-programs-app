package ui;

import javax.swing.*;
import java.awt.*;

/**
 * Login screen for the Cruise Management System
 * Allows users to choose between Admin and Customer modes
 */
public class LoginFrame extends JFrame {
    
    public LoginFrame() {
        setTitle("Cruise Management System - Login");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
    }
    
    private void initComponents() {
        // Main panel with gradient-like background
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
        
        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setPreferredSize(new Dimension(500, 100));
        headerPanel.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("⚓ Cruise Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        // Center panel with buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome! Please select your role:");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        welcomeLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(welcomeLabel, gbc);
        
        // Admin button
        JButton adminButton = new JButton("🔐 Admin Dashboard");
        adminButton.setFont(new Font("Arial", Font.BOLD, 16));
        adminButton.setPreferredSize(new Dimension(250, 50));
        adminButton.setBackground(new Color(60, 120, 160));
        adminButton.setForeground(Color.WHITE);
        adminButton.setFocusPainted(false);
        adminButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        adminButton.setOpaque(true);
        adminButton.addActionListener(e -> openAdminDashboard());
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        centerPanel.add(adminButton, gbc);
        
        // Customer button
        JButton customerButton = new JButton("🎫 Customer Booking");
        customerButton.setFont(new Font("Arial", Font.BOLD, 16));
        customerButton.setPreferredSize(new Dimension(250, 50));
        customerButton.setBackground(new Color(34, 139, 34));
        customerButton.setForeground(Color.WHITE);
        customerButton.setFocusPainted(false);
        customerButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        customerButton.setOpaque(true);
        customerButton.addActionListener(e -> openCustomerBooking());
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        centerPanel.add(customerButton, gbc);
        
        // Info label
        JLabel infoLabel = new JLabel("<html><center>Admin: Manage cruises and view reports<br>" +
                                      "Customer: Browse and book cruises</center></html>");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        infoLabel.setForeground(Color.GRAY);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        centerPanel.add(infoLabel, gbc);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    /**
     * Opens Admin Dashboard
     */
    private void openAdminDashboard() {
        // Simple password check
        String password = JOptionPane.showInputDialog(this, 
            "Enter Admin Password:", 
            "Admin Login", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (password != null && password.equals("admin123")) {
            AdminDashboard dashboard = new AdminDashboard();
            dashboard.setVisible(true);
            this.dispose();
        } else if (password != null) {
            JOptionPane.showMessageDialog(this, 
                "Incorrect password!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Opens Customer Booking interface
     */
    private void openCustomerBooking() {
        CustomerBookingUI bookingUI = new CustomerBookingUI();
        bookingUI.setVisible(true);
        this.dispose();
    }
}