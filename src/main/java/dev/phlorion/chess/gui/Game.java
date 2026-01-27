package dev.phlorion.chess.gui;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.misc.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game {
    public final static String NAME = "Chess";
    public final static int WIDTH = 800;
    public final static int HEIGHT = 800;

    private static JFrame initializeFrame() {
        JFrame frame = new  JFrame(NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        return frame;
    }

    public static void main(String[] args) {
        JFrame frame = Game.initializeFrame();

        Board board = new Board("src/main/resources/test1");

        GridPanel grid = new GridPanel(board);
        frame.add(grid);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
