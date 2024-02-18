package com.example.chess.player;

import com.example.chess.board.Board;
import com.example.chess.move.Move;
import com.example.chess.piece.Piece;
import com.example.chess.piece.PiecesType;

import java.util.Collection;

public class PlayerWhite extends Player {
    public PlayerWhite(Board board, Collection<Move> legalMoves, Collection<Move> opponentMoves) {
        super(board, legalMoves, opponentMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public PiecesType getType() {
        return PiecesType.WHITE;
    }

    @Override
    public Player opponent() {
        return this.board.getPlayerBlack();
    }
}
