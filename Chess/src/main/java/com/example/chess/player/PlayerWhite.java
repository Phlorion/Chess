package com.example.chess.player;

import com.example.chess.board.Board;
//import com.example.chess.move.Move;
import com.example.chess.move.Move;
import com.example.chess.piece.Piece;
import com.example.chess.piece.PiecesType;

import java.util.Collection;
import java.util.List;

public class PlayerWhite extends Player {
    public PlayerWhite(Board board, List<Move> potentialMoves, List<Move> legalMoves, boolean isCheckMated, boolean isStaleMated) {
        super(board, potentialMoves, legalMoves, isCheckMated, isStaleMated);
    }

    public PlayerWhite(Board board) {
        super(board);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public PiecesType getType() {
        return PiecesType.WHITE;
    }
}
