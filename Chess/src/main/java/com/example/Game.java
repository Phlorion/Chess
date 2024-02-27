package com.example;

import com.example.chess.board.Board;
import com.example.chess.piece.*;
import com.example.gui.Table;
import javafx.application.Application;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Game extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Board board = Board.createStandardBoard();

        Board.Builder builder = new Board.Builder();
        builder.setPiece(new King(0, 4, PiecesType.BLACK));
        builder.setPiece(new King(7, 4, PiecesType.WHITE));
        builder.setPiece(new Rook(0, 0, PiecesType.BLACK));
        builder.setPiece(new Rook(0, 7, PiecesType.BLACK));
        builder.setPiece(new Queen(4, 5, PiecesType.WHITE));
        builder.setMoveMaker(PiecesType.WHITE);
        Board board = builder.build();

        System.out.println(board);
        System.out.println(board.getCurrentPlayer() + "'s turn");

        Table table = new Table(stage);
        table.setBoard(board);
        table.setPieces();
    }
}
