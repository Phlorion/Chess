package dev.phlorion.chess.gui;

import dev.phlorion.chess.pieces.Piece;
import dev.phlorion.chess.pieces.PieceColor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

public class PiecePanel extends JPanel {
    Piece pieceReference;
    private BufferedImage image;

    public PiecePanel(Piece pieceReference) {
        this.pieceReference = pieceReference;
        mapImage();
        setOpaque(false);
    }

    public Piece getPieceReference() {
        return pieceReference;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }

    private void mapImage() {
        String imagePath = "";
        switch (pieceReference.getPieceKind()) {
            case PAWN:
                imagePath = pieceReference.getPieceColor() == PieceColor.WHITE ? "Chess_plt60.png" : "Chess_pdt60.png";
                break;
            case ROOK:
                imagePath = pieceReference.getPieceColor() == PieceColor.WHITE ? "Chess_rlt60.png" : "Chess_rdt60.png";
                break;
            case KNIGHT:
                imagePath = pieceReference.getPieceColor() == PieceColor.WHITE ? "Chess_nlt60.png" : "Chess_ndt60.png";
                break;
            case BISHOP:
                imagePath = pieceReference.getPieceColor() == PieceColor.WHITE ? "Chess_blt60.png" : "Chess_bdt60.png";
                break;
            case QUEEN:
                imagePath = pieceReference.getPieceColor() == PieceColor.WHITE ? "Chess_qlt60.png" : "Chess_qdt60.png";
                break;
            case KING:
                imagePath = pieceReference.getPieceColor() == PieceColor.WHITE ? "Chess_klt60.png" : "Chess_kdt60.png";
                break;
            default:
                break;
        }

        if (imagePath.isEmpty()) return;

        try {
            URL resource = this.getClass().getResource("/pieces/" + imagePath);
            if (resource != null) {
                this.image = ImageIO.read(resource);
            } else {
                System.err.println("Could not find image: " + "pieces/" + imagePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
