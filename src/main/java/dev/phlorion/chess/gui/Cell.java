package dev.phlorion.chess.gui;

import javax.swing.*;
import java.awt.*;

public class Cell extends JPanel {
    private final int row;
    private final int col;
    private final Color originalColor;
    private PiecePanel piecePanel;

    private final Color lightColor = new Color(238, 238, 210);
    private final Color darkColor = new Color(118, 150, 85);

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.originalColor = (row + col) % 2 == 0 ? lightColor : darkColor;
        setBackground(originalColor);
        setLayout(new GridBagLayout());
    }

    public Color getOriginalColor() {
        return originalColor;
    }

    public void setPiecePanel(PiecePanel piecePanel) {
        this.piecePanel = piecePanel;
        add(piecePanel);
        revalidate();
        repaint();
    }
}
