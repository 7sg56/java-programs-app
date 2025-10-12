#!/bin/bash

# Algorithm Visualizer - Compile and Run Script

echo "=================================="
echo "Algorithm Visualizer"
echo "=================================="
echo ""

# Clean up old class files
echo "Cleaning up old class files..."
rm -f *.class
echo "Done."
echo ""

# Compile the Java file
echo "Compiling Main.java..."
javac Main.java

# Check if compilation was successful
if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo ""
    echo "Starting Algorithm Visualizer..."
    echo "=================================="
    echo ""
    
    # Run the program
    java Main
    
    echo ""
    echo "=================================="
    echo "Application closed."
else
    echo "Compilation failed. Please check the error messages above."
    exit 1
fi

