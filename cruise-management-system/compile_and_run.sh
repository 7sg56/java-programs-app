#!/bin/bash

# Cruise Management System - Compile and Run Script
# This script compiles and runs the Cruise Management System

echo "Cruise Management System - Compilation and Execution Script"
echo "=========================================================="

# Check if Java is installed
if ! command -v javac &> /dev/null; then
    echo "Error: Java compiler (javac) not found. Please install JDK."
    exit 1
fi

if ! command -v java &> /dev/null; then
    echo "Error: Java runtime (java) not found. Please install JDK."
    exit 1
fi

echo "Java version:"
java -version
echo ""

echo "Compiling Cruise Management System..."

# Compile all Java files
javac -cp "." *.java db/*.java ui/*.java utils/*.java

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo ""
    echo "Starting Cruise Management System..."
    echo "===================================="
    echo ""
    echo "System Features:"
    echo "  🚢 Cruise Management - Add, edit, and manage cruises"
    echo "  🎫 Booking System - Complete booking workflow"
    echo "  👥 User Interfaces - Admin and Customer dashboards"
    echo "  📊 Reports - Occupancy and revenue tracking"
    echo "  🎫 Ticket Generation - Automatic PDF ticket creation"
    echo ""
    echo "Login Information:"
    echo "  Admin Password: admin123"
    echo "  Customer Access: No password required"
    echo ""
    echo "Getting Started:"
    echo "  1. Choose your role (Admin or Customer)"
    echo "  2. For Admin: Enter password 'admin123'"
    echo "  3. For Customer: Browse and book cruises"
    echo ""
    
    # Run the application
    java Main
    
    if [ $? -eq 0 ]; then
        echo ""
        echo "Cruise Management System closed successfully."
    else
        echo ""
        echo "Error: Cruise Management System exited with an error."
    fi
else
    echo "Error: Compilation failed. Please check the error messages above."
    echo ""
    echo "Common issues:"
    echo "  - Missing Java files in the project"
    echo "  - Incorrect package declarations"
    echo "  - Missing dependencies"
    echo "  - Java version compatibility issues"
    exit 1
fi
