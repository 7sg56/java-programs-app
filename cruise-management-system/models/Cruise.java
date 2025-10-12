package models;

import java.sql.Date;

/**
 * Model class representing a Cruise
 */
public class Cruise {
    private int cruiseId;
    private String name;
    private String route;
    private Date departureDate;
    private Date returnDate;
    private int capacity;
    private double basePrice;
    private String status;
    
    // Default constructor
    public Cruise() {}
    
    // Constructor with all parameters
    public Cruise(int cruiseId, String name, String route, Date departureDate, 
                  Date returnDate, int capacity, double basePrice, String status) {
        this.cruiseId = cruiseId;
        this.name = name;
        this.route = route;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.capacity = capacity;
        this.basePrice = basePrice;
        this.status = status;
    }
    
    // Getters and Setters
    public int getCruiseId() {
        return cruiseId;
    }
    
    public void setCruiseId(int cruiseId) {
        this.cruiseId = cruiseId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getRoute() {
        return route;
    }
    
    public void setRoute(String route) {
        this.route = route;
    }
    
    public Date getDepartureDate() {
        return departureDate;
    }
    
    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }
    
    public Date getReturnDate() {
        return returnDate;
    }
    
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public double getBasePrice() {
        return basePrice;
    }
    
    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Cruise{" +
                "cruiseId=" + cruiseId +
                ", name='" + name + '\'' +
                ", route='" + route + '\'' +
                ", departureDate=" + departureDate +
                ", returnDate=" + returnDate +
                ", capacity=" + capacity +
                ", basePrice=" + basePrice +
                ", status='" + status + '\'' +
                '}';
    }
}
