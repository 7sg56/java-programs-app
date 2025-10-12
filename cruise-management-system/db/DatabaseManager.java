package db;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Database Manager for handling database connections and initialization
 */
public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:cruise_management.db";
    private static Connection connection;
    
    /**
     * Initialize the database and create tables if they don't exist
     */
    public static void initialize() {
        try {
            // For now, we'll use a simple file-based approach
            // In a real application, you would use SQLite JDBC driver
            System.out.println("Database initialization skipped - using in-memory data");
            System.out.println("Note: For full functionality, SQLite JDBC driver is required");
            
        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Get database connection
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
        }
        return connection;
    }
    
    /**
     * Close database connection
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Create all necessary tables
     */
    private static void createTables() throws SQLException {
        // Create CRUISES table
        String createCruisesTable = """
            CREATE TABLE IF NOT EXISTS CRUISES (
                cruise_id INTEGER PRIMARY KEY AUTOINCREMENT,
                name VARCHAR(100) NOT NULL,
                route VARCHAR(200) NOT NULL,
                departure_date DATE NOT NULL,
                return_date DATE NOT NULL,
                capacity INTEGER NOT NULL,
                base_price DECIMAL(10,2) NOT NULL,
                status VARCHAR(20) DEFAULT 'OPEN'
            )
        """;
        
        // Create SUITES table
        String createSuitesTable = """
            CREATE TABLE IF NOT EXISTS SUITES (
                suite_id INTEGER PRIMARY KEY AUTOINCREMENT,
                suite_type VARCHAR(50) NOT NULL,
                price_multiplier DECIMAL(3,2) NOT NULL
            )
        """;
        
        // Create EXTRAS table
        String createExtrasTable = """
            CREATE TABLE IF NOT EXISTS EXTRAS (
                extra_id INTEGER PRIMARY KEY AUTOINCREMENT,
                extra_name VARCHAR(100) NOT NULL,
                extra_price DECIMAL(10,2) NOT NULL
            )
        """;
        
        // Create BOOKINGS table
        String createBookingsTable = """
            CREATE TABLE IF NOT EXISTS BOOKINGS (
                booking_id INTEGER PRIMARY KEY AUTOINCREMENT,
                cruise_id INTEGER NOT NULL,
                passenger_name VARCHAR(100) NOT NULL,
                suite_type VARCHAR(50) NOT NULL,
                num_passengers INTEGER NOT NULL,
                extras_selected TEXT,
                total_price DECIMAL(10,2) NOT NULL,
                booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (cruise_id) REFERENCES CRUISES(cruise_id)
            )
        """;
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createCruisesTable);
            stmt.execute(createSuitesTable);
            stmt.execute(createExtrasTable);
            stmt.execute(createBookingsTable);
        }
    }
    
    /**
     * Insert sample data into the database
     */
    private static void insertSampleData() throws SQLException {
        // Check if data already exists
        String checkCruises = "SELECT COUNT(*) FROM CRUISES";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(checkCruises)) {
            
            if (rs.next() && rs.getInt(1) > 0) {
                return; // Data already exists
            }
        }
        
        // Insert sample cruises
        String insertCruises = """
            INSERT INTO CRUISES (name, route, departure_date, return_date, capacity, base_price, status) VALUES
            ('Caribbean Paradise', 'Miami - Bahamas - Miami', ?, ?, 200, 1200.00, 'OPEN'),
            ('Mediterranean Explorer', 'Barcelona - Rome - Athens - Barcelona', ?, ?, 150, 2500.00, 'OPEN'),
            ('Alaskan Adventure', 'Seattle - Juneau - Ketchikan - Seattle', ?, ?, 100, 1800.00, 'OPEN'),
            ('Tropical Escape', 'Fort Lauderdale - Cozumel - Grand Cayman - Fort Lauderdale', ?, ?, 180, 1400.00, 'OPEN')
        """;
        
        try (PreparedStatement pstmt = connection.prepareStatement(insertCruises)) {
            LocalDate today = LocalDate.now();
            
            // Caribbean Paradise - 30 days from now
            pstmt.setDate(1, Date.valueOf(today.plusDays(30)));
            pstmt.setDate(2, Date.valueOf(today.plusDays(37)));
            
            // Mediterranean Explorer - 45 days from now
            pstmt.setDate(3, Date.valueOf(today.plusDays(45)));
            pstmt.setDate(4, Date.valueOf(today.plusDays(52)));
            
            // Alaskan Adventure - 60 days from now
            pstmt.setDate(5, Date.valueOf(today.plusDays(60)));
            pstmt.setDate(6, Date.valueOf(today.plusDays(67)));
            
            // Tropical Escape - 75 days from now
            pstmt.setDate(7, Date.valueOf(today.plusDays(75)));
            pstmt.setDate(8, Date.valueOf(today.plusDays(82)));
            
            pstmt.executeUpdate();
        }
        
        // Insert sample suites
        String insertSuites = """
            INSERT INTO SUITES (suite_type, price_multiplier) VALUES
            ('Interior', 1.0),
            ('Ocean View', 1.3),
            ('Balcony', 1.6),
            ('Suite', 2.0),
            ('Penthouse', 3.0)
        """;
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(insertSuites);
        }
        
        // Insert sample extras
        String insertExtras = """
            INSERT INTO EXTRAS (extra_name, extra_price) VALUES
            ('WiFi Package', 50.00),
            ('Spa Package', 200.00),
            ('Premium Dining', 150.00),
            ('Shore Excursions', 300.00),
            ('Photography Package', 100.00),
            ('Beverage Package', 80.00)
        """;
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(insertExtras);
        }
        
        System.out.println("Sample data inserted successfully!");
    }
}
