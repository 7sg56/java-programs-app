import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

// Main Application Class
public class Main extends JFrame {
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 700;
    private static final int ARRAY_SIZE = 100;
    
    private VisualizationPanel visualPanel;
    private JComboBox<String> algorithmSelector;
    private JButton startButton;
    private JButton generateButton;
    private JSlider speedSlider;
    private JLabel statusLabel;
    
    private int[] array;
    private int delay = 20;
    private boolean sorting = false;
    
    public Main() {
        setTitle("Algorithm Visualizer - Top 5 Sorting Algorithms");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initializeArray();
        initializeComponents();
        layoutComponents();
        
        setVisible(true);
    }
    
    private void initializeArray() {
        array = new int[ARRAY_SIZE];
        Random rand = new Random();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = rand.nextInt(500) + 50; // Values between 50 and 550
        }
    }
    
    private void initializeComponents() {
        // Visualization Panel
        visualPanel = new VisualizationPanel(array);
        
        // Algorithm Selector
        String[] algorithms = {"Bubble Sort", "Selection Sort", "Insertion Sort", "Merge Sort", "Quick Sort"};
        algorithmSelector = new JComboBox<>(algorithms);
        algorithmSelector.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Buttons
        startButton = new JButton("Start Sorting");
        startButton.setFont(new Font("Arial", Font.BOLD, 14));
        startButton.setFocusable(false);
        startButton.addActionListener(e -> startSorting());
        
        generateButton = new JButton("Generate Array");
        generateButton.setFont(new Font("Arial", Font.BOLD, 14));
        generateButton.setFocusable(false);
        generateButton.addActionListener(e -> generateNewArray());
        
        // Speed Slider
        speedSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 50);
        speedSlider.setMajorTickSpacing(25);
        speedSlider.setMinorTickSpacing(5);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.addChangeListener(e -> {
            // Inverse relationship: higher slider value = slower speed
            delay = 101 - speedSlider.getValue();
        });
        
        // Status Label
        statusLabel = new JLabel("Ready to sort");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }
    
    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Top Control Panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        controlPanel.setBackground(new Color(240, 240, 240));
        
        controlPanel.add(new JLabel("Algorithm:"));
        controlPanel.add(algorithmSelector);
        controlPanel.add(startButton);
        controlPanel.add(generateButton);
        controlPanel.add(new JLabel("Speed:"));
        controlPanel.add(speedSlider);
        
        add(controlPanel, BorderLayout.NORTH);
        
        // Central Visualization Panel
        add(visualPanel, BorderLayout.CENTER);
        
        // Bottom Status Panel
        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(new Color(240, 240, 240));
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.SOUTH);
    }
    
    private void generateNewArray() {
        if (sorting) return;
        
        initializeArray();
        visualPanel.setArray(array);
        visualPanel.resetColors();
        visualPanel.repaint();
        statusLabel.setText("New array generated - Ready to sort");
    }
    
    private void startSorting() {
        if (sorting) return;
        
        sorting = true;
        startButton.setEnabled(false);
        generateButton.setEnabled(false);
        algorithmSelector.setEnabled(false);
        
        String selectedAlgorithm = (String) algorithmSelector.getSelectedItem();
        statusLabel.setText("Sorting with " + selectedAlgorithm + "...");
        
        Thread sortingThread = new Thread(() -> {
            SortingAlgorithms sorter = new SortingAlgorithms(array, visualPanel, this);
            
            switch (selectedAlgorithm) {
                case "Bubble Sort":
                    sorter.bubbleSort();
                    break;
                case "Selection Sort":
                    sorter.selectionSort();
                    break;
                case "Insertion Sort":
                    sorter.insertionSort();
                    break;
                case "Merge Sort":
                    sorter.mergeSort(0, array.length - 1);
                    break;
                case "Quick Sort":
                    sorter.quickSort(0, array.length - 1);
                    break;
            }
            
            sortingComplete();
        });
        
        sortingThread.start();
    }
    
    private void sortingComplete() {
        SwingUtilities.invokeLater(() -> {
            visualPanel.markAllSorted();
            visualPanel.repaint();
            statusLabel.setText("Sorting complete!");
            
            startButton.setEnabled(true);
            generateButton.setEnabled(true);
            algorithmSelector.setEnabled(true);
            sorting = false;
        });
    }
    
    public int getDelay() {
        return delay;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}

// Visualization Panel Class
class VisualizationPanel extends JPanel {
    private int[] array;
    private int[] barColors; // 0 = default, 1 = comparing, 2 = swapping, 3 = sorted
    
    private static final Color DEFAULT_COLOR = new Color(70, 130, 180); // Steel Blue
    private static final Color COMPARING_COLOR = new Color(255, 215, 0); // Gold
    private static final Color SWAPPING_COLOR = new Color(220, 20, 60); // Crimson
    private static final Color SORTED_COLOR = new Color(34, 139, 34); // Forest Green
    
    public VisualizationPanel(int[] array) {
        this.array = array;
        this.barColors = new int[array.length];
        setBackground(Color.WHITE);
    }
    
    public void setArray(int[] array) {
        this.array = array;
        this.barColors = new int[array.length];
    }
    
    public void resetColors() {
        for (int i = 0; i < barColors.length; i++) {
            barColors[i] = 0;
        }
    }
    
    public void setComparing(int index1, int index2) {
        resetColors();
        if (index1 >= 0 && index1 < barColors.length) barColors[index1] = 1;
        if (index2 >= 0 && index2 < barColors.length) barColors[index2] = 1;
    }
    
    public void setSwapping(int index1, int index2) {
        resetColors();
        if (index1 >= 0 && index1 < barColors.length) barColors[index1] = 2;
        if (index2 >= 0 && index2 < barColors.length) barColors[index2] = 2;
    }
    
    public void setSorted(int index) {
        if (index >= 0 && index < barColors.length) {
            barColors[index] = 3;
        }
    }
    
    public void markAllSorted() {
        for (int i = 0; i < barColors.length; i++) {
            barColors[i] = 3;
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        int barWidth = width / array.length;
        
        for (int i = 0; i < array.length; i++) {
            int barHeight = (int) ((double) array[i] / 600 * height);
            int x = i * barWidth;
            int y = height - barHeight;
            
            // Set color based on state
            switch (barColors[i]) {
                case 1:
                    g2d.setColor(COMPARING_COLOR);
                    break;
                case 2:
                    g2d.setColor(SWAPPING_COLOR);
                    break;
                case 3:
                    g2d.setColor(SORTED_COLOR);
                    break;
                default:
                    g2d.setColor(DEFAULT_COLOR);
            }
            
            g2d.fillRect(x, y, barWidth - 1, barHeight);
        }
    }
}

// Sorting Algorithms Class
class SortingAlgorithms {
    private int[] array;
    private VisualizationPanel panel;
    private Main visualizer;
    
    public SortingAlgorithms(int[] array, VisualizationPanel panel, Main visualizer) {
        this.array = array;
        this.panel = panel;
        this.visualizer = visualizer;
    }
    
    private void sleep() {
        try {
            Thread.sleep(visualizer.getDelay());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    // Bubble Sort
    public void bubbleSort() {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                panel.setComparing(j, j + 1);
                panel.repaint();
                sleep();
                
                if (array[j] > array[j + 1]) {
                    panel.setSwapping(j, j + 1);
                    panel.repaint();
                    sleep();
                    
                    // Swap
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
            panel.setSorted(n - i - 1);
        }
        panel.setSorted(0);
    }
    
    // Selection Sort
    public void selectionSort() {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                panel.setComparing(minIdx, j);
                panel.repaint();
                sleep();
                
                if (array[j] < array[minIdx]) {
                    minIdx = j;
                }
            }
            
            if (minIdx != i) {
                panel.setSwapping(i, minIdx);
                panel.repaint();
                sleep();
                
                // Swap
                int temp = array[i];
                array[i] = array[minIdx];
                array[minIdx] = temp;
            }
            panel.setSorted(i);
        }
        panel.setSorted(n - 1);
    }
    
    // Insertion Sort
    public void insertionSort() {
        int n = array.length;
        panel.setSorted(0);
        
        for (int i = 1; i < n; i++) {
            int key = array[i];
            int j = i - 1;
            
            panel.setComparing(i, j);
            panel.repaint();
            sleep();
            
            while (j >= 0 && array[j] > key) {
                panel.setSwapping(j, j + 1);
                panel.repaint();
                sleep();
                
                array[j + 1] = array[j];
                j--;
                
                if (j >= 0) {
                    panel.setComparing(j, j + 1);
                    panel.repaint();
                    sleep();
                }
            }
            array[j + 1] = key;
            panel.setSorted(i);
        }
    }
    
    // Merge Sort
    public void mergeSort(int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            
            mergeSort(left, mid);
            mergeSort(mid + 1, right);
            merge(left, mid, right);
        }
    }
    
    private void merge(int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];
        
        for (int i = 0; i < n1; i++) {
            leftArray[i] = array[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = array[mid + 1 + j];
        }
        
        int i = 0, j = 0, k = left;
        
        while (i < n1 && j < n2) {
            panel.setComparing(left + i, mid + 1 + j);
            panel.repaint();
            sleep();
            
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            panel.setSwapping(k, k);
            panel.repaint();
            sleep();
            k++;
        }
        
        while (i < n1) {
            array[k] = leftArray[i];
            panel.setSwapping(k, k);
            panel.repaint();
            sleep();
            i++;
            k++;
        }
        
        while (j < n2) {
            array[k] = rightArray[j];
            panel.setSwapping(k, k);
            panel.repaint();
            sleep();
            j++;
            k++;
        }
        
        for (int idx = left; idx <= right; idx++) {
            panel.setSorted(idx);
        }
    }
    
    // Quick Sort
    public void quickSort(int low, int high) {
        if (low < high) {
            int pivotIndex = partition(low, high);
            quickSort(low, pivotIndex - 1);
            quickSort(pivotIndex + 1, high);
        }
    }
    
    private int partition(int low, int high) {
        int pivot = array[high];
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            panel.setComparing(j, high);
            panel.repaint();
            sleep();
            
            if (array[j] < pivot) {
                i++;
                panel.setSwapping(i, j);
                panel.repaint();
                sleep();
                
                // Swap
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        
        panel.setSwapping(i + 1, high);
        panel.repaint();
        sleep();
        
        // Swap pivot
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        
        panel.setSorted(i + 1);
        return i + 1;
    }
}