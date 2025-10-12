# Algorithm Visualizer

A Java Swing application that provides real-time visualization of popular sorting algorithms. Watch how different algorithms sort data step-by-step with color-coded animations.

## Features

- **5 Sorting Algorithms**: Bubble Sort, Selection Sort, Insertion Sort, Merge Sort, and Quick Sort
- **Real-time Visualization**: See the sorting process in action with animated bars
- **Color-Coded States**:
  - 🔵 Steel Blue: Default/unsorted elements
  - 🟡 Gold: Elements being compared
  - 🔴 Crimson: Elements being swapped
  - 🟢 Green: Sorted elements
- **Adjustable Speed**: Control the animation speed with a slider (1-100)
- **Random Array Generation**: Generate new random arrays to sort
- **Array Size**: 100 elements with values ranging from 50 to 550

## Requirements

- Java Development Kit (JDK) 8 or higher
- Java Swing library (included in standard JDK)

## How to Run

### Using the Start Script (Recommended)

```bash
./compile_and_run.sh
```

### Manual Compilation and Execution

```bash
# Compile the program
javac Main.java

# Run the program
java Main
```

## How to Use

1. **Launch the Application**: Run the program using one of the methods above
2. **Select Algorithm**: Choose a sorting algorithm from the dropdown menu
3. **Generate Array** (Optional): Click "Generate Array" to create a new random array
4. **Adjust Speed** (Optional): Use the speed slider to control animation speed
   - Lower values = faster sorting
   - Higher values = slower, more detailed visualization
5. **Start Sorting**: Click "Start Sorting" to begin the visualization
6. **Watch the Process**: Observe as the algorithm sorts the array with color-coded animations

## Algorithm Descriptions

### Bubble Sort
- **Time Complexity**: O(n²)
- **Space Complexity**: O(1)
- Repeatedly swaps adjacent elements if they're in the wrong order

### Selection Sort
- **Time Complexity**: O(n²)
- **Space Complexity**: O(1)
- Finds the minimum element and places it at the beginning

### Insertion Sort
- **Time Complexity**: O(n²)
- **Space Complexity**: O(1)
- Builds the sorted array one item at a time

### Merge Sort
- **Time Complexity**: O(n log n)
- **Space Complexity**: O(n)
- Divides the array into halves, sorts them, and merges them back

### Quick Sort
- **Time Complexity**: O(n log n) average, O(n²) worst case
- **Space Complexity**: O(log n)
- Picks a pivot and partitions the array around it

## Application Window

- **Window Size**: 1000x700 pixels
- **Array Size**: 100 elements
- **Controls**: Located at the top of the window
- **Visualization Area**: Central panel showing animated bars
- **Status Bar**: Bottom panel showing current operation status

## Controls During Sorting

While sorting is in progress:
- Algorithm selector is disabled
- "Start Sorting" button is disabled
- "Generate Array" button is disabled
- Speed slider remains active for real-time adjustment

## Technical Details

- **Language**: Java
- **GUI Framework**: Java Swing
- **Threading**: Sorting algorithms run on separate threads to prevent UI freezing
- **Graphics**: Uses Java 2D Graphics with anti-aliasing for smooth rendering

## Project Structure

```
Algorithm-vizualizer/
├── Main.java              # Main application file containing:
│                          # - Main class (JFrame)
│                          # - VisualizationPanel class
│                          # - SortingAlgorithms class
├── compile_and_run.sh     # Build and run script
└── README.md              # This file
```

## Educational Value

This visualizer is perfect for:
- Learning how different sorting algorithms work
- Understanding algorithm efficiency differences
- Teaching computer science concepts
- Comparing algorithm performance visually

## Future Enhancements

Potential improvements could include:
- Additional algorithms (Heap Sort, Radix Sort, etc.)
- Adjustable array size
- Step-by-step mode with pause/resume
- Performance metrics (comparisons, swaps, time elapsed)
- Sound effects for comparisons and swaps
- Export visualization as video/GIF

## License

This is an educational project free to use and modify.

## Author

Created as part of a Java projects collection for learning and demonstration purposes.

