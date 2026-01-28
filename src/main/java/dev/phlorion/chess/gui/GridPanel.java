package dev.phlorion.chess.gui;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.misc.Vector2;
import dev.phlorion.chess.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GridPanel extends JPanel {
    private final int rows;
    private final int columns;
    private Board board;

    public GridPanel(Board board) {
        this.board = board;
        this.rows = board.getBoardShape().x;
        this.columns = board.getBoardShape().y;

        initializeGrid();
    }

    private void initializeGrid() {
        setBackground(Color.white);
        setLayout(new GridLayout(rows, columns));
        for (int i = 0; i < rows*columns; i++) {
            int row = i / columns;
            int col = i % rows;
            Piece piece = board.getPieceAt(new Vector2(row, col));
            Cell cell = new Cell(row, col);
            cell.setPiecePanel(Game.pieceToPanel.get(piece));
            add(cell);
            cell.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    cell.setBackground(cell.getOriginalColor().brighter());
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    cell.setBackground(cell.getOriginalColor());
                }
            });
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Game.WIDTH, Game.HEIGHT);
    }
}
