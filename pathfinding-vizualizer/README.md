# Pathfinding Visualizer

A comprehensive Java-based visualization tool featuring 6 different pathfinding algorithms, from lightning-fast heuristic search to deep exploration strategies.

## Features

### 🔍 Algorithms Implemented (Fastest → Slowest)
1. **Greedy Best-First Search**: Lightning-fast heuristic-only search (non-optimal)
2. **A* Algorithm**: Optimal pathfinding with heuristic (Manhattan distance)
3. **Dijkstra's Algorithm**: Guaranteed shortest path without heuristic
4. **BFS (Breadth-First Search)**: Level-by-level exploration, unweighted shortest path
5. **Bellman-Ford Algorithm**: Systematic edge relaxation, handles negative weights
6. **DFS (Depth-First Search)**: Deep exploration with backtracking (non-optimal)
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

### Greedy Best-First Search
- **Strategy**: Pure heuristic-based search - ignores actual path cost
- **Formula**: f(n) = h(n) only (Manhattan distance to goal)
- **Optimality**: NOT optimal - can find suboptimal paths
- **Efficiency**: Very fast, often faster than A* in practice
- **Time Complexity**: O((V + E) log V)
- **Best Use**: When speed matters more than optimality, open spaces
- **Weakness**: Can get "stuck" going wrong direction, ignores obstacles between current and goal

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

### Bellman-Ford Algorithm (SPFA Variant)
- **Strategy**: Queue-based edge relaxation - only processes nodes that were recently updated
- **Formula**: For each edge (u→v): if distance[u] + weight(u,v) < distance[v], then distance[v] = distance[u] + weight(u,v)
- **Algorithm Steps**:
  1. Initialize all distances to ∞ (except source = 0)
  2. Use a queue starting from the source node
  3. For each node, relax all its outgoing edges
  4. If a neighbor's distance improves, add it to the queue
  5. Continue until queue is empty
- **SPFA Optimization**: Shortest Path Faster Algorithm - only processes nodes with updated distances
- **Optimality**: Guaranteed shortest path, can handle negative weights and detect negative cycles
- **Time Complexity**: O(E) average case, O(V × E) worst case
- **Space Complexity**: O(V)
- **Visualization**: Shows natural radial propagation similar to Dijkstra
- **Note**: This queue-based variant explores more uniformly than the classic nested-loop version

### DFS (Depth-First Search)
- **Strategy**: Explore as far as possible along each branch before backtracking
- **Data Structure**: Stack (LIFO - Last In, First Out)
- **Optimality**: NOT optimal - finds A path, but often very long and winding
- **Efficiency**: Can be slow, may explore entire grid before finding goal
- **Time Complexity**: O(V + E) - may visit all nodes
- **Space Complexity**: O(V) - stack depth
- **Best Use**: Maze generation, finding ANY path quickly, memory-constrained environments
- **Weakness**: Path quality is poor, can take very indirect routes
- **Visualization**: Shows deep "tendrils" exploring before backtracking

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
1. **Select Algorithm**: Choose from 6 algorithms (ordered by speed)
2. **Adjust Speed**: Control visualization delay
3. **Start**: Click Run to begin visualization
4. **Reset**: Clear the grid to try again

### Understanding Results
- **Blue Nodes**: Algorithm explored these locations
- **Yellow Path**: The shortest path found
- **Statistics Panel**: Shows nodes visited and path length

## Algorithm Comparison

| Algorithm | Speed | Optimal Path? | Best Use Case | Visualization Pattern |
|-----------|-------|---------------|---------------|----------------------|
| **Greedy Best-First** | ⚡⚡⚡ Fastest | ❌ No | Quick paths, speed priority | Beeline toward goal |
| **A*** | ⚡⚡ Very Fast | ✅ Yes | Single target, balanced | Efficient radial |
| **Dijkstra** | ⚡ Fast | ✅ Yes | Multiple targets, weighted | Uniform radial |
| **BFS** | ⚡ Fast | ✅ Yes (unweighted) | Unweighted graphs | Level-by-level |
| **Bellman-Ford** | 🐌 Moderate | ✅ Yes | Negative weights | Radial waves |
| **DFS** | 🐌🐌 Slowest | ❌ No | Maze generation, any path | Deep tendrils |

### Quick Selection Guide

**Need the FASTEST result?**
→ Use **Greedy Best-First** (but path may not be optimal)

**Need OPTIMAL path quickly?**
→ Use **A*** (best balance of speed and optimality)

**Need GUARANTEED shortest path?**
→ Use **Dijkstra** or **BFS**

**Have negative edge weights?**
→ Use **Bellman-Ford**

**Just need ANY path?**
→ Use **DFS** (simplest, non-optimal)

**Want to COMPARE algorithms?**
→ Run different algorithms on the same maze!

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

### Time Complexity Comparison
| Algorithm | Time Complexity | Practical Speed |
|-----------|----------------|-----------------|
| Greedy Best-First | O((V + E) log V) | ⚡⚡⚡ Very Fast |
| A* | O((V + E) log V) | ⚡⚡ Fast |
| Dijkstra | O((V + E) log V) | ⚡ Moderate |
| BFS | O(V + E) | ⚡ Fast |
| Bellman-Ford | O(E) avg, O(V × E) worst | 🐌 Slow |
| DFS | O(V + E) | 🐌 Variable |

*V = vertices/nodes, E = edges/connections*

### Space Complexity
- **All algorithms**: O(V) for data structures (queue/stack/set)
- **Priority queue algorithms** (A*, Dijkstra, Greedy): Slightly higher constant factor

### Nodes Visited (Typical)
On a 30×30 grid with moderate obstacles:
- **Greedy Best-First**: ~50-150 nodes (beeline to goal)
- **A***: ~100-300 nodes (efficient exploration)
- **Dijkstra**: ~200-400 nodes (uniform expansion)
- **BFS**: ~200-400 nodes (level-by-level)
- **Bellman-Ford**: ~200-400 nodes (similar to BFS)
- **DFS**: ~200-800 nodes (can explore entire grid)

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
- [x] Greedy Best-First Search ✅
- [x] A* Algorithm ✅
- [x] Dijkstra's Algorithm ✅
- [x] Breadth-First Search (BFS) ✅
- [x] Bellman-Ford (SPFA) ✅
- [x] Depth-First Search (DFS) ✅
- [ ] Bidirectional Search
- [ ] Theta* (any-angle pathfinding)
- [ ] Jump Point Search

## Learning Resources

### Understanding Pathfinding
- **Nodes**: Positions on the grid
- **Edges**: Connections between adjacent nodes
- **Cost**: Distance/effort to move between nodes
- **Heuristic**: Estimated distance to goal

### Algorithm Visualization
Watch how different algorithms behave:
- **Greedy Best-First** makes a beeline toward the goal (fast but not optimal)
- **A*** balances cost and heuristic for efficient optimal paths
- **Dijkstra** explores uniformly in all directions
- **BFS** expands level-by-level like a wave
- **Bellman-Ford** shows wave-like relaxation iterations
- **DFS** creates deep "tendrils" before backtracking
- Compare optimal vs non-optimal algorithms
- See how obstacles affect different strategies

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
