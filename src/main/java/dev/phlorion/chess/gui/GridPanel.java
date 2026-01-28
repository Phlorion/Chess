package dev.phlorion.chess.gui;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.misc.Vector2;
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
            cell.setMarked(true);
        }
        revalidate();
        repaint();
    }

    public void flushCandidates() {
        for  (Cell cell : currentMoveCandidates) {
            cell.setMarked(false);
        }
        currentMoveCandidates.clear();
        revalidate();
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}
