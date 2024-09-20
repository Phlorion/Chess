package com.example.scrap.other;

import com.example.ai.MiniMax;
import com.example.chess.board.Board;
import com.example.chess.move.Move;
import com.example.chess.player.Player;

import java.util.List;

public class MinimaxTest {
    public static void main(String[] args) {
        MiniMax mm = new MiniMax();

        Board board = new Board();
        board.setStartingPieces();
        Player whitePlayer = board.getWhitePlayer();
        Player blackPlayer = board.getBlackPlayer();
        whitePlayer.setLegalMoves(whitePlayer.calculateAllLegalMoves(board.getBoard()));
        blackPlayer.setLegalMoves(blackPlayer.calculateAllLegalMoves(board.getBoard()));
        System.out.println(board + "\n--------------------------------------------------------");

        int i = 0;
        Board copy = new Board(board);
        Player copyWhitePlayer = copy.getWhitePlayer();
        Player copyBlackPlayer = copy.getBlackPlayer();
        copyWhitePlayer.setLegalMoves(copyWhitePlayer.calculateAllLegalMoves(copy.getBoard()));
        copyBlackPlayer.setLegalMoves(copyBlackPlayer.calculateAllLegalMoves(copy.getBoard()));
        List<Move> copyLegalMoves = copy.getCurrentPlayer().getLegalMoves();
        for (Move m : copyLegalMoves) {
            copy = new Board(board);
            copy.makeMove(m);
            System.out.println(i + "\n" + copy);
            i++;
        }

        List<Move> legalMoves = board.getCurrentPlayer().getLegalMoves();
        board.makeMove(legalMoves.get(0));
        System.out.println(board + "\n--------------------------------------------------------");

        i = 0;
        copy = new Board(board);
        copyWhitePlayer = copy.getWhitePlayer();
        copyBlackPlayer = copy.getBlackPlayer();
        copyWhitePlayer.setLegalMoves(copyWhitePlayer.calculateAllLegalMoves(copy.getBoard()));
        copyBlackPlayer.setLegalMoves(copyBlackPlayer.calculateAllLegalMoves(copy.getBoard()));
        copyLegalMoves = copy.getCurrentPlayer().getLegalMoves();
        for (Move m : copyLegalMoves) {
            copy = new Board(board);
            copy.makeMove(m);
            System.out.println(i + "\n" + copy);
            i++;
        }

        legalMoves = board.getCurrentPlayer().getLegalMoves();
        board.makeMove(legalMoves.get(0));
        System.out.println(board + "\n--------------------------------------------------------");

        i = 0;
        copy = new Board(board);
        copyWhitePlayer = copy.getWhitePlayer();
        copyBlackPlayer = copy.getBlackPlayer();
        copyWhitePlayer.setLegalMoves(copyWhitePlayer.calculateAllLegalMoves(copy.getBoard()));
        copyBlackPlayer.setLegalMoves(copyBlackPlayer.calculateAllLegalMoves(copy.getBoard()));
        copyLegalMoves = copy.getCurrentPlayer().getLegalMoves();
        for (Move m : copyLegalMoves) {
            copy = new Board(board);
            copy.makeMove(m);
            System.out.println(i + "\n" + copy);
            i++;
        }
    }
}
