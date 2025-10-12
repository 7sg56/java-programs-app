package ui;

import db.*;
import models.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

/**
 * Admin Dashboard for managing cruises and bookings
 */
public class AdminDashboard extends JFrame {
    private JTable cruisesTable;
    private DefaultTableModel cruisesTableModel;
    private JTable bookingsTable;
    private DefaultTableModel bookingsTableModel;
    
    public AdminDashboard() {
        setTitle("Admin Dashboard - Cruise Management System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
        loadData();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Top panel with title and navigation
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(60, 120, 160));
        topPanel.setPreferredSize(new Dimension(1200, 60));
        
        JLabel titleLabel = new JLabel("🔐 Admin Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        
        JButton backButton = new JButton("🔙 Back to Login");
        backButton.addActionListener(e -> goBack());
        topPanel.add(backButton, BorderLayout.WEST);
        
        // Main content panel with tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Cruises tab
        JPanel cruisesPanel = createCruisesPanel();
        tabbedPane.addTab("🚢 Manage Cruises", cruisesPanel);
        
        // Bookings tab
        JPanel bookingsPanel = createBookingsPanel();
        tabbedPane.addTab("📋 View Bookings", bookingsPanel);
        
        // Reports tab
        JPanel reportsPanel = createReportsPanel();
        tabbedPane.addTab("📊 Reports", reportsPanel);
        
        add(topPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    private JPanel createCruisesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Cruises table
        String[] columns = {"ID", "Name", "Route", "Departure", "Return", "Capacity", "Base Price", "Status"};
        cruisesTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        cruisesTable = new JTable(cruisesTableModel);
        cruisesTable.setRowHeight(25);
        
        JScrollPane cruisesScrollPane = new JScrollPane(cruisesTable);
        
        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        
        JButton addCruiseButton = new JButton("➕ Add Cruise");
        addCruiseButton.addActionListener(e -> showAddCruiseDialog());
        
        JButton editCruiseButton = new JButton("✏️ Edit Cruise");
        editCruiseButton.addActionListener(e -> editSelectedCruise());
        
        JButton deleteCruiseButton = new JButton("🗑️ Delete Cruise");
        deleteCruiseButton.addActionListener(e -> deleteSelectedCruise());
        
        JButton refreshButton = new JButton("🔄 Refresh");
        refreshButton.addActionListener(e -> loadCruises());
        
        buttonsPanel.add(addCruiseButton);
        buttonsPanel.add(editCruiseButton);
        buttonsPanel.add(deleteCruiseButton);
        buttonsPanel.add(refreshButton);
        
        panel.add(cruisesScrollPane, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createBookingsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Bookings table
        String[] columns = {"Booking ID", "Cruise", "Passenger", "Suite", "Passengers", "Total Price", "Date"};
        bookingsTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bookingsTable = new JTable(bookingsTableModel);
        bookingsTable.setRowHeight(25);
        
        JScrollPane bookingsScrollPane = new JScrollPane(bookingsTable);
        
        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        
        JButton cancelBookingButton = new JButton("❌ Cancel Booking");
        cancelBookingButton.addActionListener(e -> cancelSelectedBooking());
        
        JButton refreshButton = new JButton("🔄 Refresh");
        refreshButton.addActionListener(e -> loadBookings());
        
        buttonsPanel.add(cancelBookingButton);
        buttonsPanel.add(refreshButton);
        
        panel.add(bookingsScrollPane, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea reportsArea = new JTextArea(20, 50);
        reportsArea.setEditable(false);
        reportsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane reportsScrollPane = new JScrollPane(reportsArea);
        
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        
        JButton generateReportButton = new JButton("📊 Generate Reports");
        generateReportButton.addActionListener(e -> generateReports(reportsArea));
        
        buttonsPanel.add(generateReportButton);
        
        panel.add(reportsScrollPane, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void loadData() {
        loadCruises();
        loadBookings();
    }
    
    private void loadCruises() {
        cruisesTableModel.setRowCount(0);
        List<Cruise> cruises = CruiseDAO.getAllCruises();
        
        for (Cruise cruise : cruises) {
            cruisesTableModel.addRow(new Object[]{
                cruise.getCruiseId(),
                cruise.getName(),
                cruise.getRoute(),
                cruise.getDepartureDate(),
                cruise.getReturnDate(),
                cruise.getCapacity(),
                "₹" + String.format("%.2f", cruise.getBasePrice()),
                cruise.getStatus()
            });
        }
    }
    
    private void loadBookings() {
        bookingsTableModel.setRowCount(0);
        List<Booking> bookings = BookingDAO.getAllBookings();
        
        for (Booking booking : bookings) {
            bookingsTableModel.addRow(new Object[]{
                booking.getBookingId(),
                booking.getCruiseName(),
                booking.getPassengerName(),
                booking.getSuiteType(),
                booking.getNumPassengers(),
                "₹" + String.format("%.2f", booking.getTotalPrice()),
                booking.getBookingDate()
            });
        }
    }
    
    private void showAddCruiseDialog() {
        JDialog dialog = new JDialog(this, "Add New Cruise", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Name
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Cruise Name:"), gbc);
        JTextField nameField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = row++;
        panel.add(nameField, gbc);
        
        // Route
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Route:"), gbc);
        JTextField routeField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = row++;
        panel.add(routeField, gbc);
        
        // Departure Date
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Departure Date:"), gbc);
        JTextField departureField = new JTextField(20);
        departureField.setText("2024-12-01");
        gbc.gridx = 1; gbc.gridy = row++;
        panel.add(departureField, gbc);
        
        // Return Date
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Return Date:"), gbc);
        JTextField returnField = new JTextField(20);
        returnField.setText("2024-12-08");
        gbc.gridx = 1; gbc.gridy = row++;
        panel.add(returnField, gbc);
        
        // Capacity
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Capacity:"), gbc);
        JSpinner capacitySpinner = new JSpinner(new SpinnerNumberModel(100, 1, 1000, 1));
        gbc.gridx = 1; gbc.gridy = row++;
        panel.add(capacitySpinner, gbc);
        
        // Base Price
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Base Price:"), gbc);
        JSpinner priceSpinner = new JSpinner(new SpinnerNumberModel(1000.0, 0.0, 10000.0, 100.0));
        gbc.gridx = 1; gbc.gridy = row++;
        panel.add(priceSpinner, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        saveButton.addActionListener(e -> {
            try {
                Cruise cruise = new Cruise();
                cruise.setName(nameField.getText());
                cruise.setRoute(routeField.getText());
                cruise.setDepartureDate(Date.valueOf(departureField.getText()));
                cruise.setReturnDate(Date.valueOf(returnField.getText()));
                cruise.setCapacity((Integer) capacitySpinner.getValue());
                cruise.setBasePrice((Double) priceSpinner.getValue());
                cruise.setStatus("OPEN");
                
                if (CruiseDAO.addCruise(cruise)) {
                    JOptionPane.showMessageDialog(dialog, "Cruise added successfully!");
                    dialog.dispose();
                    loadCruises();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to add cruise!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid data format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        panel.add(buttonPanel, gbc);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void editSelectedCruise() {
        int selectedRow = cruisesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a cruise to edit!");
            return;
        }
        
        int cruiseId = (int) cruisesTableModel.getValueAt(selectedRow, 0);
        Cruise cruise = CruiseDAO.getCruiseById(cruiseId);
        
        if (cruise != null) {
            showEditCruiseDialog(cruise);
        }
    }
    
    private void showEditCruiseDialog(Cruise cruise) {
        JDialog dialog = new JDialog(this, "Edit Cruise", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Name
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Cruise Name:"), gbc);
        JTextField nameField = new JTextField(20);
        nameField.setText(cruise.getName());
        gbc.gridx = 1; gbc.gridy = row++;
        panel.add(nameField, gbc);
        
        // Route
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Route:"), gbc);
        JTextField routeField = new JTextField(20);
        routeField.setText(cruise.getRoute());
        gbc.gridx = 1; gbc.gridy = row++;
        panel.add(routeField, gbc);
        
        // Departure Date
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Departure Date:"), gbc);
        JTextField departureField = new JTextField(20);
        departureField.setText(cruise.getDepartureDate().toString());
        gbc.gridx = 1; gbc.gridy = row++;
        panel.add(departureField, gbc);
        
        // Return Date
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Return Date:"), gbc);
        JTextField returnField = new JTextField(20);
        returnField.setText(cruise.getReturnDate().toString());
        gbc.gridx = 1; gbc.gridy = row++;
        panel.add(returnField, gbc);
        
        // Capacity
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Capacity:"), gbc);
        JSpinner capacitySpinner = new JSpinner(new SpinnerNumberModel(cruise.getCapacity(), 1, 1000, 1));
        gbc.gridx = 1; gbc.gridy = row++;
        panel.add(capacitySpinner, gbc);
        
        // Base Price
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Base Price:"), gbc);
        JSpinner priceSpinner = new JSpinner(new SpinnerNumberModel(cruise.getBasePrice(), 0.0, 10000.0, 100.0));
        gbc.gridx = 1; gbc.gridy = row++;
        panel.add(priceSpinner, gbc);
        
        // Status
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Status:"), gbc);
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"OPEN", "FULL", "CANCELLED"});
        statusCombo.setSelectedItem(cruise.getStatus());
        gbc.gridx = 1; gbc.gridy = row++;
        panel.add(statusCombo, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        saveButton.addActionListener(e -> {
            try {
                cruise.setName(nameField.getText());
                cruise.setRoute(routeField.getText());
                cruise.setDepartureDate(Date.valueOf(departureField.getText()));
                cruise.setReturnDate(Date.valueOf(returnField.getText()));
                cruise.setCapacity((Integer) capacitySpinner.getValue());
                cruise.setBasePrice((Double) priceSpinner.getValue());
                cruise.setStatus((String) statusCombo.getSelectedItem());
                
                if (CruiseDAO.updateCruise(cruise)) {
                    JOptionPane.showMessageDialog(dialog, "Cruise updated successfully!");
                    dialog.dispose();
                    loadCruises();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to update cruise!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid data format: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void deleteSelectedCruise() {
        int selectedRow = cruisesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a cruise to delete!");
            return;
        }
        
        int cruiseId = (int) cruisesTableModel.getValueAt(selectedRow, 0);
        String cruiseName = (String) cruisesTableModel.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete cruise: " + cruiseName + "?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (CruiseDAO.deleteCruise(cruiseId)) {
                JOptionPane.showMessageDialog(this, "Cruise deleted successfully!");
                loadCruises();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete cruise!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void cancelSelectedBooking() {
        int selectedRow = bookingsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to cancel!");
            return;
        }
        
        int bookingId = (int) bookingsTableModel.getValueAt(selectedRow, 0);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to cancel booking #" + bookingId + "?",
            "Confirm Cancellation",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (BookingDAO.cancelBooking(bookingId)) {
                JOptionPane.showMessageDialog(this, "Booking cancelled successfully!");
                loadBookings();
                loadCruises(); // Refresh cruise status
            } else {
                JOptionPane.showMessageDialog(this, "Failed to cancel booking!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void generateReports(JTextArea reportsArea) {
        StringBuilder report = new StringBuilder();
        report.append("CRUISE MANAGEMENT SYSTEM - REPORTS\n");
        report.append("==================================\n\n");
        
        // Cruise Statistics
        List<Cruise> cruises = CruiseDAO.getAllCruises();
        report.append("CRUISE STATISTICS:\n");
        report.append("------------------\n");
        report.append("Total Cruises: ").append(cruises.size()).append("\n");
        
        int openCruises = 0;
        int fullCruises = 0;
        for (Cruise cruise : cruises) {
            if ("OPEN".equals(cruise.getStatus())) {
                openCruises++;
            } else if ("FULL".equals(cruise.getStatus())) {
                fullCruises++;
            }
        }
        
        report.append("Open Cruises: ").append(openCruises).append("\n");
        report.append("Full Cruises: ").append(fullCruises).append("\n\n");
        
        // Booking Statistics
        List<Booking> bookings = BookingDAO.getAllBookings();
        report.append("BOOKING STATISTICS:\n");
        report.append("-------------------\n");
        report.append("Total Bookings: ").append(bookings.size()).append("\n");
        
        double totalRevenue = 0;
        for (Booking booking : bookings) {
            totalRevenue += booking.getTotalPrice();
        }
        
        report.append("Total Revenue: ₹").append(String.format("%.2f", totalRevenue)).append("\n\n");
        
        // Recent Bookings
        report.append("RECENT BOOKINGS:\n");
        report.append("----------------\n");
        int count = 0;
        for (Booking booking : bookings) {
            if (count >= 10) break; // Show only last 10
            report.append("Booking #").append(booking.getBookingId())
                  .append(" - ").append(booking.getPassengerName())
                  .append(" - ").append(booking.getCruiseName())
                  .append(" - ₹").append(String.format("%.2f", booking.getTotalPrice()))
                  .append("\n");
            count++;
        }
        
        reportsArea.setText(report.toString());
    }
    
    private void goBack() {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
        this.dispose();
    }
}
