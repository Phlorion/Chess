package dev.phlorion.chess.pieces;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.misc.Vector2;
import dev.phlorion.chess.move.Move;

import java.util.List;

public abstract class Piece {
    Vector2 position;
    PieceKind pieceKind;
    PieceColor pieceColor;
    boolean hasMoved;

    public Piece() {
        position = new Vector2(0, 0);
        pieceColor = PieceColor.WHITE;
        hasMoved = false;
    }

    public Piece(int x, int y, PieceColor pieceColor) {
        position = new Vector2(x, y);
        this.pieceColor = pieceColor;
        hasMoved = false;
    }

    final public Vector2 getPosition() {
        return position;
    }

    final public void setPosition(Vector2 position) {
        this.position = position;
    }

    public PieceKind getPieceKind() {
        return pieceKind;
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public abstract List<Move> legalMoves(Board board);

    @Override
    public String toString() {
        if (pieceColor == PieceColor.WHITE)
            return pieceKind.pieceName;
        return pieceKind.pieceName.toLowerCase();
    }
}
