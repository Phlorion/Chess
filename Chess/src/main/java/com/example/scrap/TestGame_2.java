package com.example.scrap;

import com.example.chess.board.Board_2;
import com.example.chess.move.Move_2;

import java.util.Arrays;

public class TestGame_2 {
    public static void main(String[] args) {
        Board_2 board = new Board_2();
        board.setStartingPieces();
        board.getWhitePlayer().setPotentialMoves(board.getWhitePlayer().calculateAllPotentialMoves());
        board.getBlackPlayer().setPotentialMoves(board.getBlackPlayer().calculateAllPotentialMoves());
        board.getWhitePlayer().setLegalMoves(board.getWhitePlayer().calculateAllLegalMoves());
        board.getBlackPlayer().setLegalMoves(board.getBlackPlayer().calculateAllLegalMoves());
        System.out.println(board);
    }
}
