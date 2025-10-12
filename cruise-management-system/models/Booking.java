package models;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Model class representing a Booking
 */
public class Booking {
    private int bookingId;
    private int cruiseId;
    private String cruiseName;
    private String passengerName;
    private String suiteType;
    private int numPassengers;
    private String extrasSelected;
    private double totalPrice;
    private Timestamp bookingDate;
    
    // Default constructor
    public Booking() {}
    
    // Constructor with parameters
    public Booking(int bookingId, int cruiseId, String passengerName, String suiteType, 
                   int numPassengers, String extrasSelected, double totalPrice) {
        this.bookingId = bookingId;
        this.cruiseId = cruiseId;
        this.passengerName = passengerName;
        this.suiteType = suiteType;
        this.numPassengers = numPassengers;
        this.extrasSelected = extrasSelected;
        this.totalPrice = totalPrice;
        this.bookingDate = new Timestamp(System.currentTimeMillis());
    }
    
    // Getters and Setters
    public int getBookingId() {
        return bookingId;
    }
    
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
    
    public int getCruiseId() {
        return cruiseId;
    }
    
    public void setCruiseId(int cruiseId) {
        this.cruiseId = cruiseId;
    }
    
    public String getCruiseName() {
        return cruiseName;
    }
    
    public void setCruiseName(String cruiseName) {
        this.cruiseName = cruiseName;
    }
    
    public String getPassengerName() {
        return passengerName;
    }
    
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }
    
    public String getSuiteType() {
        return suiteType;
    }
    
    public void setSuiteType(String suiteType) {
        this.suiteType = suiteType;
    }
    
    public int getNumPassengers() {
        return numPassengers;
    }
    
    public void setNumPassengers(int numPassengers) {
        this.numPassengers = numPassengers;
    }
    
    public String getExtrasSelected() {
        return extrasSelected;
    }
    
    public void setExtrasSelected(String extrasSelected) {
        this.extrasSelected = extrasSelected;
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public Timestamp getBookingDate() {
        return bookingDate;
    }
    
    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }
    
    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", cruiseId=" + cruiseId +
                ", cruiseName='" + cruiseName + '\'' +
                ", passengerName='" + passengerName + '\'' +
                ", suiteType='" + suiteType + '\'' +
                ", numPassengers=" + numPassengers +
                ", extrasSelected='" + extrasSelected + '\'' +
                ", totalPrice=" + totalPrice +
                ", bookingDate=" + bookingDate +
                '}';
    }
}
