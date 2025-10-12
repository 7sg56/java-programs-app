import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Main class for Pathfinding Visualizer
 * Interactive visualization of A* and Dijkstra's pathfinding algorithms
 */
public class Main extends JFrame {
    private static final int GRID_SIZE = 30;
    private static final int CELL_SIZE = 20;
    private static final int WINDOW_SIZE = GRID_SIZE * CELL_SIZE;
    
    private Node[][] grid;
    private GridPanel gridPanel;
    private Node startNode;
    private Node endNode;
    private int visualizationDelay = 20;
    private String selectedAlgorithm = "A*";
    
    // Mouse state
    private enum DrawMode { NONE, WALL, ERASE }
    private DrawMode drawMode = DrawMode.NONE;
    
    public Main() {
        setTitle("Pathfinding Visualizer - A* & Dijkstra");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        initializeGrid();
        setupUI();
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void initializeGrid() {
        grid = new Node[GRID_SIZE][GRID_SIZE];
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                grid[x][y] = new Node(x, y);
            }
        }
        
        // Set default start and end
        startNode = grid[5][5];
        startNode.setState(Node.State.START);
        
        endNode = grid[GRID_SIZE - 6][GRID_SIZE - 6];
        endNode.setState(Node.State.END);
    }
    
    private void setupUI() {
        setLayout(new BorderLayout());
        
        // Grid panel
        gridPanel = new GridPanel();
        add(gridPanel, BorderLayout.CENTER);
        
        // Control panel
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(new Color(240, 240, 240));
        panel.setPreferredSize(new Dimension(WINDOW_SIZE, 80));
        
        // Algorithm selection
        JLabel algoLabel = new JLabel("Algorithm:");
        algoLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        JComboBox<String> algoCombo = new JComboBox<>(new String[]{"A*", "Dijkstra", "BFS", "Bellman-Ford"});
        algoCombo.addActionListener(e -> selectedAlgorithm = (String) algoCombo.getSelectedItem());
        
        // Speed control
        JLabel speedLabel = new JLabel("Speed:");
        speedLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        JSlider speedSlider = new JSlider(0, 100, 80);
        speedSlider.setPreferredSize(new Dimension(150, 25));
        speedSlider.addChangeListener(e -> {
            int value = speedSlider.getValue();
            visualizationDelay = 100 - value; // Invert so higher value = faster
        });
        
        // Buttons
        JButton runButton = new JButton("▶ Run");
        runButton.setForeground(Color.BLACK);
        runButton.setFocusPainted(false);
        runButton.setFont(new Font("Arial", Font.BOLD, 12));
        runButton.addActionListener(e -> runAlgorithm());
        
        JButton clearPathButton = new JButton("Clear Path");
        clearPathButton.addActionListener(e -> clearPath());
        
        JButton clearAllButton = new JButton("Clear All");
        clearAllButton.addActionListener(e -> clearAll());
        
        JButton randomMazeButton = new JButton("🎲 Random Maze");
        randomMazeButton.setForeground(Color.BLACK);
        randomMazeButton.setFocusPainted(false);
        randomMazeButton.setFont(new Font("Arial", Font.BOLD, 12));
        randomMazeButton.addActionListener(e -> generateRandomMaze());
        
        // Add components
        panel.add(algoLabel);
        panel.add(algoCombo);
        panel.add(new JSeparator(SwingConstants.VERTICAL));
        panel.add(speedLabel);
        panel.add(speedSlider);
        panel.add(new JSeparator(SwingConstants.VERTICAL));
        panel.add(runButton);
        panel.add(clearPathButton);
        panel.add(clearAllButton);
        panel.add(randomMazeButton);
        
        return panel;
    }
    
    private void runAlgorithm() {
        clearPath();
        
        new Thread(() -> {
            PathfindingAlgorithm pathfinder = new PathfindingAlgorithm(
                grid, GRID_SIZE, gridPanel, visualizationDelay
            );
            
            PathfindingResult result;
            switch (selectedAlgorithm) {
                case "A*":
                    result = pathfinder.aStar(startNode, endNode);
                    break;
                case "Dijkstra":
                    result = pathfinder.dijkstra(startNode, endNode);
                    break;
                case "BFS":
                    result = pathfinder.bfs(startNode, endNode);
                    break;
                case "Bellman-Ford":
                    result = pathfinder.bellmanFord(startNode, endNode);
                    break;
                default:
                    result = pathfinder.aStar(startNode, endNode);
            }
            
            PathfindingResult finalResult = result;
            SwingUtilities.invokeLater(() -> {
                String message = selectedAlgorithm + " Algorithm:\n" + finalResult.toString();
                JOptionPane.showMessageDialog(this, message, "Result", 
                    finalResult.isSuccess() ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
            });
        }).start();
    }
    
    private void clearPath() {
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                grid[x][y].reset();
            }
        }
        gridPanel.repaint();
    }
    
    private void clearAll() {
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                grid[x][y].setState(Node.State.EMPTY);
                grid[x][y].reset();
            }
        }
        
        // Reset start and end
        startNode = grid[5][5];
        startNode.setState(Node.State.START);
        
        endNode = grid[GRID_SIZE - 6][GRID_SIZE - 6];
        endNode.setState(Node.State.END);
        
        gridPanel.repaint();
    }
    
    /**
     * Generates a random maze with walls
     */
    private void generateRandomMaze() {
        clearAll();
        
        java.util.Random random = new java.util.Random();
        
        // Random density between 20% and 35%
        double wallDensity = 0.20 + random.nextDouble() * 0.15;
        
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                Node node = grid[x][y];
                
                // Don't place walls on start or end nodes
                if (node != startNode && node != endNode) {
                    if (random.nextDouble() < wallDensity) {
                        node.setState(Node.State.WALL);
                    }
                }
            }
        }
        
        // Optional: Add some patterns for more interesting mazes
        addRandomPatterns(random);
        
        gridPanel.repaint();
    }
    
    /**
     * Adds random wall patterns for more interesting mazes
     */
    private void addRandomPatterns(java.util.Random random) {
        int numPatterns = random.nextInt(3) + 2; // 2-4 patterns
        
        for (int i = 0; i < numPatterns; i++) {
            int pattern = random.nextInt(3);
            
            switch (pattern) {
                case 0: // Horizontal line
                    addHorizontalLine(random);
                    break;
                case 1: // Vertical line
                    addVerticalLine(random);
                    break;
                case 2: // L-shape
                    addLShape(random);
                    break;
            }
        }
    }
    
    private void addHorizontalLine(java.util.Random random) {
        int y = random.nextInt(GRID_SIZE);
        int startX = random.nextInt(GRID_SIZE / 2);
        int length = random.nextInt(GRID_SIZE / 3) + 3;
        
        for (int x = startX; x < Math.min(startX + length, GRID_SIZE); x++) {
            Node node = grid[x][y];
            if (node != startNode && node != endNode) {
                node.setState(Node.State.WALL);
            }
        }
    }
    
    private void addVerticalLine(java.util.Random random) {
        int x = random.nextInt(GRID_SIZE);
        int startY = random.nextInt(GRID_SIZE / 2);
        int length = random.nextInt(GRID_SIZE / 3) + 3;
        
        for (int y = startY; y < Math.min(startY + length, GRID_SIZE); y++) {
            Node node = grid[x][y];
            if (node != startNode && node != endNode) {
                node.setState(Node.State.WALL);
            }
        }
    }
    
    private void addLShape(java.util.Random random) {
        int x = random.nextInt(GRID_SIZE - 5) + 2;
        int y = random.nextInt(GRID_SIZE - 5) + 2;
        int size = random.nextInt(4) + 3;
        
        // Horizontal part
        for (int i = 0; i < size; i++) {
            if (x + i < GRID_SIZE) {
                Node node = grid[x + i][y];
                if (node != startNode && node != endNode) {
                    node.setState(Node.State.WALL);
                }
            }
        }
        
        // Vertical part
        for (int i = 0; i < size; i++) {
            if (y + i < GRID_SIZE) {
                Node node = grid[x][y + i];
                if (node != startNode && node != endNode) {
                    node.setState(Node.State.WALL);
                }
            }
        }
    }
    
    /**
     * Custom panel for drawing the grid
     */
    class GridPanel extends JPanel {
        public GridPanel() {
            setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
            setBackground(Color.WHITE);
            
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    handleMousePress(e);
                }
                
                @Override
                public void mouseReleased(MouseEvent e) {
                    drawMode = DrawMode.NONE;
                }
            });
            
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    handleMouseDrag(e);
                }
            });
        }
        
        private void handleMousePress(MouseEvent e) {
            int x = e.getX() / CELL_SIZE;
            int y = e.getY() / CELL_SIZE;
            
            if (x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE) {
                Node node = grid[x][y];
                
                if (node.getState() == Node.State.WALL) {
                    drawMode = DrawMode.ERASE;
                    node.setState(Node.State.EMPTY);
                } else if (node.getState() == Node.State.EMPTY) {
                    drawMode = DrawMode.WALL;
                    node.setState(Node.State.WALL);
                }
                
                repaint();
            }
        }
        
        private void handleMouseDrag(MouseEvent e) {
            int x = e.getX() / CELL_SIZE;
            int y = e.getY() / CELL_SIZE;
            
            if (x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE) {
                Node node = grid[x][y];
                
                if (node != startNode && node != endNode) {
                    if (drawMode == DrawMode.WALL && node.getState() == Node.State.EMPTY) {
                        node.setState(Node.State.WALL);
                        repaint();
                    } else if (drawMode == DrawMode.ERASE && node.getState() == Node.State.WALL) {
                        node.setState(Node.State.EMPTY);
                        repaint();
                    }
                }
            }
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Draw nodes
            for (int x = 0; x < GRID_SIZE; x++) {
                for (int y = 0; y < GRID_SIZE; y++) {
                    Node node = grid[x][y];
                    g2d.setColor(node.getColor());
                    g2d.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
            
            // Draw grid lines
            g2d.setColor(new Color(200, 200, 200));
            for (int i = 0; i <= GRID_SIZE; i++) {
                g2d.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, WINDOW_SIZE);
                g2d.drawLine(0, i * CELL_SIZE, WINDOW_SIZE, i * CELL_SIZE);
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new Main());
    }
}