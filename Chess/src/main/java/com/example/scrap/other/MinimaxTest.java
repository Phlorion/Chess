package com.example.scrap.other;

import com.example.ai.MiniMax;
import com.example.chess.board.Board;
import com.example.chess.move.CaptureMove;
import com.example.chess.move.Move;
import com.example.chess.move.RegularMove;
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

        /*Board copy = new Board(board);

        List<Move> legalMoves = board.getCurrentPlayer().getLegalMoves();
        List<Move> temp = new ArrayList<>();
        for (Move m : legalMoves) {
            Move copyMove = null;
            if (m.getClass().getName().contains("RegularMove"))
                copyMove = new RegularMove(m, copy.getBoard());
            else if (m.getClass().getName().contains("CaptureMove"))
                copyMove = new CaptureMove(m, copy.getBoard());
            temp.add(copyMove);
        }
        for (Move m : legalMoves) System.out.print(m.getUid() + " ");
        System.out.println();
        for (Move m : temp) System.out.print(m.getUid() + " ");*/

        int i = 0;
        Board copy = new Board(board);
        List<Move> legalMoves = board.getCurrentPlayer().getLegalMoves();
        List<Move> copiedLegalMoves = new ArrayList<>();
        for (Move m : legalMoves) {
            Move copyMove = null;
            if (m.getClass().getName().contains("RegularMove"))
                copyMove = new RegularMove(m, copy.getBoard());
            else if (m.getClass().getName().contains("CaptureMove"))
                copyMove = new CaptureMove(m, copy.getBoard());
            copiedLegalMoves.add(copyMove);
        }
        for (Move m : copiedLegalMoves) {
            copy = new Board(board);
            copy.makeMove(m);
            System.out.println(i + "\n" + copy);
            i++;
        }

        board.makeMove(legalMoves.get(0));
        System.out.println(board + "\n--------------------------------------------------------");

        i = 0;
        copy = new Board(board);
        legalMoves = board.getCurrentPlayer().getLegalMoves();
        copiedLegalMoves = new ArrayList<>();
        for (Move m : legalMoves) {
            Move copyMove = null;
            if (m.getClass().getName().contains("RegularMove"))
                copyMove = new RegularMove(m, copy.getBoard());
            else if (m.getClass().getName().contains("CaptureMove"))
                copyMove = new CaptureMove(m, copy.getBoard());
            copiedLegalMoves.add(copyMove);
        }
        for (Move m : copiedLegalMoves) {
            copy = new Board(board);
            copy.makeMove(m);
            System.out.println(i + "\n" + copy);
            i++;
        }

        board.makeMove(legalMoves.get(0));
        System.out.println(board + "\n--------------------------------------------------------");

        i = 0;
        copy = new Board(board);
        legalMoves = board.getCurrentPlayer().getLegalMoves();
        copiedLegalMoves = new ArrayList<>();
        for (Move m : legalMoves) {
            Move copyMove = null;
            if (m.getClass().getName().contains("RegularMove"))
                copyMove = new RegularMove(m, copy.getBoard());
            else if (m.getClass().getName().contains("CaptureMove"))
                copyMove = new CaptureMove(m, copy.getBoard());
            copiedLegalMoves.add(copyMove);
        }
        for (Move m : copiedLegalMoves) {
            copy = new Board(board);
            copy.makeMove(m);
            System.out.println(i + "\n" + copy);
            i++;
        }
    }
}
