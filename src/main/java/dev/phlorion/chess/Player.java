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
            List<Move> pieceLegalMoves = p.legalMoves(board);
            pieceLegalMoves.removeIf(m -> !isKingSafeAfterMove(board, m)); // make sure the move doesn't leave the king in check
            allLegalsMoves.addAll(pieceLegalMoves);
        }

        return allLegalsMoves;
    }

    public boolean isKingChecked(Board board, Player opponent) {
        Vector2 kingPos = board.findKingPositionOnBoard(this.type);
        return board.isPositionAttacked(opponent, kingPos);
    }

    private boolean isKingSafeAfterMove(Board board, Move move) {
        // fake execute the move
        move.execute(board);

        // check if the king is exposed
        Player opponent = board.getOpponentPlayer();
        Vector2 kingPos = board.findKingPositionOnBoard(this.type);
        boolean isKingSafe = !board.isPositionAttacked(opponent, kingPos);

        // redo the move execution
        move.redo(board);

        return isKingSafe;
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
