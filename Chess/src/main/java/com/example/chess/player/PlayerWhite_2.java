package com.example.chess.player;

import com.example.chess.board.Board_2;
import com.example.chess.move.Move;
import com.example.chess.piece.Piece;
import com.example.chess.piece.PiecesType;

import java.util.Collection;

public class PlayerWhite_2 extends Player_2{
    public PlayerWhite_2(Board_2 board, Collection<Move> potentialMoves, Collection<Move> legalMoves, boolean isCheckMated, boolean isStaleMated) {
        super(board, potentialMoves, legalMoves, isCheckMated, isStaleMated);
    }

    public PlayerWhite_2(Board_2 board) {
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
