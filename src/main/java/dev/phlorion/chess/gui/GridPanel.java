package dev.phlorion.chess.gui;

import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel {
    private int cellSize;
    private int rows;
    private int columns;

    public GridPanel(int rows, int columns, int cellSize) {
        this.rows = rows;
        this.columns = columns;
        this.cellSize = cellSize;
        setBackground(Color.black);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;
    }
}
