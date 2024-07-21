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
    private boolean isCheckMated;
    private boolean isStaleMated;
    private Move previousMove;

    public Player(Board board) {
        this.board = board;
        this.king = findKing(); // find king of player
        this.isCheckMated = false;
        this.isStaleMated = false;
        this.previousMove = null;
    }

    public King getKing() {
        return king;
    }

    public Move getPreviousMove() {
        return previousMove;
    }

    public void setPreviousMove(Move move) {
        previousMove = move;
    }

    /**
     * Get the legal moves of this player's pieces
     * @return The legal moves
     */
    public Collection<Move> getAllLegalMoves() {
        return this.legalMoves;
    }

    /**
     * Set the legal moves of this player's pieces
     * @param legalMoves The legal moves
     */
    public void setLegalMoves(Collection<Move> legalMoves) {
        this.legalMoves = legalMoves;
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
    public static List<Move> isAttackingOnTile(Tile tile, Collection<Move> moves) {
        List<Move> attacking = new ArrayList<>();
        for (Move m : moves) {
            if (tile.equals(m.getTo())) {
                attacking.add(m);
            }
        }
        return attacking;
    }
    /**
     * Get all the moves that are currently attacking this tile
     * @param tile The tile that is getting attacked
     * @param moves The moves we are examining
     * @return The moves from the total set of legal moves that are attacking this tile
     */
    public static List<Move> isAttackingOnTile_2(Tile tile, List<Move> moves) {
        List<Move> attacking = new ArrayList<>();
        for (Move m : moves) {
            if (tile.equals(m.getTo())) {
                attacking.add(m);
            }
        }
        return attacking;
    }

    public boolean isLegalMove(Move move) {
        return getAllLegalMoves().contains(move);
    }

    public Board makeMove(Move move) {
        if (isLegalMove(move)) {
            this.board = move.execute(this.board);
        }
        return this.board;
    }

    public boolean isChecked() {
        return !isAttackingOnTile(this.board.getTile(king.getPiecePosI(), king.getPiecePosJ()), opponent().getAllLegalMoves()).isEmpty();
    }

    public void setIsCheckMated(boolean isCheckMated) {
        this.isCheckMated = isCheckMated;
    }

    public boolean isCheckMated() {
        return this.isCheckMated;
    }

    public void setIsStaleMated(boolean isStaleMated) {
        this.isStaleMated = isStaleMated;
    }

    public boolean isStaleMated() {
        return this.isStaleMated;
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
        return Objects.equals(board, player.board) && Objects.equals(king, player.king);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, king);
    }
}
