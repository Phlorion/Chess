package dev.phlorion.chess.gui;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.pieces.Piece;
import dev.phlorion.chess.pieces.PieceColor;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Game {
    public final static String NAME = "Chess";
    public final static int WIDTH = 600;
    public final static int HEIGHT = 600;

    public static HashMap<Piece, PiecePanel> pieceToPanel;

    private static JFrame initializeFrame() {
        JFrame frame = new JFrame(NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        return frame;
    }

    public static void main(String[] args) {
        JFrame frame = Game.initializeFrame();

        Board board = new Board("src/main/resources/test1");
        for (Piece p : board.getPieces(PieceColor.WHITE))
            pieceToPanel.put(p, new PiecePanel(p));
        for (Piece p : board.getPieces(PieceColor.BLACK))
            pieceToPanel.put(p, new PiecePanel(p));

        GridPanel grid = new GridPanel(board);
        frame.add(grid);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
