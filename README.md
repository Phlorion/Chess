# Chess Engine

A feature-complete, modular Chess engine and interactive Swing application built in Java 21. Featuring an AI opponent powered by Minimax search with Alpha-Beta Pruning, sound effects, custom ASCII board state loading, legal move verification, castling, pawn promotion, and check/checkmate/stalemate detection.

---

## Features

- **Full Chess Rules & Mechanics**
  - Complete piece movement validation (Pawns, Knights, Bishops, Rooks, Queens, Kings).
  - Special moves: **Castling** (Kingside & Queenside), **Pawn Promotion** (interactively pick Queen, Rook, Bishop, or Knight), and initial pawn double-steps.
  - Automatic **Check**, **Checkmate**, and **Stalemate** state evaluation.
  - Illegal move prevention (filtering out moves that leave or put the King in check).

- **AI Engine**
  - **Minimax Algorithm with Alpha-Beta Pruning** (`MinimaxAlgorithm`): Evaluates material values and positional safety with configurable search depth and simulated thinking delay.
  - **Random Choice AI** (`RandomChoiceAlgorithm`): Lightweight fallback AI picking random legal moves.
  - Asynchronous AI move processing so the GUI remains smooth and responsive.

- **Rich Java Swing GUI**
  - 8x8 grid rendering with custom high-resolution piece graphics.
  - Visual indicators:
    - Highlight for selected piece.
    - Highlight for legal target destinations.
    - Highlight for last performed move.
  - Interactive **Pawn Promotion Overlay** rendered directly on top of the grid.
  - Mouse hover cursor effects (`HAND_CURSOR`).

- **Audio System**
  - Sound cues using Java Sound API (`javax.sound.sampled`):
    - `move-self.wav`: Regular piece movements.
    - `capture.wav`: Piece captures.
    - `castle.wav`: Castling maneuvers.
    - `move-check.wav`: Moves that place the opponent in Check.

- **Custom Board State Loader (`BoardReader`)**
  - Load arbitrary initial board configurations or custom chess puzzles from ASCII layout files.

---

## Project Structure

```text
src/main/java/dev/phlorion/chess/
├── Board.java                    # 8x8 grid, piece tracking, turn history, attack checking
├── Player.java                   # Player representation (White/Black) & move validation
│
├── ai/
│   ├── Algorithm.java            # Interface for move selection algorithms
│   ├── MinimaxAlgorithm.java     # Minimax algorithm with Alpha-Beta pruning
│   └── RandomChoiceAlgorithm.java # Random legal move selection
│
├── engine/
│   ├── GameEngine.java           # Central game loop, turn transitions, audio trigger
│   ├── EnginePlayer.java         # Links a Player with a MoveProvider
│   ├── MoveProvider.java         # Interface for requesting moves (Human/AI)
│   ├── HumanProvider.java        # Swing GUI mouse input move provider
│   ├── AIProvider.java           # Asynchronous AI thread move provider
│   └── MoveCallback.java        # Move selection callback interface
│
├── gui/
│   ├── Game.java                 # Base Swing frame & window container setup
│   ├── AIGame.java               # Main entry point for Human vs AI mode
│   ├── StaticGame.java           # Main entry point for Human vs Human (local 2-player)
│   ├── GridPanel.java            # Main 8x8 chessboard JPanel with highlights & sounds
│   ├── Cell.java                 # Individual grid cell component
│   ├── PiecePanel.java           # Piece PNG rendering component
│   └── PromotionOverlay.java    # Interactive pawn promotion selection dialog
│
├── move/
│   ├── Move.java                 # Standard move representation (execute / undo)
│   └── CastlingMove.java         # Dual-piece movement for King + Rook castling
│
├── pieces/
│   ├── Piece.java                # Abstract piece base class
│   ├── PieceColor.java           # Enum (WHITE, BLACK)
│   ├── PieceKind.java            # Enum (PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING)
│   ├── SlidingPiece.java         # Base class for linear sliding moves (Rook, Bishop, Queen)
│   ├── Pawn.java                 # Pawn movement logic & promotion triggers
│   ├── Knight.java               # Knight L-shaped movement logic
│   ├── Bishop.java               # Bishop diagonal movement logic
│   ├── Rook.java                 # Rook straight movement logic
│   ├── Queen.java                # Queen combination movement logic
│   └── King.java                 # King movement logic & castling validation
│
└── misc/
    ├── Vector2.java              # 2D coordinate structure (row, column)
    └── BoardReader.java          # ASCII file to Piece[][] matrix parser
```

---

## Prerequisites

- **Java JDK**: Version 21 or higher
- **Maven**: Version 3.8+ (or built-in Maven wrapper)

---

## Building the Project

Compile the source code using Maven:

```bash
mvn clean compile
```

---

## Running the Game

You can run either **Human vs AI** or **Local 2-Player (Human vs Human)** mode:

### 1. Human vs AI Mode (`AIGame`)
Play against the Minimax AI bot.

```bash
mvn exec:java -Dexec.mainClass="dev.phlorion.chess.gui.AIGame"
```

### 2. Local 2-Player Mode (`StaticGame`)
Play against another human player locally on the same computer.

```bash
mvn exec:java -Dexec.mainClass="dev.phlorion.chess.gui.StaticGame"
```

---

## Custom Board Layouts

The `BoardReader` class parses text-based layout files where each character corresponds to a piece on the board:

| Character | Piece (Black) | Character | Piece (White) |
| :---: | :--- | :---: | :--- |
| `r` | Rook | `R` | Rook |
| `n` | Knight | `N` | Knight |
| `b` | Bishop | `B` | Bishop |
| `q` | Queen | `Q` | Queen |
| `k` | King | `K` | King |
| `p` | Pawn | `P` | Pawn |
| `.` / space | Empty square | | |

Preset test layouts are located in `src/main/resources/`:
- `test1`, `test2`, `test3`, `test4`, `test5`
- `test_castle` (demonstrating castling conditions)

---

## Customizing the AI

The AI engine uses the `MinimaxAlgorithm`. You can adjust search depth and thinking delay when instantiating the algorithm:

```java
// Minimax with search depth of 4 levels and a 300ms delay for natural pace
Algorithm aiAlgorithm = new MinimaxAlgorithm(4, 300);
AIProvider aiProvider = new AIProvider(aiAlgorithm);
```

Or switch to `RandomChoiceAlgorithm` for a simpler opponent:

```java
Algorithm aiAlgorithm = new RandomChoiceAlgorithm();
AIProvider aiProvider = new AIProvider(aiAlgorithm);
```

---

## License

This project is open source and available for educational and personal use.
