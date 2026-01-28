package dev.phlorion.chess.gui;

import javax.swing.*;
import java.awt.*;

public class Cell extends JPanel {
    private final int row;
    private final int col;
    private final int cellSize;
    private final Color originalColor;
    private PiecePanel piecePanel;
    private boolean marked = false;

    private final Color lightColor = new Color(238, 238, 210);
    private final Color darkColor = new Color(118, 150, 85);

    public Cell(int row, int col, int cellSize) {
        this.row = row;
        this.col = col;
        this.cellSize = cellSize;
        this.originalColor = (row + col) % 2 == 0 ? lightColor : darkColor;
        setBackground(originalColor);
        setPreferredSize(new Dimension(cellSize, cellSize));
        setLayout(new GridBagLayout());
    }

    public Color getOriginalColor() {
        return originalColor;
    }

    public PiecePanel getPiecePanel() {
        return piecePanel;
    }

    public void setPiecePanel(PiecePanel piecePanel) {
        // remove previous
        if (this.piecePanel != null)
            remove(this.piecePanel);

        this.piecePanel = piecePanel;
        this.piecePanel.setPreferredSize(new Dimension(cellSize, cellSize));
        add(piecePanel);
        revalidate();
        repaint();
    }

    public void removePiecePanel() {
        if (piecePanel == null) return;
        remove(piecePanel);
        revalidate();
        repaint();
        this.piecePanel = null;
    }

    public void brighten() {
        setBackground(getOriginalColor().brighter());
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public boolean isMarked() {
        return marked;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 =  (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        if (marked && piecePanel != null) {
            g2.setColor(new Color(255, 251, 0, 80));
            g2.fillRect(0, 0, getWidth(), getHeight());
        } else if (marked) {
            g2.setColor(new Color(0, 0, 0, 120));
            g2.fillOval(cellSize / 3, cellSize / 3, cellSize / 3, cellSize / 3);
        }
    }

    @Override
    public String toString() {
        return "Cell @ " + row + "x" + col;
    }
}
