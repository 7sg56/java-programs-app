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
     * Bellman-Ford algorithm implementation.
     * Can handle negative weights and detects negative cycles.
     * Relaxes ALL edges in the graph (V-1) times to find shortest paths.
     */
    public PathfindingResult bellmanFord(Node start, Node end) {
        // Initialize all nodes to infinite distance
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                grid[x][y].updateCosts(Double.MAX_VALUE, null);
            }
        }
        
        start.updateCosts(0, null);
        
        int nodesVisited = 0;
        
        // Relax ALL edges (V-1) times
        // V = number of walkable nodes in the grid
        for (int iteration = 0; iteration < gridSize * gridSize - 1; iteration++) {
            boolean relaxed = false;
            
            // Process all edges in the graph
            // For a grid, edges are from each node to its neighbors
            for (int x = 0; x < gridSize; x++) {
                for (int y = 0; y < gridSize; y++) {
                    Node current = grid[x][y];
                    
                    // Skip walls and unreachable nodes
                    if (!current.isWalkable() || current.getG() == Double.MAX_VALUE) {
                        continue;
                    }
                    
                    // Try to relax all outgoing edges from this node
                    for (int[] dir : DIRECTIONS) {
                        int newX = current.getX() + dir[0];
                        int newY = current.getY() + dir[1];
                        
                        if (!isValid(newX, newY)) continue;
                        
                        Node neighbor = grid[newX][newY];
                        
                        if (!neighbor.isWalkable()) continue;
                        
                        double newDistance = current.getG() + 1; // Edge weight is 1
                        
                        // Relax the edge if we found a shorter path
                        if (newDistance < neighbor.getG()) {
                            neighbor.updateCosts(newDistance, current);
                            
                            // Mark as visited and visualize on first relaxation
                            if (neighbor != start && neighbor != end && neighbor.getState() != Node.State.VISITED) {
                                neighbor.setState(Node.State.VISITED);
                                nodesVisited++;
                            }
                            relaxed = true;
                        }
                    }
                }
            }
            
            // Visualize after each complete iteration
            visualize();
            
            // Early termination: if no edge was relaxed, we're done
            if (!relaxed) break;
        }
        
        // Check if path to end exists
        if (end.getG() == Double.MAX_VALUE) {
            return new PathfindingResult(false, nodesVisited, 0);
        }
        
        int pathLength = reconstructPath(end);
        return new PathfindingResult(true, nodesVisited, pathLength);
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