package db;

import models.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * In-memory data store for the cruise management system
 * This replaces database functionality for demonstration purposes
 */
public class DataStore {
    private static List<Cruise> cruises = new ArrayList<>();
    private static List<Booking> bookings = new ArrayList<>();
    private static List<Suite> suites = new ArrayList<>();
    private static List<Extra> extras = new ArrayList<>();
    
    private static AtomicInteger cruiseIdCounter = new AtomicInteger(4);
    private static AtomicInteger bookingIdCounter = new AtomicInteger(3);
    
    static {
        initializeData();
    }
    
    /**
     * Initialize sample data
     */
    private static void initializeData() {
        // Initialize cruises
        Date today = new Date(System.currentTimeMillis());
        Date departure1 = new Date(today.getTime() + 30L * 24 * 60 * 60 * 1000);
        Date return1 = new Date(today.getTime() + 37L * 24 * 60 * 60 * 1000);
        Date departure2 = new Date(today.getTime() + 45L * 24 * 60 * 60 * 1000);
        Date return2 = new Date(today.getTime() + 52L * 24 * 60 * 60 * 1000);
        Date departure3 = new Date(today.getTime() + 60L * 24 * 60 * 60 * 1000);
        Date return3 = new Date(today.getTime() + 67L * 24 * 60 * 60 * 1000);
        
        cruises.add(new Cruise(1, "Caribbean Paradise", "Miami - Bahamas - Miami", 
                             departure1, return1, 200, 1200.0, "OPEN"));
        cruises.add(new Cruise(2, "Mediterranean Explorer", "Barcelona - Rome - Athens - Barcelona", 
                             departure2, return2, 150, 2500.0, "OPEN"));
        cruises.add(new Cruise(3, "Alaskan Adventure", "Seattle - Juneau - Ketchikan - Seattle", 
                             departure3, return3, 100, 1800.0, "OPEN"));
        
        // Initialize suites
        suites.add(new Suite(1, "Interior", 1.0));
        suites.add(new Suite(2, "Ocean View", 1.3));
        suites.add(new Suite(3, "Balcony", 1.6));
        suites.add(new Suite(4, "Suite", 2.0));
        suites.add(new Suite(5, "Penthouse", 3.0));
        
        // Initialize extras
        extras.add(new Extra(1, "WiFi Package", 50.00));
        extras.add(new Extra(2, "Spa Package", 200.00));
        extras.add(new Extra(3, "Premium Dining", 150.00));
        extras.add(new Extra(4, "Shore Excursions", 300.00));
        extras.add(new Extra(5, "Photography Package", 100.00));
        extras.add(new Extra(6, "Beverage Package", 80.00));
        
        // Initialize sample bookings
        Booking booking1 = new Booking(1, 1, "John Doe", "Ocean View", 2, "WiFi Package, Spa Package", 3120.00);
        booking1.setCruiseName("Caribbean Paradise");
        booking1.setBookingDate(new Timestamp(System.currentTimeMillis() - 86400000));
        bookings.add(booking1);
        
        Booking booking2 = new Booking(2, 2, "Jane Smith", "Balcony", 1, "Premium Dining", 4150.00);
        booking2.setCruiseName("Mediterranean Explorer");
        booking2.setBookingDate(new Timestamp(System.currentTimeMillis() - 172800000));
        bookings.add(booking2);
    }
    
    // Cruise operations
    public static List<Cruise> getAllCruises() {
        return new ArrayList<>(cruises);
    }
    
    public static List<Cruise> getAvailableCruises() {
        List<Cruise> available = new ArrayList<>();
        for (Cruise cruise : cruises) {
            if ("OPEN".equals(cruise.getStatus())) {
                available.add(cruise);
            }
        }
        return available;
    }
    
    public static Cruise getCruiseById(int cruiseId) {
        for (Cruise cruise : cruises) {
            if (cruise.getCruiseId() == cruiseId) {
                return cruise;
            }
        }
        return null;
    }
    
    public static boolean addCruise(Cruise cruise) {
        cruise.setCruiseId(cruiseIdCounter.getAndIncrement());
        cruises.add(cruise);
        return true;
    }
    
    public static boolean updateCruise(Cruise cruise) {
        for (int i = 0; i < cruises.size(); i++) {
            if (cruises.get(i).getCruiseId() == cruise.getCruiseId()) {
                cruises.set(i, cruise);
                return true;
            }
        }
        return false;
    }
    
    public static boolean deleteCruise(int cruiseId) {
        return cruises.removeIf(cruise -> cruise.getCruiseId() == cruiseId);
    }
    
    public static int getCruiseOccupancy(int cruiseId) {
        int totalPassengers = 0;
        for (Booking booking : bookings) {
            if (booking.getCruiseId() == cruiseId) {
                totalPassengers += booking.getNumPassengers();
            }
        }
        return totalPassengers;
    }
    
    public static void updateCruiseStatus(int cruiseId) {
        Cruise cruise = getCruiseById(cruiseId);
        if (cruise != null) {
            int occupancy = getCruiseOccupancy(cruiseId);
            String newStatus = (occupancy >= cruise.getCapacity()) ? "FULL" : "OPEN";
            cruise.setStatus(newStatus);
        }
    }
    
    // Booking operations
    public static int createBooking(Booking booking) {
        int bookingId = bookingIdCounter.getAndIncrement();
        booking.setBookingId(bookingId);
        booking.setBookingDate(new Timestamp(System.currentTimeMillis()));
        
        // Set cruise name
        Cruise cruise = getCruiseById(booking.getCruiseId());
        if (cruise != null) {
            booking.setCruiseName(cruise.getName());
        }
        
        bookings.add(booking);
        updateCruiseStatus(booking.getCruiseId());
        
        System.out.println("Booking created: " + booking.getPassengerName() + " for cruise " + booking.getCruiseName());
        return bookingId;
    }
    
    public static List<Booking> getAllBookings() {
        return new ArrayList<>(bookings);
    }
    
    public static List<Booking> getBookingsByCruiseId(int cruiseId) {
        List<Booking> cruiseBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getCruiseId() == cruiseId) {
                cruiseBookings.add(booking);
            }
        }
        return cruiseBookings;
    }
    
    public static Booking getBookingById(int bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingId() == bookingId) {
                return booking;
            }
        }
        return null;
    }
    
    public static boolean cancelBooking(int bookingId) {
        Booking booking = getBookingById(bookingId);
        if (booking != null) {
            int cruiseId = booking.getCruiseId();
            boolean removed = bookings.removeIf(b -> b.getBookingId() == bookingId);
            if (removed) {
                updateCruiseStatus(cruiseId);
                System.out.println("Booking cancelled: ID " + bookingId);
            }
            return removed;
        }
        return false;
    }
    
    // Suite operations
    public static List<Suite> getAllSuites() {
        return new ArrayList<>(suites);
    }
    
    public static Suite getSuiteByType(String suiteType) {
        for (Suite suite : suites) {
            if (suite.getSuiteType().equals(suiteType)) {
                return suite;
            }
        }
        return null;
    }
    
    // Extra operations
    public static List<Extra> getAllExtras() {
        return new ArrayList<>(extras);
    }
}
