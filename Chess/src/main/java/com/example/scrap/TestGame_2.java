package com.example.scrap;

import com.example.chess.board.Board_2;

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
        System.out.println(Arrays.toString(board.getWhitePlayer().getLegalMoves().get(0).getBoard()));
        System.out.println("from I " + board.getWhitePlayer().getLegalMoves().get(0).getFrom().getI());
        System.out.println("from J"+board.getWhitePlayer().getLegalMoves().get(0).getFrom().getJ());
        System.out.println("to I"+board.getWhitePlayer().getLegalMoves().get(0).getTo().getI());
        System.out.println("to J"+board.getWhitePlayer().getLegalMoves().get(0).getTo().getJ());
        System.out.println(board.getWhitePlayer().getLegalMoves().get(0).getPiece());
    }
}
