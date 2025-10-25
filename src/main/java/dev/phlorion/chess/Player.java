package dev.phlorion.chess;

import dev.phlorion.chess.misc.Vector2;
import dev.phlorion.chess.move.Move;
import dev.phlorion.chess.pieces.King;
import dev.phlorion.chess.pieces.Piece;
import dev.phlorion.chess.pieces.PieceColor;

import java.util.ArrayList;
import java.util.List;

public class Player {
    final PieceColor type;
    List<Piece> pieces;

    public Player(PieceColor playerType) {
        type = playerType;
    }

    public PieceColor getType() {
        return type;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public List<Move> getPlayerLegalMoves(Board board) {
        List<Move> allLegalsMoves = new ArrayList<>();
        for (Piece p : pieces) {
            allLegalsMoves.addAll(p.legalMoves(board));
        }

        return allLegalsMoves;
    }

    public boolean isKingChecked(Board board, Player opponent) {
        King king = (King) board.findKing(type);
        for (Move m : opponent.getPlayerLegalMoves(board)) {
            if (m.getTargetedPos().equals(king.getPosition())) {
                return true;
            }
        }

        return false;
    }

    public Move makeMove(Board board, Piece piece, Vector2 targetPosition) {
        Move attemptedMove = new Move(piece, targetPosition);

        for (Move m : getPlayerLegalMoves(board)) {
            if (m.equals(attemptedMove)) {
                m.execute(board);
                return m;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "Player " + type;
    }
}
