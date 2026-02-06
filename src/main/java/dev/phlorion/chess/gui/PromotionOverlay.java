package dev.phlorion.chess.gui;

import dev.phlorion.chess.pieces.PieceColor;
import dev.phlorion.chess.pieces.PieceKind;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.function.Consumer;

public class PromotionOverlay extends JPanel {
    int promotionCellSize;

    public PromotionOverlay(PieceColor color, int promotionCellSize, Consumer<PieceKind> onSelect) {
        setLayout(new GridBagLayout());

        this.promotionCellSize = promotionCellSize;

        JPanel selectionBox = new JPanel(new GridLayout(4, 1, 0, 0));
        selectionBox.setOpaque(false);
        selectionBox.setBackground(new Color(255, 255, 255, 255));

        addButton(PieceKind.QUEEN, color, onSelect, selectionBox);
        addButton(PieceKind.ROOK, color, onSelect, selectionBox);
        addButton(PieceKind.BISHOP, color, onSelect, selectionBox);
        addButton(PieceKind.KNIGHT, color, onSelect, selectionBox);

        add(selectionBox);
    }

    private void addButton(PieceKind kind, PieceColor color, Consumer<PieceKind> onSelect, JPanel selectionBox) {
        String resourceString = null;
        switch (kind) {
            case QUEEN:
                if (color == PieceColor.WHITE) resourceString = "qlt60";
                else resourceString = "qdt60";
                break;
            case ROOK:
                if (color == PieceColor.WHITE) resourceString = "rlt60";
                else resourceString = "rdt60";
                break;
            case KNIGHT:
                if (color == PieceColor.WHITE) resourceString = "nlt60";
                else resourceString = "ndt60";
                break;
            case BISHOP:
                if (color == PieceColor.WHITE) resourceString = "blt60";
                else resourceString = "bdt60";
                break;
            default:
                break;
        }

        String path = "/pieces/Chess_" + resourceString + ".png";
        JButton button = new JButton(getResizedIcon(path, promotionCellSize, promotionCellSize));
        button.setContentAreaFilled(false); // Removes the default grey background
        button.setBorderPainted(false);     // Removes the button border
        button.setFocusPainted(false);      // Removes the "ring" when clicked
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addActionListener(e -> onSelect.accept(kind));
        selectionBox.add(button);
    }

    private ImageIcon getResizedIcon(String resourcePath, int width, int height) {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(resourcePath)));

        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width - 10, height - 10, Image.SCALE_SMOOTH);

        return new ImageIcon(resizedImg);
    }
}
