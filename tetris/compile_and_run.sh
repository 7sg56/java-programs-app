#!/bin/bash

# Tetris Game - Compile and Run Script
# This script compiles and runs the Tetris Game

echo "Tetris Game - Compilation and Execution Script"
echo "=============================================="

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

echo "Compiling Tetris Game..."

# Compile all Java files
javac -cp "." *.java game/*.java ui/*.java input/*.java utils/*.java

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo ""
    echo "Starting Tetris Game..."
    echo "======================"
    echo ""
    echo "Game Controls:"
    echo "  ← →  Move left/right"
    echo "  ↓    Soft drop"
    echo "  ↑    Hard drop"
    echo "  Space Rotate piece"
    echo "  P    Pause/Resume"
    echo "  R    Restart game"
    echo "  ESC  Quit game"
    echo ""
    
    # Run the application
    java Main
    
    if [ $? -eq 0 ]; then
        echo ""
        echo "Tetris Game closed successfully."
    else
        echo ""
        echo "Error: Tetris Game exited with an error."
    fi
else
    echo "Error: Compilation failed. Please check the error messages above."
    exit 1
fi
