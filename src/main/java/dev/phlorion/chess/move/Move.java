package dev.phlorion.chess.move;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.misc.Vector2;
import dev.phlorion.chess.pieces.Piece;

import java.util.Objects;

public class Move {
    Vector2 previousPos;
    Vector2 targetedPos;
    boolean firstMoveExecuted;
    Piece piece;
    Piece capturedPiece;

    public Move(Piece piece, Vector2 targetedPosition) {
        this.piece = piece;
        capturedPiece = null;
        firstMoveExecuted = false; // keep track if this move moved the piece for the first time
        previousPos = piece.getPosition();
        targetedPos = targetedPosition;
    }

    public void execute(Board board) {
        // check if we captured a piece
        capturedPiece = board.getPieceAt(targetedPos);

        piece.setPosition(targetedPos);
        if (!piece.hasMoved())  {
            piece.setHasMoved(true); // if piece's first move
            firstMoveExecuted = true;
        }

        board.setOnBoard(previousPos, null);
        board.setOnBoard(targetedPos, piece);

        // set pieces on both players
        board.getCurrentPlayer().setPieces(board.getPieces(board.getCurrentPlayer().getType()));
        board.getOpponentPlayer().setPieces(board.getPieces(board.getOpponentPlayer().getType()));
    }

    public void redo(Board board) {
        piece.setPosition(previousPos);
        if (capturedPiece != null) capturedPiece.setPosition(targetedPos);
        if (firstMoveExecuted) { // this means that the previous status of the piece was hasMoved: false
            piece.setHasMoved(false);
        }

        board.setOnBoard(previousPos, piece);
        board.setOnBoard(targetedPos, capturedPiece);

        // set pieces on both players
        board.getCurrentPlayer().setPieces(board.getPieces(board.getCurrentPlayer().getType()));
        board.getOpponentPlayer().setPieces(board.getPieces(board.getOpponentPlayer().getType()));
    }

    public Vector2 getTargetedPos() {
        return targetedPos;
    }

    public Piece getPiece() {
        return piece;
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
