package db;

import models.Cruise;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Cruise operations
 */
public class CruiseDAO {
    
    /**
     * Adds a new cruise to the database
     */
    public static boolean addCruise(Cruise cruise) {
        return DataStore.addCruise(cruise);
    }
    
    /**
     * Updates an existing cruise
     */
    public static boolean updateCruise(Cruise cruise) {
        return DataStore.updateCruise(cruise);
    }
    
    /**
     * Deletes a cruise
     */
    public static boolean deleteCruise(int cruiseId) {
        return DataStore.deleteCruise(cruiseId);
    }
    
    /**
     * Gets all cruises
     */
    public static List<Cruise> getAllCruises() {
        return DataStore.getAllCruises();
    }
    
    /**
     * Gets available cruises (status = OPEN)
     */
    public static List<Cruise> getAvailableCruises() {
        return DataStore.getAvailableCruises();
    }
    
    /**
     * Gets a cruise by ID
     */
    public static Cruise getCruiseById(int cruiseId) {
        return DataStore.getCruiseById(cruiseId);
    }
    
    /**
     * Gets current occupancy for a cruise
     */
    public static int getCruiseOccupancy(int cruiseId) {
        return DataStore.getCruiseOccupancy(cruiseId);
    }
    
    /**
     * Updates cruise status based on occupancy
     */
    public static void updateCruiseStatus(int cruiseId) {
        DataStore.updateCruiseStatus(cruiseId);
    }
}