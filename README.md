# Java Applications Portfolio

A collection of Java desktop applications demonstrating algorithms, GUI development, and software design patterns.

## 📂 Projects

### 🧮 Calculator
**A fully functional GUI calculator with keyboard support**

- Complete arithmetic operations (+, -, ×, ÷)
- Decimal number handling and keyboard shortcuts
- Error handling for division by zero
- Clean separation of UI and business logic

```bash
cd calculator && ./compile_and_run.sh
```

[📖 Full Documentation](calculator/README.md)

---

### 🗺️ Pathfinding Visualizer
**Interactive visualization of 6 pathfinding algorithms with real-time animation**

Watch algorithms explore a grid to find the shortest path from start to end:

1. **Greedy Best-First** ⚡⚡⚡ - Fastest (non-optimal)
2. **A*** ⚡⚡ - Optimal with heuristic
3. **Dijkstra** ⚡ - Guaranteed shortest path
4. **BFS** ⚡ - Level-by-level exploration
5. **Bellman-Ford** 🐌 - Edge relaxation (handles negative weights)
6. **DFS** 🐌 - Deep exploration (non-optimal)

**Features:**
- Interactive grid with wall drawing
- Real-time step-by-step visualization
- Random maze generation
- Adjustable animation speed
- Compare algorithm efficiency

```bash
cd pathfinding-vizualizer && ./compile_and_run.sh
```

[📖 Full Documentation](pathfinding-vizualizer/README.md)

---

### 📊 Sorting Visualizer
**Real-time visualization of 5 sorting algorithms with color-coded animations**

Watch sorting algorithms in action with animated bars:
- **Bubble Sort**, **Selection Sort**, **Insertion Sort** - O(n²)
- **Merge Sort**, **Quick Sort** - O(n log n)

**Features:**
- Color-coded comparison and swap states
- Adjustable animation speed
- Random array generation (100 elements)
- Multi-threaded for smooth UI

```bash
cd Sorting-vizualizer && ./compile_and_run.sh
```

[📖 Full Documentation](Sorting-vizualizer/README.md)

---

### 🎮 Tetris Game
**Classic Tetris implementation with smooth controls and modern UI**

Complete Tetris gameplay with line clearing, scoring, and level progression.

**Controls:** ← → (move), ↓ (soft drop), ↑ (hard drop), Space (rotate), P (pause), R (restart)

**Features:**
- Classic falling tetromino mechanics
- Next piece preview
- Score tracking and level progression
- Pause/resume functionality

```bash
cd tetris && ./compile_and_run.sh
```

[📖 Full Documentation](tetris/README.md)

---

### 🚢 Cruise Management System
**Enterprise-level booking and management system with database integration**

Complete cruise booking system with admin dashboard and customer interface.

**Features:**
- Dual interfaces (Admin & Customer)
- SQLite database with full CRUD operations
- Suite types and extra services with dynamic pricing
- PDF ticket generation (auto-download on booking)
- Real-time occupancy tracking and revenue reports

**Technology:** Swing GUI + SQLite + JDBC + PDF Generation

```bash
cd cruise-management-system && ./compile_and_run.sh
```

[📖 Full Documentation](cruise-management-system/README.md)

---

## 🛠️ Technical Stack

**Core Technologies:**
- Java (JDK 8+)
- Swing & AWT for GUI
- SQLite with JDBC (Cruise System)
- No external dependencies

**Design Patterns:**
- MVC (Model-View-Controller)
- DAO (Data Access Object)
- Observer Pattern
- State Pattern

**Key Concepts:**
- Object-Oriented Programming
- Data Structures (Priority Queues, Stacks, Sets, Maps)
- Algorithms (Pathfinding, Sorting, Graph traversal)
- Multi-threading (Non-blocking UI)
- Database Management (CRUD operations)

---

## 🚀 Quick Start

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Terminal/Command Prompt

### Running Projects

**Using Scripts (Recommended):**
```bash
cd <project-directory>
./compile_and_run.sh
```

**Manual Compilation:**
```bash
cd <project-directory>
javac *.java
java Main
```

**Using an IDE:**
Open project in IntelliJ IDEA, Eclipse, or VS Code and run `Main.java`

---

## 📚 Learning Path

**Recommended progression:**
1. **Calculator** - Basic GUI and event handling
2. **Tetris** - Game loop and state management
3. **Sorting Visualizer** - Algorithms and threading
4. **Pathfinding Visualizer** - Graph algorithms and data structures
5. **Cruise System** - Database integration and enterprise patterns

---

## 🎯 Skills Demonstrated

- ✅ Object-Oriented Programming with design patterns
- ✅ GUI Development with Swing/AWT
- ✅ Algorithm Design and Implementation (11+ algorithms)
- ✅ Database Integration with SQLite
- ✅ Multi-threaded Programming
- ✅ Event-Driven Architecture
- ✅ Error Handling and Validation

---

## 📁 Project Structure

```
APP-java-projects/
├── calculator/                    # GUI calculator
├── pathfinding-vizualizer/        # 6 pathfinding algorithms
├── Sorting-vizualizer/            # 5 sorting algorithms
├── tetris/                        # Classic Tetris game
├── cruise-management-system/      # Booking system with database
└── README.md                      # This file
```

---

**Created with ☕ Java** | Educational Portfolio

*All projects are fully functional, documented, and ready to run. Each includes a compilation script for easy setup.*
