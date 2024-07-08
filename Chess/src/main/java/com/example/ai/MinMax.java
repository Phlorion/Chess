package com.example.ai;

import com.example.chess.board.Board;
import com.example.chess.move.Move;
import com.example.chess.piece.King;
import com.example.chess.piece.Knight;
import com.example.chess.piece.PiecesType;
import com.example.chess.piece.Rook;

public class MinMax {

    private int depth;
    private Board board;

    public MinMax() {depth = 10;}
    public MinMax(int depth) {this.depth = depth+1;}//Remove "+1"

    public Move run() {
        // create tree
        State initState = new State(board);
        System.out.println(board);
        for (Board b : initState.getChildred()) {
            System.out.print(b + "\t");
        }
        return null;
    }

    public void setBoard(Board board) {
        this.board = board;
    }


    public static void main(String[] args) {
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

        // MinMax
        MinMax minMax = new MinMax();
        minMax.setBoard(initBoard);
        minMax.run();
    }
}
