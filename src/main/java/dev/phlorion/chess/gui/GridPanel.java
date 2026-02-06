package dev.phlorion.chess.gui;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.misc.Vector2;
import dev.phlorion.chess.move.Move;
import dev.phlorion.chess.pieces.Piece;
import dev.phlorion.chess.pieces.PieceColor;
import dev.phlorion.chess.pieces.PieceKind;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class GridPanel extends JPanel {
    private final int width;
    private final int height;
    private final int rows;
    private final int columns;
    private Cell[] cells;

    private JLayeredPane layeredPane;
    private JPanel chessBoard;

    private ArrayList<Cell> currentMoveCandidates = new ArrayList<>();

    private PromotionOverlay activeOverlay;

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
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(width, height));
        chessBoard = new JPanel(new GridLayout(rows, columns));
        chessBoard.setBounds(0, 0, width, height);
        layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
        add(layeredPane, BorderLayout.CENTER);

        for (int i = 0; i < rows*columns; i++) {
            int row = i / columns;
            int col = i % rows;
            Piece piece = board.getPieceAt(new Vector2(row, col));
            Cell cell = new Cell(row, col, cellSize);
            if (piece != null)
                cell.setPiecePanel(new PiecePanel(piece));
            cells[i] = cell;
            chessBoard.add(cell);
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
        for (Cell cell : currentMoveCandidates) {
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

    public void showPromotionOverlay(Cell cell, PieceColor color, Consumer<PieceKind> onSelect) {
        int x, y;
        if (cell.getX() < width / 2) {
            x = cell.getX() + cell.getWidth();
        } else {
            x = cell.getX() - cell.getWidth();
        }

        if (cell.getY() < height / 2) {
            y = 0;
        } else {
            y = height - 4 * cell.getHeight();
        }

        activeOverlay = new PromotionOverlay(color, cells[0].getWidth(), onSelect);
        activeOverlay.setBounds(x, y, chessBoard.getWidth() / 8, chessBoard.getHeight() / 2);

        layeredPane.add(activeOverlay, JLayeredPane.POPUP_LAYER); // Add to the top layer
        revalidate();
        repaint();
    }

    public void hidePromotionOverlay() {
        if (activeOverlay != null) {
            layeredPane.remove(activeOverlay);
            activeOverlay = null;
            revalidate();
            repaint();
        }
    }

    public boolean isShowingOverlay() {
        return activeOverlay != null;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}
