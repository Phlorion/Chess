package dev.phlorion.chess.gui;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.misc.Vector2;
import dev.phlorion.chess.move.Move;
import dev.phlorion.chess.pieces.Piece;
import dev.phlorion.chess.pieces.PieceColor;
import dev.phlorion.chess.pieces.PieceKind;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Game {
    public final static String NAME = "Chess";
    public final static int WIDTH = 600;
    public final static int HEIGHT = 600;

    public static HashMap<Piece, PiecePanel> pieceToPanel = new HashMap<>();

    private static JFrame initializeFrame() {
        JFrame frame = new JFrame(NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        return frame;
    }

    public static void main(String[] args) {
        JFrame frame = Game.initializeFrame();

        Board board = new Board("src/main/resources/test5");
        board.setCurrentPlayer(PieceColor.WHITE);

        for (Piece p : board.getPieces(PieceColor.WHITE))
            pieceToPanel.put(p, new PiecePanel(p));
        for (Piece p : board.getPieces(PieceColor.BLACK))
            pieceToPanel.put(p, new PiecePanel(p));

        GridPanel grid = new GridPanel(board);
        frame.add(grid);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        for (Cell cell : grid.getCells()) {
            cell.addMouseListener(new MouseAdapter() {
                private Piece holdingPiece = null;
                private final HashMap<Cell, Move> cellToMove = new HashMap<>();

                @Override
                public void mouseEntered(MouseEvent e) {
                    cell.brighten();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    cell.setBackground(cell.getOriginalColor());
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if (cell.getPiecePanel() == null) return;

                    Piece selectedPiece = cell.getPiecePanel().getPieceReference();
                    this.holdingPiece = selectedPiece;
                    List<Move> moves = board.getCurrentPlayer().getPlayerLegalMoves(board)
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
                        board.getCurrentPlayer().makeMove(board, holdingPiece, cellToMove.get(targetCell).getTargetedPos());
                        cell.removePiecePanel();
                        targetCell.setPiecePanel(pieceToPanel.get(holdingPiece));

                        board.setCurrentPlayer(board.getOpponentPlayer().getType());

                        // check if check mated
                        if (board.getCurrentPlayer().isCheckMated(board, board.getOpponentPlayer())) {
                            System.out.println(board.getOpponentPlayer() + " WON!");
                            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        }
                    }

                    holdingPiece = null;
                    cellToMove.clear();
                    grid.flushCandidates();
                }
            });
        }
    }
}
