package com.example.chess.move;

import com.example.chess.board.Board;
import com.example.chess.piece.Piece;
import com.example.chess.board.Tile;
import com.example.chess.player.Player;

import java.util.Comparator;
import java.util.Objects;

public abstract class Move {
    Tile from;
    Tile to;
    Piece piece;
    Board board;

    public Move(Board board, Tile from, Tile to, Piece piece) {
        this.board = board;
        this.from = from;
        this.to = to;
        this.piece = piece;
    }

    /**
     * Executes the move. This means a new updated board is made.
     * @return The new board
     */
    public abstract Board execute(Board board);
    public abstract Board fakeExecute(Board board);

    public abstract Board reverseFakeExecute(Board board);

    public Tile getFrom() {
        return from;
    }

    public void setFrom(Tile from) {
        this.from = from;
    }

    public Tile getTo() {
        return to;
    }

    public void setTo(Tile to) {
        this.to = to;
    }

    public Piece getPiece() {
        return piece;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return Objects.equals(from, move.from) && Objects.equals(to, move.to) && Objects.equals(piece, move.piece) && Objects.equals(board, move.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, piece, board);
    }

    @Override
    public String toString() {
        return "Piece: " + piece + " From: (" + from.getI() + ", " + from.getJ() + ") To: (" + to.getI() + ", " + to.getJ() + ")";
    }

    public static class CompareMoves implements Comparator<Move> {

        @Override
        public int compare(Move o1, Move o2) {
            return (Board.NUM_TILES_PER_ROW * o1.getTo().getI() + o1.getTo().getJ())
                    - (Board.NUM_TILES_PER_ROW * o2.getTo().getI() + o2.getTo().getJ());
        }
    }
}
