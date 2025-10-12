package models;

/**
 * Model class representing a Suite
 */
public class Suite {
    private int suiteId;
    private String suiteType;
    private double priceMultiplier;
    
    // Default constructor
    public Suite() {}
    
    // Constructor with parameters
    public Suite(int suiteId, String suiteType, double priceMultiplier) {
        this.suiteId = suiteId;
        this.suiteType = suiteType;
        this.priceMultiplier = priceMultiplier;
    }
    
    // Getters and Setters
    public int getSuiteId() {
        return suiteId;
    }
    
    public void setSuiteId(int suiteId) {
        this.suiteId = suiteId;
    }
    
    public String getSuiteType() {
        return suiteType;
    }
    
    public void setSuiteType(String suiteType) {
        this.suiteType = suiteType;
    }
    
    public double getPriceMultiplier() {
        return priceMultiplier;
    }
    
    public void setPriceMultiplier(double priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }
    
    @Override
    public String toString() {
        return "Suite{" +
                "suiteId=" + suiteId +
                ", suiteType='" + suiteType + '\'' +
                ", priceMultiplier=" + priceMultiplier +
                '}';
    }
}
