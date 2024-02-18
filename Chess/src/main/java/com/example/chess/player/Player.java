package com.example.chess.player;

import com.example.chess.board.Board;
import com.example.chess.board.Tile;
import com.example.chess.move.Move;
import com.example.chess.piece.King;
import com.example.chess.piece.Piece;
import com.example.chess.piece.PiecesType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public abstract class Player {
    protected Board board;
    protected King king;
    protected Collection<Move> legalMoves;
    protected Collection<Move> opponentMoves;
    private boolean isInCheck;

    public Player(Board board, Collection<Move> legalMoves, Collection<Move> opponentMoves) {
        this.board = board;
        this.legalMoves = legalMoves;
        this.opponentMoves = opponentMoves;
        this.king = findKing(); // find king of player
        if (this.king != null)
            this.isInCheck = !isAttackingOnTile(this.board.getTile(king.getPiecePosI(), king.getPiecePosJ()), opponentMoves).isEmpty();
    }

    /**
     * Get the piece king of this player
     * @return The piece
     */
    private King findKing() {
        for (Piece p : getActivePieces()) {
            if (p.getPieceKind().equals(Piece.PieceKind.KING)) {
                return (King) p;
            }
        }
        return null;
    }

    /**
     * Get all the moves that are currently attacking this tile
     * @param tile The tile that is getting attacked
     * @param moves The moves we are examining
     * @return The moves from the total set of legal moves that are attacking this tile
     */
    private List<Move> isAttackingOnTile(Tile tile, Collection<Move> moves) {
        List<Move> attacking = new ArrayList<>();
        for (Move m : moves) {
            if (tile.equals(m.getTo())) {
                attacking.add(m);
            }
        }
        return attacking;
    }

    public boolean isLegalMove(Move move) {
        return legalMoves.contains(move);
    }

    // TODO: implement conditions for checking and other rules
    public Board makeMove(Move move) {
        if (isLegalMove(move)) {
            this.board = move.execute();
        }
        return this.board;
    }

    public boolean isChecked() {
        return isInCheck;
    }

    public boolean isCheckMated() {
        List<Move> kingLegalMoves = king.legalMoves(this.board);
        for (Move m : kingLegalMoves) {
            if (isChecked() && !isAttackingOnTile(m.getTo(), opponentMoves).isEmpty()) {
                kingLegalMoves.remove(m);
            }
        }
        return kingLegalMoves.isEmpty();
    }

    // TODO: implement below methods
    public boolean isStaleMated() {
        return false;
    }

    public boolean isCastled() {
        return false;
    }

    /**
     * Get all the pieces of this player that are still in the game
     * @return The pieces
     */
    public abstract Collection<Piece> getActivePieces();

    /**
     * Get the type of the player (black or white)
     * @return Player type
     */
    public abstract PiecesType getType();

    /**
     * Get the opponent player
     * @return The opponent
     */
    public abstract Player opponent();

    @Override
    public String toString() {
        return getType().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return isInCheck == player.isInCheck && Objects.equals(board, player.board) && Objects.equals(king, player.king) && Objects.equals(legalMoves, player.legalMoves);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, king, legalMoves, isInCheck);
    }
}
