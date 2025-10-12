package models;

/**
 * Model class representing an Extra service
 */
public class Extra {
    private int extraId;
    private String extraName;
    private double extraPrice;
    
    // Default constructor
    public Extra() {}
    
    // Constructor with parameters
    public Extra(int extraId, String extraName, double extraPrice) {
        this.extraId = extraId;
        this.extraName = extraName;
        this.extraPrice = extraPrice;
    }
    
    // Getters and Setters
    public int getExtraId() {
        return extraId;
    }
    
    public void setExtraId(int extraId) {
        this.extraId = extraId;
    }
    
    public String getExtraName() {
        return extraName;
    }
    
    public void setExtraName(String extraName) {
        this.extraName = extraName;
    }
    
    public double getExtraPrice() {
        return extraPrice;
    }
    
    public void setExtraPrice(double extraPrice) {
        this.extraPrice = extraPrice;
    }
    
    @Override
    public String toString() {
        return "Extra{" +
                "extraId=" + extraId +
                ", extraName='" + extraName + '\'' +
                ", extraPrice=" + extraPrice +
                '}';
    }
}
