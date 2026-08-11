package dev.phlorion.chess.gui;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.ai.Algorithm;
import dev.phlorion.chess.ai.MinimaxAlgorithm;
import dev.phlorion.chess.ai.RandomChoiceAlgorithm;
import dev.phlorion.chess.engine.AIProvider;
import dev.phlorion.chess.engine.EnginePlayer;
import dev.phlorion.chess.engine.GameEngine;
import dev.phlorion.chess.engine.HumanProvider;
import dev.phlorion.chess.engine.MoveCallback;
import dev.phlorion.chess.move.Move;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.HashMap;

public class AIGame extends Game {
    public static void main(String[] args) {
        Game game = new AIGame();

        Board board = new Board("src/main/resources/test_castle");

        JFrame frame = game.initializeFrame();
        GridPanel grid = (GridPanel) game.loadGame(frame, board);

        // White player is Human, Black player is AI for now
        // TODO: Make player choose if he wants to be white or black
        EnginePlayer white = new EnginePlayer(board.getCurrentPlayer(), new HumanProvider(grid));

        // Create an AIProvider
        // Override the existing requestMove method just to add a gameOverCheck in case the AI wins
        final GameEngine[] engineRef = new GameEngine[1];
        AIProvider aiProvider = new AIProvider(new MinimaxAlgorithm(3, 500)) {
            @Override
            public void requestMove(Board b, MoveCallback moveCallback) {
                super.requestMove(b, (move) -> {
                    moveCallback.onMoveSelected(move);
                    SwingUtilities.invokeLater(() -> checkGameOver(engineRef[0], frame));
                });
            }
        };
        EnginePlayer black = new EnginePlayer(board.getOpponentPlayer(), aiProvider);

        GameEngine engine = new GameEngine(board, grid, white, black);
        engineRef[0] = engine;
        engine.start();

        for (Cell cell : grid.getCells()) {
            cell.addMouseListener(new MouseAdapter() {
                private final HashMap<Cell, Move> cellToMove = new HashMap<>();

                @Override
                public void mouseEntered(MouseEvent e) {
                    if (cell.getPiecePanel() != null)
                        cell.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    grid.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if (grid.isShowingOverlay()) return;
                    if (engine.getCurrentPlayer().getMoveProvider() instanceof HumanProvider humanProvider) {
                        humanProvider.mousePressed(e, cell, cellToMove, engine);
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (engine.getCurrentPlayer().getMoveProvider() instanceof HumanProvider humanProvider) {
                        humanProvider.mouseReleased(e, cell, cellToMove, engine);
                        checkGameOver(engine, frame);
                    }
                }
            });
        }
    }

    private static void checkGameOver(GameEngine engine, JFrame frame) {
        if (engine.isCheckmated()) {
            System.out.println(engine.getBoard().getOpponentPlayer() + " WON!");
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        } else if (engine.isStaleMated()) {
            System.out.println("TIE!");
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
    }
}
