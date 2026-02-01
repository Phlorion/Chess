package dev.phlorion.chess.gui;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.engine.EnginePlayer;
import dev.phlorion.chess.engine.GameEngine;
import dev.phlorion.chess.engine.HumanProvider;
import dev.phlorion.chess.misc.Vector2;
import dev.phlorion.chess.move.Move;
import dev.phlorion.chess.pieces.Piece;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LANGame extends Game {
    public static void main(String[] args) {
        Game game = new LANGame();

        Board board = new Board("src/main/resources/test_castle");

        JFrame frame = game.initializeFrame();
        GridPanel grid = (GridPanel) game.loadGame(frame, board);

        EnginePlayer white = new EnginePlayer(board.getCurrentPlayer(), new HumanProvider());
        EnginePlayer black = new EnginePlayer(board.getOpponentPlayer(), new HumanProvider());
        GameEngine engine = new GameEngine(board, grid, white, black);
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
                    if (cell.getPiecePanel() == null) return;

                    Piece selectedPiece = cell.getPiecePanel().getPieceReference();
                    List<Move> moves = engine.getCurrentPlayer().getPlayer().getPlayerLegalMoves(engine.getBoard())
                            .stream()
                            .filter(m -> m.getPiece().equals(selectedPiece))
                            .toList();

                    ArrayList<Cell> candidates = new ArrayList<>();
                    for (Move m : moves) {
                        Vector2 targetPos = m.getTargetedPos();
                        Cell targetCell = grid.getCell(targetPos.x, targetPos.y);
                        candidates.add(targetCell);
                        cellToMove.put(targetCell, m);
                    }
                    grid.addCandidates(candidates);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    // get the cell where the mouse was released
                    Point p = e.getPoint();
                    Point gridPoint = SwingUtilities.convertPoint(cell, p, grid);

                    int column = gridPoint.x / cell.getWidth();
                    int row = gridPoint.y / cell.getHeight();
                    Cell targetCell = grid.getCell(row, column);

                    if (cellToMove.containsKey(targetCell)) {
                        ((HumanProvider) engine.getCurrentPlayer().getMoveProvider()).submitMove(cellToMove.get(targetCell));

                        // check if check mated
                        if (engine.isCheckmated()) {
                            System.out.println(engine.getBoard().getOpponentPlayer() + " WON!");
                            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        } else if (engine.isStaleMated()) {
                            System.out.println("TIE!");
                            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        }
                    }

                    cellToMove.clear();
                    grid.flushCandidates();
                }
            });
        }
    }
}
