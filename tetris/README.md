# Tetris Game

A classic Tetris game implementation in Java using Swing GUI.

## Features

### 🎮 Core Gameplay
- **Classic Tetris Mechanics**: Falling tetromino pieces with rotation and movement
- **Line Clearing**: Complete horizontal lines to clear them and score points
- **Level Progression**: Game speed increases with each level
- **Score System**: Points for line clears and piece drops
- **Next Piece Preview**: See the upcoming piece before it falls
- **Game Over Detection**: Game ends when pieces reach the top

### 🎯 Game Features
- **Smooth Controls**: Responsive keyboard input for piece movement
- **Pause/Resume**: Pause the game at any time
- **Restart Functionality**: Start a new game without closing the application
- **Visual Feedback**: Clear graphics with color-coded pieces
- **Real-time Score**: Live score and level updates

## Technology Stack

- **Java**: Core application language
- **Swing**: GUI framework for desktop interface
- **AWT Graphics**: Custom rendering for game graphics
- **Timer**: Game loop and piece falling mechanics
- **No external dependencies**

## Project Structure

```
tetris/
├── Main.java                    # Application entry point
├── game/
│   ├── GameState.java          # Game state management
│   ├── Grid.java               # Game grid logic
│   └── Tetromino.java          # Tetromino piece logic
├── ui/
│   ├── GamePanel.java          # Main game rendering
│   ├── NextPiecePanel.java     # Next piece preview
│   └── ScoreBoard.java         # Score and level display
├── input/
│   └── KeyHandler.java         # Keyboard input handling
├── utils/
│   └── Constants.java          # Game constants and configuration
├── compile_and_run.sh          # Easy compilation and execution script
└── README.md                   # This file
```

## Game Controls

| Key | Action |
|-----|--------|
| ← → | Move piece left/right |
| ↓ | Soft drop (faster fall) |
| ↑ | Hard drop (instant drop) |
| Space | Rotate piece clockwise |
| P | Pause/Resume game |
| R | Restart game |
| ESC | Quit game |

## How to Run

### Option 1: Using the Script (Recommended)
```bash
cd tetris
./compile_and_run.sh
```

### Option 2: Manual Compilation
```bash
# Compile
javac -cp "." *.java game/*.java ui/*.java input/*.java utils/*.java

# Run
java Main
```

### Option 3: Using an IDE
1. Import the project into IntelliJ IDEA or Eclipse
2. Run `Main.java`

## Game Rules

### Basic Gameplay
1. **Tetrominoes Fall**: Different shaped pieces fall from the top
2. **Move and Rotate**: Use arrow keys to position pieces
3. **Fill Rows**: Complete horizontal lines to clear them
4. **Score Points**: Earn points for line clears and piece drops
5. **Level Up**: Game speed increases with each level
6. **Game Over**: Game ends when pieces reach the top

### Scoring System
- **Line Clears**: Points based on number of lines cleared simultaneously
  - 1 line: 100 points
  - 2 lines: 300 points
  - 3 lines: 500 points
  - 4 lines (Tetris): 800 points
- **Soft Drop**: 1 point per cell
- **Hard Drop**: 2 points per cell

### Level Progression
- **Level 1**: Slowest speed
- **Each Level**: Speed increases
- **Level Up**: Occurs after clearing 10 lines

## Tetromino Pieces

The game includes all 7 classic Tetromino pieces:

1. **I-Piece**: Long vertical line (cyan)
2. **O-Piece**: 2x2 square (yellow)
3. **T-Piece**: T-shaped (purple)
4. **S-Piece**: S-shaped (green)
5. **Z-Piece**: Z-shaped (red)
6. **J-Piece**: J-shaped (blue)
7. **L-Piece**: L-shaped (orange)

## Game Features in Detail

### Game State Management
- **Current Piece**: Active falling piece
- **Next Piece**: Preview of upcoming piece
- **Grid State**: 2D array representing the game board
- **Score Tracking**: Current score, lines cleared, level
- **Game Status**: Playing, paused, game over states

### Input Handling
- **Responsive Controls**: Immediate response to key presses
- **Key Mapping**: Arrow keys for movement, space for rotation
- **Special Keys**: Pause, restart, and quit functionality
- **Focus Management**: Game panel maintains focus for input

### Rendering System
- **Custom Graphics**: Hand-drawn game elements
- **Color Coding**: Each piece type has distinct colors
- **Grid Display**: Clear visual representation of the game state
- **UI Components**: Score display, next piece preview, controls

### Game Logic
- **Collision Detection**: Prevents pieces from overlapping
- **Line Clearing**: Detects and removes completed lines
- **Piece Rotation**: Implements proper rotation mechanics
- **Boundary Checking**: Prevents pieces from going out of bounds

## Code Architecture

### Game Components
- **GameState**: Central game logic and state management
- **Grid**: 2D game board representation and line clearing
- **Tetromino**: Individual piece logic and rotation
- **GamePanel**: Main rendering and game loop
- **KeyHandler**: Input processing and game control

### Design Patterns
- **MVC Pattern**: Separation of game logic, view, and input
- **Observer Pattern**: UI updates based on game state changes
- **State Pattern**: Different game states (playing, paused, game over)

## Requirements

- **Java JDK 8 or higher**
- **No external dependencies**
- **Swing support** (included with Java)

## Troubleshooting

### Common Issues
1. **Java not found**: Install JDK and ensure it's in PATH
2. **Compilation errors**: Check Java version compatibility
3. **Game not responding**: Ensure the game panel has focus
4. **Graphics issues**: Update Java version or graphics drivers

### Performance Tips
- **Close other applications** for better performance
- **Use fullscreen mode** for optimal experience
- **Ensure Java is up to date** for best graphics support

## Game Tips

### For Beginners
1. **Start Slow**: Take time to plan piece placement
2. **Watch the Next Piece**: Plan ahead for upcoming pieces
3. **Clear Lines**: Focus on completing horizontal lines
4. **Practice Rotation**: Learn how pieces rotate

### For Advanced Players
1. **Tetris Strategy**: Aim for 4-line clears (Tetris)
2. **T-Spin Techniques**: Master advanced rotation moves
3. **Speed Play**: Practice with higher levels
4. **Pattern Recognition**: Learn common piece sequences

## Future Enhancements

- **High Score System**: Persistent high score tracking
- **Sound Effects**: Audio feedback for actions
- **Background Music**: Optional game music
- **Multiplayer Mode**: Two-player competitive mode
- **Custom Themes**: Different visual styles
- **Save/Load Game**: Game state persistence
- **Statistics**: Detailed game statistics and analytics

## Development Notes

### Code Organization
- **Package Structure**: Logical separation of concerns
- **Constants**: Centralized configuration values
- **Error Handling**: Robust error management
- **Documentation**: Comprehensive code comments

### Performance Considerations
- **Efficient Rendering**: Optimized graphics drawing
- **Memory Management**: Proper object lifecycle management
- **Timer Usage**: Smooth game loop implementation
- **Input Optimization**: Responsive keyboard handling

## License

This project is created for educational and demonstration purposes.

## Credits

- **Classic Tetris**: Based on the original Tetris game by Alexey Pajitnov
- **Java Implementation**: Custom implementation using Java Swing
- **Educational Purpose**: Created for learning Java game development
