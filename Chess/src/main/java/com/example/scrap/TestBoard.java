package com.example.scrap;

import com.example.chess.board.Board;
import com.example.chess.move.RegularMove;
import com.example.chess.piece.King;
import com.example.chess.piece.Knight;
import com.example.chess.piece.PiecesType;
import com.example.chess.piece.Rook;

public class TestBoard {

    public static void main(String args[]) {
        // pieces
        Knight nBlack = new Knight(1, 1, PiecesType.BLACK);
        King kBlack = new King(0, 1, PiecesType.BLACK);
        Rook rWhite = new Rook(6, 5, PiecesType.WHITE);
        King kWhite = new King(7, 7, PiecesType.WHITE);

        // create initial board
        Board.Builder builder = new Board.Builder();
        builder.setPiece(nBlack);
        builder.setPiece(kBlack);
        builder.setPiece(rWhite);
        builder.setPiece(kWhite);
        builder.setMoveMaker(PiecesType.BLACK);
        Board initBoard = builder.build();

        System.out.println(initBoard);

        // check assigned with =
        Board newBoard1 = new Board(initBoard);
        System.out.println(initBoard.equals(newBoard1));
        initBoard = initBoard.getCurrentPlayer().makeMove(new RegularMove(initBoard, initBoard.getTile(1, 1), initBoard.getTile(3, 2), nBlack));
        System.out.println("--------------------------AFTER MOVE--------------------------");
        System.out.println("Initial board:");
        System.out.println(initBoard);
        System.out.println("\nNew board:");
        System.out.println(newBoard1);

    }

}
