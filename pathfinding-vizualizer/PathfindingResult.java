/**
 * Stores the results of a pathfinding algorithm execution
 */
public class PathfindingResult {
    private final boolean success;
    private final int nodesVisited;
    private final int pathLength;
    
    public PathfindingResult(boolean success, int nodesVisited, int pathLength) {
        this.success = success;
        this.nodesVisited = nodesVisited;
        this.pathLength = pathLength;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public int getNodesVisited() {
        return nodesVisited;
    }
    
    public int getPathLength() {
        return pathLength;
    }
    
    @Override
    public String toString() {
        if (success) {
            return String.format("Path found! Nodes visited: %d, Path length: %d", 
                               nodesVisited, pathLength);
        } else {
            return String.format("No path found. Nodes visited: %d", nodesVisited);
        }
    }
}
