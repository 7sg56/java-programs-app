import java.awt.*;

/**
 * Represents a node in the pathfinding grid
 */
public class Node implements Comparable<Node> {
    
    public enum State {
        EMPTY,      // Unvisited, walkable
        WALL,       // Obstacle
        START,      // Starting point
        END,        // Goal/target point
        VISITED,    // Explored by algorithm
        PATH        // Part of final path
    }
    
    private final int x;
    private final int y;
    private State state;
    private Node parent;
    private double g;  // Cost from start to this node
    private double h;  // Heuristic cost to goal
    private double f;  // Total cost (g + h)
    
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.state = State.EMPTY;
        this.parent = null;
        this.g = Double.MAX_VALUE;
        this.h = 0;
        this.f = Double.MAX_VALUE;
    }
    
    /**
     * Updates the costs and parent of this node
     */
    public void updateCosts(double g, Node parent) {
        this.g = g;
        this.parent = parent;
        this.f = g + h;
    }
    
    /**
     * Calculates Manhattan distance heuristic to goal
     */
    public void calculateHeuristic(Node goal) {
        this.h = Math.abs(this.x - goal.x) + Math.abs(this.y - goal.y);
        this.f = this.g + this.h;
    }
    
    /**
     * Resets the node for a new pathfinding run
     */
    public void reset() {
        if (state != State.START && state != State.END && state != State.WALL) {
            state = State.EMPTY;
        }
        parent = null;
        g = Double.MAX_VALUE;
        h = 0;
        f = Double.MAX_VALUE;
    }
    
    /**
     * Checks if the node is walkable
     */
    public boolean isWalkable() {
        return state != State.WALL;
    }
    
    /**
     * Gets the color for rendering based on state
     */
    public Color getColor() {
        return switch (state) {
            case EMPTY -> Color.WHITE;
            case WALL -> Color.BLACK;
            case START -> Color.GREEN;
            case END -> Color.RED;
            case VISITED -> new Color(135, 206, 250); // Light blue
            case PATH -> Color.YELLOW;
        };
    }
    
    // Getters and Setters
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public State getState() {
        return state;
    }
    
    public void setState(State state) {
        this.state = state;
    }
    
    public Node getParent() {
        return parent;
    }
    
    public void setParent(Node parent) {
        this.parent = parent;
    }
    
    public double getG() {
        return g;
    }
    
    public double getH() {
        return h;
    }
    
    public double getF() {
        return f;
    }
    
    @Override
    public int compareTo(Node other) {
        // Compare by f value for priority queue (A*)
        return Double.compare(this.f, other.f);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Node)) return false;
        Node other = (Node) obj;
        return this.x == other.x && this.y == other.y;
    }
    
    @Override
    public int hashCode() {
        return 31 * x + y;
    }
    
    @Override
    public String toString() {
        return String.format("Node(%d, %d) [%s] f=%.1f", x, y, state, f);
    }
}
