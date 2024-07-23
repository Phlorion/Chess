package com.example.chess.player;

import com.example.chess.board.Board;
import com.example.chess.board.Board_2;
import com.example.chess.move.Move;
import com.example.chess.move.Move_2;
import com.example.chess.piece.Piece;
import com.example.chess.piece.PiecesType;

import java.util.Collection;
import java.util.List;

public class PlayerBlack_2 extends Player_2{
    public PlayerBlack_2(Board_2 board, List<Move_2> potentialMoves, List<Move_2> legalMoves, boolean isCheckMated, boolean isStaleMated) {
        super(board, potentialMoves, legalMoves, isCheckMated, isStaleMated);
    }

    public PlayerBlack_2(Board_2 board) {
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
