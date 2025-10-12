#!/bin/bash

# Pathfinding Visualizer - Compile and Run Script
# This script compiles and runs the Pathfinding Visualizer

echo "Pathfinding Visualizer - Compilation and Execution Script"
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

echo "Compiling Pathfinding Visualizer..."

# Compile all Java files
javac *.java

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo ""
    echo "Starting Pathfinding Visualizer..."
    echo "=================================="
    echo ""
    echo "Algorithms Available:"
    echo "  🔍 A* Algorithm - Optimal pathfinding with heuristic"
    echo "  📍 Dijkstra's Algorithm - Guaranteed shortest path"
    echo ""
    echo "Features:"
    echo "  • Interactive grid - click and drag to create walls"
    echo "  • Real-time visualization - watch algorithms work"
    echo "  • Color-coded nodes - see exploration and path"
    echo "  • Statistics - nodes visited and path length"
    echo ""
    echo "How to Use:"
    echo "  1. Set start point (Green)"
    echo "  2. Set end point (Red)"
    echo "  3. Draw walls (Black)"
    echo "  4. Select algorithm (A* or Dijkstra)"
    echo "  5. Click Run to visualize pathfinding"
    echo ""
    
    # Check if Main class exists and has main method
    if [ ! -f "Main.class" ]; then
        echo "Warning: Main.class not found after compilation."
        echo ""
        echo "Note: The project contains PathfindingAlgorithm class but may need:"
        echo "  - A Main class with main() method"
        echo "  - Node class for grid representation"
        echo "  - PathfindingResult class for results"
        echo "  - GUI components for visualization"
        echo ""
        echo "Please ensure all required classes are implemented."
        exit 1
    fi
    
    # Run the application
    java Main
    
    if [ $? -eq 0 ]; then
        echo ""
        echo "Pathfinding Visualizer closed successfully."
    else
        echo ""
        echo "Error: Pathfinding Visualizer exited with an error."
    fi
else
    echo "Error: Compilation failed. Please check the error messages above."
    echo ""
    echo "Common issues:"
    echo "  - Missing required classes (Node, Main, PathfindingResult)"
    echo "  - Syntax errors in code"
    echo "  - Java version incompatibility"
    echo "  - Missing imports"
    exit 1
fi
