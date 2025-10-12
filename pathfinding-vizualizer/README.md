# Pathfinding Visualizer

A Java-based visualization tool for pathfinding algorithms including A* and Dijkstra's algorithm.

## Features

### 🔍 Algorithms Implemented
- **A* Algorithm**: Optimal pathfinding with heuristic (Manhattan distance)
- **Dijkstra's Algorithm**: Guaranteed shortest path without heuristic
- **BFS (Breadth-First Search)**: Level-by-level exploration, unweighted shortest path
- **Bellman-Ford Algorithm**: Systematic edge relaxation, handles negative weights
- **Visual Comparison**: See how each algorithm explores the grid differently

### 🎨 Visual Features
- **Interactive Grid**: Click and drag to place walls, start, and end points
- **Real-time Visualization**: Watch algorithms explore nodes step by step
- **Color-Coded States**:
  - 🟩 **Green**: Start node
  - 🟥 **Red**: End node
  - ⬛ **Black**: Walls/obstacles
  - 🟦 **Blue**: Visited nodes (explored by algorithm)
  - 🟨 **Yellow**: Final path from start to end
  - ⬜ **White**: Unvisited nodes

### 📊 Statistics
- **Nodes Visited**: Total nodes explored by the algorithm
- **Path Length**: Length of the shortest path found
- **Success/Failure**: Whether a path exists

## Technology Stack

- **Java**: Core application language
- **Swing**: GUI framework for desktop interface
- **Priority Queue**: Efficient node exploration
- **Custom Node System**: Grid-based pathfinding representation

## Project Structure

```
pathfinding-vizualizer/
├── Main.java                   # Contains PathfindingAlgorithm class
├── compile_and_run.sh         # Compilation and execution script
└── README.md                   # This file
```

## Algorithm Details

### A* Algorithm
- **Strategy**: Best-first search with heuristic
- **Formula**: f(n) = g(n) + h(n)
  - g(n) = cost from start to node n
  - h(n) = estimated cost from n to goal (Manhattan distance)
- **Optimality**: Finds optimal path if heuristic is admissible
- **Efficiency**: Generally faster than Dijkstra for single target
- **Time Complexity**: O((V + E) log V)

### Dijkstra's Algorithm
- **Strategy**: Uniform-cost search
- **Formula**: f(n) = g(n) only
- **Optimality**: Always finds shortest path
- **Efficiency**: Explores more nodes than A* but guaranteed optimal
- **Time Complexity**: O((V + E) log V)

### BFS (Breadth-First Search)
- **Strategy**: Level-by-level exploration using a queue
- **Formula**: Explores all neighbors at current depth first
- **Optimality**: Finds shortest path in unweighted graphs
- **Efficiency**: Fast for unweighted grids, explores uniformly
- **Time Complexity**: O(V + E)

### Bellman-Ford Algorithm
- **Strategy**: Dynamic programming with edge relaxation
- **Formula**: Relax all edges V-1 times
- **Optimality**: Finds shortest path, can handle negative weights
- **Efficiency**: Slower but more versatile than Dijkstra
- **Time Complexity**: O(V * E)

### Manhattan Distance Heuristic
```
h(node, goal) = |node.x - goal.x| + |node.y - goal.y|
```

## Node States

The grid uses different states to visualize algorithm progress:

| State | Color | Description |
|-------|-------|-------------|
| EMPTY | White | Unvisited, walkable node |
| WALL | Black | Obstacle, cannot pass through |
| START | Green | Starting point for pathfinding |
| END | Red | Goal/target point |
| VISITED | Blue | Node explored by algorithm |
| PATH | Yellow | Part of final shortest path |

## How to Run

### Option 1: Using the Script (Recommended)
```bash
cd pathfinding-vizualizer
./compile_and_run.sh
```

### Option 2: Manual Compilation
```bash
# Compile
javac Main.java

# Run (requires Main class with main method)
java Main
```

### Option 3: Using an IDE
1. Import the project into IntelliJ IDEA or Eclipse
2. Run `Main.java`

## Usage Guide

### Setting Up the Grid
1. **Place Start Point**: Default position (top-left, Green)
2. **Place End Point**: Default position (bottom-right, Red)
3. **Draw Walls**: Click and drag to create obstacles (Black)
4. **Clear Wall**: Click wall again to remove it
5. **Random Maze**: Click "🎲 Random Maze" to generate random obstacles

### Running Algorithms
1. **Select Algorithm**: Choose between A* or Dijkstra
2. **Adjust Speed**: Control visualization delay
3. **Start**: Click Run to begin visualization
4. **Reset**: Clear the grid to try again

### Understanding Results
- **Blue Nodes**: Algorithm explored these locations
- **Yellow Path**: The shortest path found
- **Statistics Panel**: Shows nodes visited and path length

## Algorithm Comparison

### When to Use A*
- ✅ Single target pathfinding
- ✅ Need fast results
- ✅ Have good heuristic
- ✅ Open spaces with few obstacles

### When to Use Dijkstra
- ✅ Multiple targets
- ✅ Need guaranteed shortest path
- ✅ Complex weighted graphs
- ✅ No suitable heuristic available

## Code Components

### PathfindingAlgorithm Class
**Main Features:**
- Grid management
- Algorithm implementations
- Visualization control
- Path reconstruction

**Key Methods:**
- `aStar(Node start, Node end)` - A* pathfinding
- `dijkstra(Node start, Node end)` - Dijkstra's pathfinding
- `reconstructPath(Node end)` - Builds final path
- `visualize()` - Updates display

### Direction Vectors
```java
// Up, Right, Down, Left
{{0, -1}, {1, 0}, {0, 1}, {-1, 0}}
```

### PathfindingResult
Stores algorithm results:
- `success` - Whether path was found
- `nodesVisited` - Total nodes explored
- `pathLength` - Length of shortest path

## Performance Characteristics

### Time Complexity
- **A***: O((V + E) log V) with good heuristic
- **Dijkstra**: O((V + E) log V)
- V = vertices/nodes, E = edges/connections

### Space Complexity
- **Both**: O(V) for priority queue and visited set

### Practical Performance
- **A*** typically visits fewer nodes
- **Dijkstra** explores uniformly in all directions
- **Grid size** affects performance linearly

## Customization

### Adjusting Grid Size
```java
int gridSize = 30; // Change to desired size
```

### Modifying Visualization Speed
```java
int delay = 50; // Milliseconds between steps
// Lower = faster, Higher = slower
```

### Adding Diagonal Movement
Add diagonal directions:
```java
// Add these to DIRECTIONS array
{1, -1}, {1, 1}, {-1, 1}, {-1, -1}
```

## Requirements

- **Java JDK 8 or higher**
- **Swing support** (included with Java)
- **No external dependencies**

## Troubleshooting

### Common Issues

1. **Java not found**
   - Install JDK and ensure it's in PATH
   - Verify: `java -version`

2. **Compilation errors**
   - Check Java version compatibility
   - Ensure all required classes are present

3. **Visualization not working**
   - Ensure Swing is supported on your system
   - Check graphics drivers

4. **Slow performance**
   - Reduce grid size
   - Increase delay value
   - Close other applications

## Planned Enhancements

### Features
- [ ] Bidirectional A* (search from both ends)
- [ ] Jump Point Search optimization
- [ ] Weighted grid (different terrain costs)
- [ ] Multiple pathfinding targets
- [ ] Save/load maze patterns
- [x] Random maze generation ✅
- [x] Animation speed control ✅
- [ ] Step-by-step mode

### UI Improvements
- [ ] Better controls panel
- [ ] Algorithm comparison mode
- [ ] Statistics graphs
- [ ] Path smoothing
- [ ] Grid patterns (mazes, spirals)

### Algorithms
- [x] Breadth-First Search (BFS) ✅
- [x] Bellman-Ford ✅
- [ ] Depth-First Search (DFS)
- [ ] Greedy Best-First Search
- [ ] Theta* (any-angle pathfinding)

## Learning Resources

### Understanding Pathfinding
- **Nodes**: Positions on the grid
- **Edges**: Connections between adjacent nodes
- **Cost**: Distance/effort to move between nodes
- **Heuristic**: Estimated distance to goal

### Algorithm Visualization
Watch how:
- A* uses heuristic to guide search
- Dijkstra explores uniformly
- Both guarantee shortest path
- Obstacles affect exploration

### Key Concepts
- **Open Set**: Nodes to be evaluated
- **Closed Set**: Already evaluated nodes
- **Parent Tracking**: Reconstruct path
- **Priority Queue**: Process nodes by cost

## Contributing

To add new features:
1. Implement new algorithm in PathfindingAlgorithm class
2. Add UI controls if needed
3. Update visualization colors/states
4. Test with various grid configurations

## Performance Tips

### For Faster Visualization
- Increase delay value
- Reduce grid size
- Clear unnecessary walls

### For Better Understanding
- Decrease delay to see details
- Try different maze patterns
- Compare A* vs Dijkstra side-by-side

## License

This project is created for educational and demonstration purposes.

## Credits

- **A* Algorithm**: Hart, Nilsson, and Raphael (1968)
- **Dijkstra's Algorithm**: Edsger W. Dijkstra (1959)
- **Java Implementation**: Custom visualization implementation
- **Educational Purpose**: Created for learning pathfinding algorithms

## Notes

**Current State**: The project currently contains the `PathfindingAlgorithm` class with core algorithm implementations. Additional classes may be needed for:
- Main GUI window
- Node class implementation
- PathfindingResult class
- Grid management and interaction

**To complete the project**, ensure you have:
1. Main class with GUI setup
2. Node class with state management
3. Grid interaction handlers
4. Control panel for algorithm selection
