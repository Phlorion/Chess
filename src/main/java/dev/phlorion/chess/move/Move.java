package dev.phlorion.chess.move;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.misc.Vector2;
import dev.phlorion.chess.pieces.Piece;

import java.util.Objects;

public class Move {
    Vector2 previousPos;
    Vector2 targetedPos;
    Piece piece;

    public Move(Piece piece, Vector2 targetedPosition) {
        this.piece = piece;
        previousPos = piece.getPosition();
        targetedPos = targetedPosition;
    }

    public void execute(Board board) {
        piece.setPosition(targetedPos);
        if (!piece.hasMoved()) piece.setHasMoved(true); // if piece's first move

        board.setOnBoard(previousPos, null);
        board.setOnBoard(targetedPos, piece);
    }

    public Vector2 getTargetedPos() {
        return targetedPos;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return Objects.equals(previousPos, move.previousPos) && Objects.equals(targetedPos, move.targetedPos) && Objects.equals(piece, move.piece);
    }

    @Override
    public int hashCode() {
        return Objects.hash(previousPos, targetedPos, piece);
    }

    @Override
    public String toString() {
        return "Move{" +
                "previousPos=" + previousPos +
                ", targetedPos=" + targetedPos +
                ", piece=" + piece +
                '}';
    }
}
