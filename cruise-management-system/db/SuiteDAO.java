package db;

import models.Suite;
import models.Extra;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Suite operations
 */
public class SuiteDAO {
    
    /**
     * Gets all suites
     */
    public static List<Suite> getAllSuites() {
        return DataStore.getAllSuites();
    }
    
    /**
     * Gets a suite by type
     */
    public static Suite getSuiteByType(String suiteType) {
        return DataStore.getSuiteByType(suiteType);
    }
    
    /**
     * Gets all extras
     */
    public static List<Extra> getAllExtras() {
        return DataStore.getAllExtras();
    }
}