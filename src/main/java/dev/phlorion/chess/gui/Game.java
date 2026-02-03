package dev.phlorion.chess.gui;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.misc.Vector2;
import dev.phlorion.chess.move.Move;
import dev.phlorion.chess.pieces.Piece;
import dev.phlorion.chess.pieces.PieceColor;
import dev.phlorion.chess.pieces.PieceKind;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Game {
    public final String name;
    public final int width;
    public final int height;

    public Game() {
        name = "Chess";
        width = 600;
        height = 600;
    }

    public Game(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    protected JFrame initializeFrame() {
        JFrame frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        return frame;
    }

    protected JPanel loadGame(JFrame frame, Board board) {
        board.setCurrentPlayer(PieceColor.WHITE);

        GridPanel grid = new GridPanel(board, width, height);
        frame.add(grid);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return grid;
    }
}
