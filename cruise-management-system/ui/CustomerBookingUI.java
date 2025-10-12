package ui;

import db.*;
import models.*;
import utils.PDFGenerator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Customer interface for browsing and booking cruises
 */
public class CustomerBookingUI extends JFrame {
    private JTable cruisesTable;
    private DefaultTableModel cruisesTableModel;
    private JComboBox<String> suiteCombo;
    private JTextField nameField;
    private JSpinner passengersSpinner;
    private JCheckBox[] extraCheckboxes;
    private JLabel priceLabel;
    private Cruise selectedCruise;
    private List<Extra> availableExtras;
    
    public CustomerBookingUI() {
        setTitle("Customer Booking - Cruise Management System");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        availableExtras = SuiteDAO.getAllExtras();
        
        initComponents();
        loadAvailableCruises();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Top panel with title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(34, 139, 34));
        topPanel.setPreferredSize(new Dimension(1100, 60));
        
        JLabel titleLabel = new JLabel("🎫 Book Your Dream Cruise", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        
        JButton backButton = new JButton("🔙 Back");
        backButton.addActionListener(e -> goBack());
        topPanel.add(backButton, BorderLayout.WEST);
        
        // Main content panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Left panel - Available cruises
        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.setBorder(BorderFactory.createTitledBorder("Available Cruises"));
        
        String[] columns = {"ID", "Name", "Route", "Departure", "Return", "Available Seats", "Base Price"};
        cruisesTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        cruisesTable = new JTable(cruisesTableModel);
        cruisesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cruisesTable.setRowHeight(25);
        cruisesTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                onCruiseSelected();
            }
        });
        
        JScrollPane tableScrollPane = new JScrollPane(cruisesTable);
        
        JButton refreshButton = new JButton("🔄 Refresh");
        refreshButton.addActionListener(e -> loadAvailableCruises());
        
        JPanel leftBottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftBottomPanel.add(refreshButton);
        
        leftPanel.add(tableScrollPane, BorderLayout.CENTER);
        leftPanel.add(leftBottomPanel, BorderLayout.SOUTH);
        
        // Right panel - Booking form
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Booking Details"));
        rightPanel.setPreferredSize(new Dimension(400, 600));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Passenger name
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Passenger Name:"), gbc);
        
        nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = row++;
        formPanel.add(nameField, gbc);
        
        // Number of passengers
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Number of Passengers:"), gbc);
        
        passengersSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        passengersSpinner.addChangeListener(e -> calculatePrice());
        gbc.gridx = 1;
        gbc.gridy = row++;
        formPanel.add(passengersSpinner, gbc);
        
        // Suite type
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Suite Type:"), gbc);
        
        List<Suite> suites = SuiteDAO.getAllSuites();
        String[] suiteTypes = suites.stream().map(Suite::getSuiteType).toArray(String[]::new);
        suiteCombo = new JComboBox<>(suiteTypes);
        suiteCombo.addActionListener(e -> calculatePrice());
        gbc.gridx = 1;
        gbc.gridy = row++;
        formPanel.add(suiteCombo, gbc);
        
        // Extras section
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        JLabel extrasLabel = new JLabel("Extra Services:");
        extrasLabel.setFont(new Font("Arial", Font.BOLD, 12));
        formPanel.add(extrasLabel, gbc);
        
        extraCheckboxes = new JCheckBox[availableExtras.size()];
        for (int i = 0; i < availableExtras.size(); i++) {
            Extra extra = availableExtras.get(i);
            extraCheckboxes[i] = new JCheckBox(extra.getExtraName() + " (₹" + extra.getExtraPrice() + ")");
            extraCheckboxes[i].addActionListener(e -> calculatePrice());
            gbc.gridy = row++;
            formPanel.add(extraCheckboxes[i], gbc);
        }
        
        gbc.gridwidth = 1;
        
        // Price display
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel priceLabelText = new JLabel("Total Price:");
        priceLabelText.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(priceLabelText, gbc);
        
        priceLabel = new JLabel("₹0.00");
        priceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        priceLabel.setForeground(new Color(0, 128, 0));
        gbc.gridx = 1;
        gbc.gridy = row++;
        formPanel.add(priceLabel, gbc);
        
        // Book button
        JButton bookButton = new JButton("🎫 Book Now");
        bookButton.setFont(new Font("Arial", Font.BOLD, 14));
        bookButton.setBackground(new Color(34, 139, 34));
        bookButton.setForeground(Color.WHITE);
        bookButton.setFocusPainted(false);
        bookButton.addActionListener(e -> bookCruise());
        
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        formPanel.add(bookButton, gbc);
        
        // View bookings button
        JButton viewBookingsButton = new JButton("📋 View My Bookings");
        viewBookingsButton.addActionListener(e -> showBookingsDialog());
        gbc.gridy = row++;
        formPanel.add(viewBookingsButton, gbc);
        
        rightPanel.add(formPanel, BorderLayout.NORTH);
        
        // Add panels to main panel
        mainPanel.add(leftPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    /**
     * Loads available cruises into table
     */
    private void loadAvailableCruises() {
        cruisesTableModel.setRowCount(0);
        List<Cruise> cruises = CruiseDAO.getAvailableCruises();
        
        for (Cruise cruise : cruises) {
            int occupancy = CruiseDAO.getCruiseOccupancy(cruise.getCruiseId());
            int available = cruise.getCapacity() - occupancy;
            
            if (available > 0) {
                cruisesTableModel.addRow(new Object[]{
                    cruise.getCruiseId(),
                    cruise.getName(),
                    cruise.getRoute(),
                    cruise.getDepartureDate(),
                    cruise.getReturnDate(),
                    available,
                    "₹" + String.format("%.2f", cruise.getBasePrice())
                });
            }
        }
    }
    
    /**
     * Handles cruise selection
     */
    private void onCruiseSelected() {
        int selectedRow = cruisesTable.getSelectedRow();
        if (selectedRow != -1) {
            int cruiseId = (int) cruisesTableModel.getValueAt(selectedRow, 0);
            selectedCruise = CruiseDAO.getCruiseById(cruiseId);
            calculatePrice();
        }
    }
    
    /**
     * Calculates total booking price
     */
    private void calculatePrice() {
        if (selectedCruise == null) {
            priceLabel.setText("₹0.00");
            return;
        }
        
        double basePrice = selectedCruise.getBasePrice();
        int passengers = (int) passengersSpinner.getValue();
        
        // Apply suite multiplier
        String suiteType = (String) suiteCombo.getSelectedItem();
        Suite suite = SuiteDAO.getSuiteByType(suiteType);
        double multiplier = suite != null ? suite.getPriceMultiplier() : 1.0;
        
        double totalPrice = basePrice * multiplier * passengers;
        
        // Add extras
        for (int i = 0; i < extraCheckboxes.length; i++) {
            if (extraCheckboxes[i].isSelected()) {
                totalPrice += availableExtras.get(i).getExtraPrice();
            }
        }
        
        priceLabel.setText("₹" + String.format("%.2f", totalPrice));
    }
    
    /**
     * Books the selected cruise
     */
    private void bookCruise() {
        if (selectedCruise == null) {
            JOptionPane.showMessageDialog(this, "Please select a cruise first!");
            return;
        }
        
        String passengerName = nameField.getText().trim();
        if (passengerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter passenger name!");
            return;
        }
        
        int numPassengers = (int) passengersSpinner.getValue();
        int available = selectedCruise.getCapacity() - CruiseDAO.getCruiseOccupancy(selectedCruise.getCruiseId());
        
        if (numPassengers > available) {
            JOptionPane.showMessageDialog(this, 
                "Not enough seats available! Only " + available + " seats remaining.",
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String suiteType = (String) suiteCombo.getSelectedItem();
        
        // Collect selected extras
        List<String> selectedExtras = new ArrayList<>();
        for (int i = 0; i < extraCheckboxes.length; i++) {
            if (extraCheckboxes[i].isSelected()) {
                selectedExtras.add(availableExtras.get(i).getExtraName());
            }
        }
        String extrasStr = String.join(", ", selectedExtras);
        
        // Calculate total price
        double totalPrice = Double.parseDouble(priceLabel.getText().replace("₹", "").replace(",", ""));
        
        // Create booking
        Booking booking = new Booking();
        booking.setCruiseId(selectedCruise.getCruiseId());
        booking.setPassengerName(passengerName);
        booking.setSuiteType(suiteType);
        booking.setNumPassengers(numPassengers);
        booking.setExtrasSelected(extrasStr);
        booking.setTotalPrice(totalPrice);
        
        int bookingId = BookingDAO.createBooking(booking);
        
        if (bookingId > 0) {
            booking.setBookingId(bookingId);
            booking.setCruiseName(selectedCruise.getName());
            
            // Generate PDF ticket
            boolean pdfGenerated = PDFGenerator.generateTicket(booking, selectedCruise);
            
            String message = "🎉 Booking Successful!\n\n";
            message += "Booking ID: " + bookingId + "\n";
            message += "Passenger: " + passengerName + "\n";
            message += "Total Paid: ₹" + String.format("%.2f", totalPrice) + "\n\n";
            
            if (pdfGenerated) {
                message += "✅ PDF Ticket saved to Downloads folder!\n";
                message += "📄 File: cruise_ticket_" + bookingId + ".pdf\n";
                message += "📁 Location: ~/Downloads/\n\n";
                message += "Your PDF ticket has been opened automatically.\n";
                message += "Please print it for boarding.";
            } else {
                message += "⚠️ Ticket generation failed!\n";
                message += "Please contact customer service.";
            }
            
            JOptionPane.showMessageDialog(this, message, "Booking Confirmed", JOptionPane.INFORMATION_MESSAGE);
            
            // Clear form
            nameField.setText("");
            passengersSpinner.setValue(1);
            for (JCheckBox cb : extraCheckboxes) {
                cb.setSelected(false);
            }
            
            loadAvailableCruises();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Booking failed! Please try again.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Shows dialog to view and cancel bookings
     */
    private void showBookingsDialog() {
        JDialog dialog = new JDialog(this, "My Bookings", true);
        dialog.setSize(900, 500);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] columns = {"Booking ID", "Cruise", "Passengers", "Suite", "Total", "Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(model);
        table.setRowHeight(25);
        
        // Load all bookings
        List<Booking> bookings = BookingDAO.getAllBookings();
        for (Booking b : bookings) {
            model.addRow(new Object[]{
                b.getBookingId(),
                b.getCruiseName(),
                b.getNumPassengers(),
                b.getSuiteType(),
                "₹" + String.format("%.2f", b.getTotalPrice()),
                b.getBookingDate()
            });
        }
        
        JScrollPane scrollPane = new JScrollPane(table);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton downloadTicketButton = new JButton("🎫 Download Ticket");
        JButton cancelButton = new JButton("❌ Cancel Booking");
        JButton closeButton = new JButton("Close");
        
        downloadTicketButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(dialog, "Please select a booking to download ticket");
                return;
            }
            
            int bookingId = (int) model.getValueAt(selectedRow, 0);
            Booking booking = BookingDAO.getBookingById(bookingId);
            
            if (booking != null) {
                Cruise cruise = CruiseDAO.getCruiseById(booking.getCruiseId());
                if (cruise != null) {
                    boolean success = PDFGenerator.generateTicket(booking, cruise);
                    if (success) {
                        String filename = "cruise_ticket_" + bookingId + ".pdf";
                        String msg = "✅ PDF Ticket Downloaded!\n\n";
                        msg += "📄 File: " + filename + "\n";
                        msg += "📁 Location: ~/Downloads/\n\n";
                        msg += "Your PDF ticket has been opened automatically.\n";
                        msg += "Please print it for your records.";
                        
                        JOptionPane.showMessageDialog(dialog, msg, "PDF Ticket Downloaded", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(dialog, 
                            "❌ Failed to generate ticket!\n\nPlease try again or contact customer service.", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        cancelButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(dialog, "Please select a booking to cancel");
                return;
            }
            
            int bookingId = (int) model.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(dialog, 
                "Are you sure you want to cancel booking #" + bookingId + "?",
                "Confirm Cancellation",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (BookingDAO.cancelBooking(bookingId)) {
                    JOptionPane.showMessageDialog(dialog, "Booking cancelled successfully!");
                    model.removeRow(selectedRow);
                    loadAvailableCruises();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to cancel booking", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        closeButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(downloadTicketButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(closeButton);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    /**
     * Returns to login screen
     */
    private void goBack() {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
        this.dispose();
    }
}