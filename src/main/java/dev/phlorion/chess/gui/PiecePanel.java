package dev.phlorion.chess.gui;

import dev.phlorion.chess.pieces.Piece;
import dev.phlorion.chess.pieces.PieceColor;
import dev.phlorion.chess.pieces.PieceKind;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class PiecePanel extends JPanel {
    Piece pieceReference;
    private BufferedImage image;

    public PiecePanel(Piece pieceReference) {
        this.pieceReference = pieceReference;
        setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    private void mapImage() {
        String imagePath;

    }
}
