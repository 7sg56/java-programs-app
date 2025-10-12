package db;

import models.Booking;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Booking operations
 */
public class BookingDAO {
    
    /**
     * Creates a new booking
     */
    public static int createBooking(Booking booking) {
        return DataStore.createBooking(booking);
    }
    
    /**
     * Gets all bookings
     */
    public static List<Booking> getAllBookings() {
        return DataStore.getAllBookings();
    }
    
    /**
     * Gets bookings by cruise ID
     */
    public static List<Booking> getBookingsByCruiseId(int cruiseId) {
        return DataStore.getBookingsByCruiseId(cruiseId);
    }
    
    /**
     * Cancels a booking
     */
    public static boolean cancelBooking(int bookingId) {
        return DataStore.cancelBooking(bookingId);
    }
    
    /**
     * Gets booking by ID
     */
    public static Booking getBookingById(int bookingId) {
        return DataStore.getBookingById(bookingId);
    }
}