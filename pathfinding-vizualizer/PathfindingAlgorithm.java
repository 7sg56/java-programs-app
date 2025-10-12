import javax.swing.*;
import java.util.*;

/**
 * Implements multiple pathfinding algorithms with visualization:
 * - A* (with heuristic)
 * - Dijkstra
 * - BFS (Breadth-First Search)
 * - Bellman-Ford
 */
public class PathfindingAlgorithm {
    private final Node[][] grid;
    private final int gridSize;
    private final JPanel panel;
    private final int delay;
    
    // Directions: up, right, down, left
    private static final int[][] DIRECTIONS = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
    
    public PathfindingAlgorithm(Node[][] grid, int gridSize, JPanel panel, int delay) {
        this.grid = grid;
        this.gridSize = gridSize;
        this.panel = panel;
        this.delay = delay;
    }
    
    /**
     * A* algorithm implementation with heuristic.
     */
    public PathfindingResult aStar(Node start, Node end) {
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Set<Node> closedSet = new HashSet<>();
        
        // Initialize start node
        start.updateCosts(0, null);
        start.calculateHeuristic(end);
        openSet.add(start);
        
        int nodesVisited = 0;
        
        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            
            // Check if we reached the end
            if (current == end) {
                int pathLength = reconstructPath(end);
                return new PathfindingResult(true, nodesVisited, pathLength);
            }
            
            closedSet.add(current);
            
            // Visualize visited node
            if (current != start && current != end) {
                current.setState(Node.State.VISITED);
                visualize();
            }
            nodesVisited++;
            
            // Explore neighbors
            for (int[] dir : DIRECTIONS) {
                int newX = current.getX() + dir[0];
                int newY = current.getY() + dir[1];
                
                if (!isValid(newX, newY)) continue;
                
                Node neighbor = grid[newX][newY];
                
                if (!neighbor.isWalkable() || closedSet.contains(neighbor)) continue;
                
                double tentativeG = current.getG() + 1; // Cost is 1 for each step
                
                if (tentativeG < neighbor.getG()) {
                    neighbor.calculateHeuristic(end);
                    neighbor.updateCosts(tentativeG, current);
                    
                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }
        
        // No path found
        return new PathfindingResult(false, nodesVisited, 0);
    }
    
    /**
     * Dijkstra's algorithm implementation (A* without heuristic).
     */
    public PathfindingResult dijkstra(Node start, Node end) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::getG));
        Set<Node> closedSet = new HashSet<>();
        
        // Initialize start node
        start.updateCosts(0, null);
        openSet.add(start);
        
        int nodesVisited = 0;
        
        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            
            // Check if we reached the end
            if (current == end) {
                int pathLength = reconstructPath(end);
                return new PathfindingResult(true, nodesVisited, pathLength);
            }
            
            if (closedSet.contains(current)) continue;
            closedSet.add(current);
            
            // Visualize visited node
            if (current != start && current != end) {
                current.setState(Node.State.VISITED);
                visualize();
            }
            nodesVisited++;
            
            // Explore neighbors
            for (int[] dir : DIRECTIONS) {
                int newX = current.getX() + dir[0];
                int newY = current.getY() + dir[1];
                
                if (!isValid(newX, newY)) continue;
                
                Node neighbor = grid[newX][newY];
                
                if (!neighbor.isWalkable() || closedSet.contains(neighbor)) continue;
                
                double tentativeG = current.getG() + 1;
                
                if (tentativeG < neighbor.getG()) {
                    neighbor.updateCosts(tentativeG, current);
                    openSet.add(neighbor);
                }
            }
        }
        
        // No path found
        return new PathfindingResult(false, nodesVisited, 0);
    }
    
    /**
     * Reconstructs and visualizes the final path from start to end.
     */
    private int reconstructPath(Node end) {
        int pathLength = 0;
        Node current = end.getParent(); // Start from parent of end (don't color end)
        
        while (current != null && current.getState() != Node.State.START) {
            current.setState(Node.State.PATH);
            visualize();
            current = current.getParent();
            pathLength++;
        }
        
        return pathLength;
    }
    
    /**
     * Checks if coordinates are within grid bounds.
     */
    private boolean isValid(int x, int y) {
        return x >= 0 && x < gridSize && y >= 0 && y < gridSize;
    }
    
    /**
     * BFS (Breadth-First Search) algorithm implementation.
     * Explores all neighbors at current depth before moving to next depth.
     */
    public PathfindingResult bfs(Node start, Node end) {
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();
        
        // Initialize start node
        start.updateCosts(0, null);
        queue.offer(start);
        visited.add(start);
        
        int nodesVisited = 0;
        
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            
            // Check if we reached the end
            if (current == end) {
                int pathLength = reconstructPath(end);
                return new PathfindingResult(true, nodesVisited, pathLength);
            }
            
            // Visualize visited node
            if (current != start && current != end) {
                current.setState(Node.State.VISITED);
                visualize();
            }
            nodesVisited++;
            
            // Explore neighbors
            for (int[] dir : DIRECTIONS) {
                int newX = current.getX() + dir[0];
                int newY = current.getY() + dir[1];
                
                if (!isValid(newX, newY)) continue;
                
                Node neighbor = grid[newX][newY];
                
                if (!neighbor.isWalkable() || visited.contains(neighbor)) continue;
                
                neighbor.updateCosts(current.getG() + 1, current);
                visited.add(neighbor);
                queue.offer(neighbor);
            }
        }
        
        // No path found
        return new PathfindingResult(false, nodesVisited, 0);
    }
    
    /**
     * Bellman-Ford Algorithm - SPFA (Shortest Path Faster Algorithm) Variant
     * 
     * This is a queue-based optimization of Bellman-Ford that:
     * 1. Only processes nodes that were updated in the previous iteration
     * 2. Explores outward naturally from the start (like Dijkstra/BFS)
     * 3. Still maintains Bellman-Ford's ability to handle negative weights
     * 
     * Time Complexity: O(V * E) worst case, but typically O(E) in practice
     * Space Complexity: O(V)
     * 
     * This creates a more natural radial propagation pattern similar to Dijkstra,
     * while still using the edge relaxation approach of Bellman-Ford.
     */
    public PathfindingResult bellmanFord(Node start, Node end) {
        // Step 1: Initialize distances to infinity
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                grid[x][y].updateCosts(Double.MAX_VALUE, null);
            }
        }
        
        // Distance to start node is 0
        start.updateCosts(0, null);
        
        // Queue for nodes that need to be processed
        Queue<Node> queue = new LinkedList<>();
        Set<Node> inQueue = new HashSet<>();
        Set<Node> visitedNodes = new HashSet<>();
        
        // Add start node to queue
        queue.offer(start);
        inQueue.add(start);
        
        // Track relaxation count to prevent infinite loops (negative cycle detection)
        Map<Node, Integer> relaxationCount = new HashMap<>();
        
        // Step 2: Process nodes using queue (SPFA approach)
        while (!queue.isEmpty()) {
            Node u = queue.poll();
            inQueue.remove(u);
            
            // Visualize current node being processed
            if (u != start && u != end && !visitedNodes.contains(u)) {
                u.setState(Node.State.VISITED);
                visitedNodes.add(u);
                visualize();
            }
            
            // Skip if unreachable
            if (u.getG() == Double.MAX_VALUE) {
                continue;
            }
            
            // Relax all edges from this node
            for (int[] dir : DIRECTIONS) {
                int nx = u.getX() + dir[0];
                int ny = u.getY() + dir[1];
                
                if (!isValid(nx, ny)) continue;
                
                Node v = grid[nx][ny];
                
                if (!v.isWalkable()) continue;
                
                // Relaxation: if distance[u] + weight(u,v) < distance[v]
                double weight = 1.0; // Edge weight in grid
                double newDistance = u.getG() + weight;
                
                if (newDistance < v.getG()) {
                    // Update distance and parent
                    v.updateCosts(newDistance, u);
                    
                    // Add to queue if not already in it
                    if (!inQueue.contains(v)) {
                        queue.offer(v);
                        inQueue.add(v);
                        
                        // Track relaxation count (for negative cycle detection in general graphs)
                        int count = relaxationCount.getOrDefault(v, 0) + 1;
                        relaxationCount.put(v, count);
                        
                        // Safety check: if a node is relaxed too many times, break
                        // (In our grid with positive weights, this shouldn't happen)
                        if (count > gridSize * gridSize) {
                            break;
                        }
                    }
                }
            }
        }
        
        // Step 3: Check if destination is reachable
        if (end.getG() == Double.MAX_VALUE) {
            return new PathfindingResult(false, visitedNodes.size(), 0);
        }
        
        // Reconstruct and return the shortest path
        int pathLength = reconstructPath(end);
        return new PathfindingResult(true, visitedNodes.size(), pathLength);
    }
    
    /**
     * Greedy Best-First Search Algorithm
     * 
     * Similar to A* but ONLY uses heuristic (h) to guide search, ignoring actual cost (g).
     * This makes it very fast but NOT guaranteed to find the optimal path.
     * 
     * Formula: f(n) = h(n) only
     * 
     * Time Complexity: O((V + E) log V) - same as A* but often faster in practice
     * Space Complexity: O(V)
     * 
     * Pros: Very fast, good for when speed matters more than optimality
     * Cons: Path may not be optimal, can get stuck going wrong direction
     */
    public PathfindingResult greedyBestFirst(Node start, Node end) {
        // Priority queue based on heuristic only
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::getH));
        Set<Node> closedSet = new HashSet<>();
        
        // Initialize start node
        start.updateCosts(0, null);
        start.calculateHeuristic(end);
        openSet.add(start);
        
        int nodesVisited = 0;
        
        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            
            // Check if we reached the end
            if (current == end) {
                int pathLength = reconstructPath(end);
                return new PathfindingResult(true, nodesVisited, pathLength);
            }
            
            closedSet.add(current);
            
            // Visualize visited node
            if (current != start && current != end) {
                current.setState(Node.State.VISITED);
                visualize();
            }
            nodesVisited++;
            
            // Explore neighbors
            for (int[] dir : DIRECTIONS) {
                int newX = current.getX() + dir[0];
                int newY = current.getY() + dir[1];
                
                if (!isValid(newX, newY)) continue;
                
                Node neighbor = grid[newX][newY];
                
                if (!neighbor.isWalkable() || closedSet.contains(neighbor)) continue;
                
                // Only calculate heuristic, ignore actual cost
                if (!openSet.contains(neighbor)) {
                    neighbor.calculateHeuristic(end);
                    neighbor.updateCosts(current.getG() + 1, current); // Track cost for path reconstruction
                    openSet.add(neighbor);
                }
            }
        }
        
        // No path found
        return new PathfindingResult(false, nodesVisited, 0);
    }
    
    /**
     * Depth-First Search (DFS) Algorithm
     * 
     * Explores as far as possible along each branch before backtracking.
     * Uses a stack (LIFO) instead of a queue (FIFO like BFS).
     * 
     * NOT guaranteed to find the shortest path!
     * Will find A path if one exists, but it may be very long and winding.
     * 
     * Time Complexity: O(V + E) - can visit all nodes
     * Space Complexity: O(V) - for the stack
     * 
     * Pros: Memory efficient, finds a path quickly (any path)
     * Cons: Path is usually NOT optimal, can be very long
     */
    public PathfindingResult dfs(Node start, Node end) {
        Stack<Node> stack = new Stack<>();
        Set<Node> visited = new HashSet<>();
        
        // Initialize start node
        start.updateCosts(0, null);
        stack.push(start);
        visited.add(start);
        
        int nodesVisited = 0;
        
        while (!stack.isEmpty()) {
            Node current = stack.pop();
            
            // Check if we reached the end
            if (current == end) {
                int pathLength = reconstructPath(end);
                return new PathfindingResult(true, nodesVisited, pathLength);
            }
            
            // Visualize visited node
            if (current != start && current != end) {
                current.setState(Node.State.VISITED);
                visualize();
            }
            nodesVisited++;
            
            // Explore neighbors (in reverse order for consistent behavior)
            for (int i = DIRECTIONS.length - 1; i >= 0; i--) {
                int[] dir = DIRECTIONS[i];
                int newX = current.getX() + dir[0];
                int newY = current.getY() + dir[1];
                
                if (!isValid(newX, newY)) continue;
                
                Node neighbor = grid[newX][newY];
                
                if (!neighbor.isWalkable() || visited.contains(neighbor)) continue;
                
                neighbor.updateCosts(current.getG() + 1, current);
                visited.add(neighbor);
                stack.push(neighbor);
            }
        }
        
        // No path found
        return new PathfindingResult(false, nodesVisited, 0);
    }
    
    /**
     * Updates the panel display and adds delay for visualization.
     */
    private void visualize() {
        SwingUtilities.invokeLater(() -> panel.repaint());
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}