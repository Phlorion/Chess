package dev.phlorion.chess.gui;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.misc.Vector2;

import javax.swing.*;
import java.awt.*;

public class Game {
    public final static String NAME = "Chess";
    public final static int WIDTH = 800;
    public final static int HEIGHT = 600;

    private static JFrame initializeFrame() {
        JFrame frame = new  JFrame(NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);

        return frame;
    }

    public static void main(String[] args) {
        JFrame frame = Game.initializeFrame();

        Board board = new Board("src/main/resources/test1");
        Vector2 boardShape = board.getBoardShape();

        GridPanel grid = new GridPanel(boardShape.x, boardShape.y, 20);
        frame.setContentPane(grid);
        frame.setVisible(true);
    }
}
