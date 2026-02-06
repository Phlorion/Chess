package dev.phlorion.chess.engine;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.gui.Cell;
import dev.phlorion.chess.gui.GridPanel;
import dev.phlorion.chess.misc.Vector2;
import dev.phlorion.chess.move.Move;
import dev.phlorion.chess.pieces.Pawn;
import dev.phlorion.chess.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HumanProvider implements MoveProvider {
    private MoveCallback pendingCallback;
    private final GridPanel grid;
    private Cell lastTargetCell;

    public HumanProvider(GridPanel grid) {
        this.grid = grid;
    }

    @Override
    public void requestMove(Board board, MoveCallback moveCallback) {
        this.pendingCallback = moveCallback;
    }

    public void submitMove(Board board, Move move) {
        if (pendingCallback == null) return;

        if (move.getPiece() instanceof Pawn && ((Pawn) move.getPiece()).canPromote(board, move)) {
            grid.showPromotionOverlay(lastTargetCell, move.getPiece().getPieceColor(), (choice) -> {
                move.setPromotionPiece(choice);
                grid.hidePromotionOverlay();
                pendingCallback.onMoveSelected(move);
                pendingCallback = null;
            });
        } else {
            pendingCallback.onMoveSelected(move);
            pendingCallback = null;
        }
    }

    public void mousePressed(MouseEvent e, Cell cell, HashMap<Cell, Move> cellToMove, GameEngine engine) {
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

    public void mouseReleased(MouseEvent e, Cell cell, HashMap<Cell, Move> cellToMove, GameEngine engine) {
        // get the cell where the mouse was released
        Point p = e.getPoint();
        Point gridPoint = SwingUtilities.convertPoint(cell, p, grid);

        int column = gridPoint.x / cell.getWidth();
        int row = gridPoint.y / cell.getHeight();
        Cell targetCell = grid.getCell(row, column);

        if (cellToMove.containsKey(targetCell)) {
            lastTargetCell = targetCell;
            this.submitMove(engine.getBoard(), cellToMove.get(targetCell));
        }

        cellToMove.clear();
        grid.flushCandidates();
    }
}
