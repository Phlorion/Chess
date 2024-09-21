package com.example.scrap.other;

import com.example.ai.MiniMax;
import com.example.ai.State;
import com.example.chess.board.Board;
import com.example.chess.move.CaptureMove;
import com.example.chess.move.Castle;
import com.example.chess.move.Move;
import com.example.chess.move.RegularMove;
import com.example.chess.piece.*;
import com.example.chess.player.Player;

import java.util.ArrayList;
import java.util.List;

public class MinimaxTest {
    public static void main(String[] args) {
        MiniMax mm = new MiniMax();

        Board board = new Board();
        board.setStartingPieces();
        /*board.setPieceOnBoard(new King(PiecesType.BLACK),0,4);
        board.setPieceOnBoard(new King(PiecesType.WHITE),7,4);
        board.setPieceOnBoard(new Pawn(PiecesType.BLACK),1,2);
        board.setPieceOnBoard(new Pawn(PiecesType.WHITE),6,5);*/

        Player whitePlayer = board.getWhitePlayer();
        Player blackPlayer = board.getBlackPlayer();
        /*board.setBlackPieces(board.findAllActivePieces(board.getBoard(), PiecesType.BLACK));
        board.setWhitePieces(board.findAllActivePieces(board.getBoard(), PiecesType.WHITE));*/
        whitePlayer.setPotentialMoves(whitePlayer.calculateAllPotentialMoves(board.getBoard()));
        blackPlayer.setPotentialMoves(blackPlayer.calculateAllPotentialMoves(board.getBoard()));
        whitePlayer.setLegalMoves(whitePlayer.calculateAllLegalMoves(board.getBoard()));
        blackPlayer.setLegalMoves(blackPlayer.calculateAllLegalMoves(board.getBoard()));
        System.out.println(board + "\n--------------------------------------------------------");

        Board copy = new Board(board);
        System.out.println(copy);
        State start = new State(copy);
        // call minimax
        mm.buildTree(start, 0);


    }
}
