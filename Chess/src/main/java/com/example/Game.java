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
        builder.setPiece(new Bishop(4, 6, PiecesType.BLACK));
        builder.setPiece(new Knight(6, 4, PiecesType.BLACK));
        builder.setPiece(new Rook(7, 0, PiecesType.WHITE));
        builder.setPiece(new Pawn(6, 0, PiecesType.WHITE));
        builder.setPiece(new Pawn(6, 3, PiecesType.BLACK));
        builder.setMoveMaker(PiecesType.WHITE);
        Board board = builder.build();

        System.out.println(board);
        System.out.println(board.getCurrentPlayer() + "'s turn");

        Table table = new Table(stage);
        table.setBoard(board);
        table.setPieces();
    }
}
