package dev.phlorion.chess.gui;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.misc.Vector2;
import dev.phlorion.chess.move.Move;
import dev.phlorion.chess.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GridPanel extends JPanel {
    private final int width;
    private final int height;
    private final int rows;
    private final int columns;
    private Cell[] cells;

    private ArrayList<Cell> currentMoveCandidates = new ArrayList<>();

    public GridPanel(Board board, int width, int height) {
        this.rows = board.getBoardShape().x;
        this.columns = board.getBoardShape().y;
        this.cells = new Cell[rows*columns];
        this.width = width;
        this.height = height;

        initializeGrid(board);
    }

    private void initializeGrid(Board board) {
        int cellSize = this.width / this.columns;
        setBackground(Color.white);
        setLayout(new GridLayout(rows, columns));
        for (int i = 0; i < rows*columns; i++) {
            int row = i / columns;
            int col = i % rows;
            Piece piece = board.getPieceAt(new Vector2(row, col));
            Cell cell = new Cell(row, col, cellSize);
            if (piece != null)
                cell.setPiecePanel(Game.pieceToPanel.get(piece));
            cells[i] = cell;
            add(cell);
        }
    }

    public Cell[] getCells() {
        return cells;
    }

    public Cell getCell(int row, int col) {
        return cells[row*columns+col];
    }

    public void addCandidates(ArrayList<Cell> candidates) {
        currentMoveCandidates.addAll(candidates);
        for (Cell cell : candidates) {
            cell.setPlaceholder(true);
        }
        revalidate();
        repaint();
    }

    public void flushCandidates() {
        for  (Cell cell : currentMoveCandidates) {
            cell.setPlaceholder(false);
        }
        currentMoveCandidates.clear();
        revalidate();
        repaint();
    }

    public void updateUI(Board board) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                Cell cell = getCell(r, c);
                Piece piece = board.getPieceAt(new Vector2(r, c));

                // clear the existing piece and add the new one
                cell.setMarked(false);
                cell.removePiecePanel();
                if (piece != null) {
                    cell.setPiecePanel(new PiecePanel(piece));
                }
            }
        }

        // mark the last move origin and final position
        Move lastMove = board.getLastMove();
        Vector2 moveOrigin = lastMove.getPreviousPos();
        Vector2 finalMovePosition = lastMove.getTargetedPos();
        Cell originCell = getCell(moveOrigin.x, moveOrigin.y);
        Cell targetCell = getCell(finalMovePosition.x, finalMovePosition.y);

        originCell.setMarked(true);
        targetCell.setMarked(true);

        revalidate();
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}
