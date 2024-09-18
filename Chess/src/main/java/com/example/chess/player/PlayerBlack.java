package com.example.chess.player;

import com.example.chess.board.Board;
import com.example.chess.move.Move;
import com.example.chess.piece.Piece;
import com.example.chess.piece.PiecesType;

import java.util.Collection;
import java.util.List;

public class PlayerBlack extends Player {
    public PlayerBlack(Board board, List<Move> potentialMoves, List<Move> legalMoves, boolean isCheckMated, boolean isStaleMated) {
        super(board, potentialMoves, legalMoves, isCheckMated, isStaleMated);
    }

    public PlayerBlack(Board board) {
        super(board);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public PiecesType getType() {
        return PiecesType.BLACK;
    }
}
