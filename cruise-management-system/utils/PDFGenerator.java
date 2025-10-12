package utils;

import models.Booking;
import models.Cruise;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for generating booking tickets in PDF format
 * Uses Java's built-in PDF generation capabilities
 */
public class PDFGenerator {
    
    /**
     * Generates a PDF ticket file for a booking and opens it automatically
     */
    public static boolean generateTicket(Booking booking, Cruise cruise) {
        try {
            // Get user's Downloads folder
            String userHome = System.getProperty("user.home");
            String downloadsPath = userHome + "/Downloads";
            
            // Create Downloads directory if it doesn't exist
            File downloadsDir = new File(downloadsPath);
            if (!downloadsDir.exists()) {
                downloadsDir.mkdirs();
            }
            
            // Now using actual PDF extension
            String filename = downloadsPath + "/cruise_ticket_" + booking.getBookingId() + ".pdf";
            
            // Generate ticket content
            String content = generateTicketContent(booking, cruise);
            
            // Create actual PDF file
            SimplePDFWriter.createPDF(filename, content);
            
            System.out.println("Ticket saved to: " + filename);
            
            // Automatically open the ticket file
            try {
                File ticketFile = new File(filename);
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(ticketFile);
                    System.out.println("Ticket opened automatically");
                }
            } catch (Exception e) {
                System.out.println("Could not auto-open ticket, but file was saved successfully");
                // Try to open the Downloads folder instead
                try {
                    Desktop.getDesktop().open(downloadsDir);
                } catch (Exception ex) {
                    System.out.println("Could not open Downloads folder");
                }
            }
            
            return true;
            
        } catch (Exception e) {
            System.err.println("Failed to generate ticket: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Gets the full path to the saved ticket
     */
    public static String getTicketPath(int bookingId) {
        String userHome = System.getProperty("user.home");
        return userHome + "/Downloads/cruise_ticket_" + bookingId + ".pdf";
    }
    
    /**
     * Generates the formatted content for the ticket
     */
    private static String generateTicketContent(Booking booking, Cruise cruise) {
        StringBuilder sb = new StringBuilder();
        
        // Header
        sb.append("═══════════════════════════════════════════════════════════════════════════\n");
        sb.append("                       CRUISE BOOKING CONFIRMATION                          \n");
        sb.append("═══════════════════════════════════════════════════════════════════════════\n");
        sb.append("\n");
        
        // Booking Information
        sb.append("BOOKING DETAILS:\n");
        sb.append("───────────────────────────────────────────────────────────────────────────\n");
        sb.append(String.format("Booking ID:           %d\n", booking.getBookingId()));
        sb.append(String.format("Booking Date:         %s\n", 
            new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date())));
        sb.append(String.format("Passenger Name:       %s\n", booking.getPassengerName()));
        sb.append(String.format("Number of Guests:     %d\n", booking.getNumPassengers()));
        sb.append("\n");
        
        // Cruise Information
        sb.append("CRUISE DETAILS:\n");
        sb.append("───────────────────────────────────────────────────────────────────────────\n");
        sb.append(String.format("Cruise Name:          %s\n", cruise.getName()));
        sb.append(String.format("Route:                %s\n", cruise.getRoute()));
        sb.append(String.format("Departure Date:       %s\n", cruise.getDepartureDate()));
        sb.append(String.format("Return Date:          %s\n", cruise.getReturnDate()));
        sb.append("\n");
        
        // Accommodation Details
        sb.append("ACCOMMODATION:\n");
        sb.append("───────────────────────────────────────────────────────────────────────────\n");
        sb.append(String.format("Suite Type:           %s\n", booking.getSuiteType()));
        
        if (booking.getExtrasSelected() != null && !booking.getExtrasSelected().isEmpty()) {
            sb.append("Additional Services:\n");
            String[] extras = booking.getExtrasSelected().split(", ");
            for (String extra : extras) {
                sb.append("  • " + extra + "\n");
            }
        }
        sb.append("\n");
        
        // Payment Details
        sb.append("PAYMENT INFORMATION:\n");
        sb.append("───────────────────────────────────────────────────────────────────────────\n");
        sb.append(String.format("Total Amount Paid:    ₹%.2f\n", booking.getTotalPrice()));
        sb.append("Payment Status:       CONFIRMED\n");
        sb.append("\n");
        
        // QR Code placeholder (could be implemented with actual QR generation)
        sb.append("BOOKING REFERENCE:\n");
        sb.append("───────────────────────────────────────────────────────────────────────────\n");
        sb.append(String.format("Reference Code:       CRS-%d-%d\n", 
            cruise.getCruiseId(), booking.getBookingId()));
        sb.append("\n");
        
        // Important Information
        sb.append("IMPORTANT INFORMATION:\n");
        sb.append("───────────────────────────────────────────────────────────────────────────\n");
        sb.append("• Please arrive at the port 2 hours before departure\n");
        sb.append("• Bring valid government-issued photo ID\n");
        sb.append("• This ticket is non-transferable\n");
        sb.append("• Keep this confirmation for boarding\n");
        sb.append("• Contact customer service for cancellations or changes\n");
        sb.append("\n");
        
        // Check-in Instructions
        sb.append("CHECK-IN INSTRUCTIONS:\n");
        sb.append("───────────────────────────────────────────────────────────────────────────\n");
        sb.append("1. Present this ticket and photo ID at check-in counter\n");
        sb.append("2. Collect your boarding pass and cabin key\n");
        sb.append("3. Review the ship safety procedures\n");
        sb.append("4. Enjoy your cruise!\n");
        sb.append("\n");
        
        // Contact Information
        sb.append("CUSTOMER SERVICE:\n");
        sb.append("───────────────────────────────────────────────────────────────────────────\n");
        sb.append("Phone:                +1-800-CRUISE-NOW\n");
        sb.append("Email:                support@cruisemanagement.com\n");
        sb.append("Website:              www.cruisemanagement.com\n");
        sb.append("\n");
        
        // Footer
        sb.append("═══════════════════════════════════════════════════════════════════════════\n");
        sb.append("              Thank you for choosing our cruise service!                    \n");
        sb.append("                    Have a wonderful journey! ⚓                            \n");
        sb.append("═══════════════════════════════════════════════════════════════════════════\n");
        sb.append("\n");
        sb.append("This is an official booking confirmation. Please print and bring this document.\n");
        sb.append(String.format("Generated: %s\n", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())));
        
        return sb.toString();
    }
    
    /**
     * Opens the booking folder to show downloaded tickets
     */
    public static void openTicketsFolder() {
        try {
            File currentDir = new File(System.getProperty("user.dir"));
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(currentDir);
            }
        } catch (Exception e) {
            System.err.println("Could not open folder: " + e.getMessage());
        }
    }
}