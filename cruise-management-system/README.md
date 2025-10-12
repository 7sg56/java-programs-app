# Cruise Management System

A comprehensive Java-based cruise management system with both admin and customer interfaces for managing cruise bookings, reservations, and customer services.

## Features

### 🚢 Core Functionality
- **Cruise Management**: Add, update, and delete cruise information
- **Booking System**: Complete booking workflow with passenger details
- **Suite Management**: Different suite types with pricing multipliers
- **Extra Services**: Additional services with individual pricing
- **Real-time Pricing**: Dynamic price calculation based on selections
- **Occupancy Tracking**: Automatic capacity management and status updates

### 👥 User Interfaces
- **Admin Dashboard**: Complete cruise and booking management
- **Customer Interface**: User-friendly booking and reservation system
- **Login System**: Role-based access control
- **Responsive UI**: Modern Swing-based interface with intuitive design

### 📊 Management Features
- **Booking Reports**: View and manage all bookings
- **Occupancy Reports**: Track cruise capacity and availability
- **Revenue Tracking**: Monitor booking revenue and pricing
- **PDF Ticket Generation**: Automatic ticket download and auto-open on booking
- **Data Persistence**: SQLite database for reliable data storage

## Technology Stack

- **Java**: Core application language
- **Swing**: GUI framework for desktop interface
- **SQLite**: Embedded database for data persistence
- **JDBC**: Database connectivity and operations
- **No external dependencies** - Pure Java implementation

## Project Structure

```
cruise-management-system/
├── Main.java                    # Application entry point
├── db/
│   └── CruiseDAO.java          # Database operations for cruises
├── ui/
│   ├── LoginFrame.java         # Login and role selection
│   └── CustomerBookingUI.java # Customer booking interface
├── utils/
│   └── PDFGenerator.java      # Ticket generation utility
├── compile_and_run.sh          # Easy compilation and execution script
└── README.md                   # This file
```

## Database Schema

The system uses the following main entities:

### Cruises Table
- `cruise_id` (Primary Key)
- `name` - Cruise name
- `route` - Cruise route description
- `departure_date` - Departure date
- `return_date` - Return date
- `capacity` - Maximum passenger capacity
- `base_price` - Base price per passenger
- `status` - OPEN/FULL status

### Bookings Table
- `booking_id` (Primary Key)
- `cruise_id` (Foreign Key)
- `passenger_name` - Customer name
- `suite_type` - Selected suite type
- `num_passengers` - Number of passengers
- `extras_selected` - Additional services
- `total_price` - Final booking price
- `booking_date` - Booking timestamp

### Suites Table
- `suite_id` (Primary Key)
- `suite_type` - Suite name
- `price_multiplier` - Price multiplier factor

### Extras Table
- `extra_id` (Primary Key)
- `extra_name` - Service name
- `extra_price` - Service price

## How to Run

### Option 1: Using the Script (Recommended)
```bash
cd cruise-management-system
./compile_and_run.sh
```

### Option 2: Manual Compilation
```bash
# Compile all Java files
javac -cp "." *.java db/*.java ui/*.java utils/*.java

# Run the application
java Main
```

### Option 3: Using an IDE
1. Import the project into IntelliJ IDEA or Eclipse
2. Run `Main.java`

## User Guide

### Admin Access
1. **Login**: Use password `admin123` to access admin features
2. **Cruise Management**: Add, edit, and delete cruises
3. **Booking Management**: View and manage all bookings
4. **Reports**: Generate occupancy and revenue reports

### Customer Features
1. **Browse Cruises**: View available cruises with real-time availability
2. **Book Cruise**: Complete booking form with passenger details
3. **Suite Selection**: Choose from different suite types
4. **Extra Services**: Add optional services to booking
5. **Price Calculation**: Real-time price updates
6. **Ticket Generation**: Automatic PDF ticket creation
7. **Booking Management**: View and cancel existing bookings

## Booking Process

### For Customers
1. **Select Cruise**: Choose from available cruises
2. **Enter Details**: Passenger name and count
3. **Choose Suite**: Select suite type (affects pricing)
4. **Add Extras**: Optional additional services
5. **Review Price**: Dynamic price calculation
6. **Confirm Booking**: Complete the reservation
7. **Receive Ticket**: Automatic PDF ticket generation

### For Administrators
1. **Add Cruises**: Create new cruise offerings
2. **Manage Bookings**: View and process bookings
3. **Update Status**: Monitor cruise capacity
4. **Generate Reports**: Track occupancy and revenue

## Key Features in Detail

### Dynamic Pricing System
- **Base Price**: Set per cruise
- **Suite Multipliers**: Different suite types affect pricing
- **Extra Services**: Additional charges for services
- **Real-time Calculation**: Instant price updates

### Capacity Management
- **Automatic Tracking**: Real-time occupancy monitoring
- **Status Updates**: Automatic OPEN/FULL status changes
- **Availability Display**: Live seat availability

### User Experience
- **Intuitive Interface**: Clean, modern design
- **Responsive Controls**: Smooth user interactions
- **Error Handling**: Comprehensive validation and error messages
- **Visual Feedback**: Clear status indicators and confirmations

## Database Operations

### Cruise Management
- **CRUD Operations**: Create, Read, Update, Delete cruises
- **Status Management**: Automatic status updates based on occupancy
- **Availability Queries**: Filter available cruises

### Booking Management
- **Booking Creation**: Complete booking workflow
- **Occupancy Tracking**: Real-time capacity monitoring
- **Cancellation**: Booking cancellation with capacity updates

### Data Integrity
- **Foreign Key Constraints**: Maintain referential integrity
- **Transaction Management**: Ensure data consistency
- **Error Handling**: Robust error management

## Requirements

- **Java JDK 8 or higher**
- **No external dependencies**
- **Swing support** (included with Java)
- **SQLite support** (included with Java 8+)

## Troubleshooting

### Common Issues
1. **Database Connection**: Ensure SQLite is properly initialized
2. **Java Version**: Use JDK 8 or higher
3. **File Permissions**: Ensure write permissions for database and PDF files
4. **Memory Issues**: Close other applications if experiencing slowdowns

### Performance Tips
- **Regular Database Maintenance**: Clean up old data periodically
- **Memory Management**: Restart application if memory usage is high
- **File System**: Ensure adequate disk space for database and PDF files

## Security Features

### Access Control
- **Role-based Access**: Separate admin and customer interfaces
- **Password Protection**: Admin access requires password
- **Data Validation**: Input validation and sanitization

### Data Protection
- **SQL Injection Prevention**: Prepared statements for all queries
- **Input Validation**: Comprehensive data validation
- **Error Handling**: Secure error messages

## Future Enhancements

### Planned Features
- **User Authentication**: Individual user accounts
- **Payment Integration**: Online payment processing
- **Email Notifications**: Automated booking confirmations
- **Advanced Reporting**: Detailed analytics and reports
- **Mobile Interface**: Mobile-friendly booking system
- **Multi-language Support**: Internationalization
- **API Integration**: Third-party service integrations

### Technical Improvements
- **Database Optimization**: Performance improvements
- **Caching System**: Faster data access
- **Backup System**: Automated data backup
- **Logging System**: Comprehensive audit trail

## Development Notes

### Code Architecture
- **MVC Pattern**: Separation of concerns
- **DAO Pattern**: Data access abstraction
- **Service Layer**: Business logic separation
- **Utility Classes**: Reusable components

### Design Principles
- **Single Responsibility**: Each class has one purpose
- **Open/Closed**: Extensible without modification
- **Dependency Injection**: Loose coupling
- **Error Handling**: Comprehensive exception management

## License

This project is created for educational and demonstration purposes.

## Credits

- **Java Implementation**: Custom implementation using Java Swing
- **Database Design**: SQLite-based data persistence
- **UI Design**: Modern Swing interface
- **Educational Purpose**: Created for learning Java application development

## Support

For technical support or questions about the system:
1. Check the troubleshooting section above
2. Review the code comments for implementation details
3. Ensure all requirements are met
4. Verify database initialization is successful
