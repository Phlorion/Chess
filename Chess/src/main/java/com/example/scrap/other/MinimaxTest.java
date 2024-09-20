package com.example.scrap.other;

import com.example.ai.MiniMax;
import com.example.ai.State;
import com.example.chess.board.Board;
import com.example.chess.move.Move;
import com.example.chess.player.Player;

import java.util.ArrayList;
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
        List<Move> legalMoves = board.getCurrentPlayer().getLegalMoves();
        for (Move m : legalMoves) {
            Board copy = new Board(board);
            copy.makeMove(m);
            System.out.println(i + "\n" + copy);
            i++;
        }

        board.makeMove(legalMoves.get(0));

        i = 0;
        legalMoves = board.getCurrentPlayer().getLegalMoves();
        for (Move m : legalMoves) {
            Board copy = new Board(board);
            copy.makeMove(m);
            System.out.println(i + "\n" + copy);
            i++;
        }
    }
}
