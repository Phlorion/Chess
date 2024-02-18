package com.example.chess.piece;

import com.example.chess.board.Board;
import com.example.chess.move.Move;

import java.util.List;
import java.util.Objects;

public abstract class Piece {
    int piecePosI;
    int piecePosJ;
    PiecesType type; // black or white
    PieceKind pieceKind;
    boolean hasMoved;

    public Piece() {
        piecePosI = piecePosJ = 0;
        this.type = PiecesType.WHITE;
        this.pieceKind = PieceKind.PAWN;
        hasMoved = false;
    }

    public Piece(int pos_i, int pos_j, PiecesType type) {
        piecePosI = pos_i;
        piecePosJ = pos_j;
        this.type = type;
        this.pieceKind = PieceKind.PAWN;
        hasMoved = false;
    }

    /**
     * Calculate all the legal moves for a piece
     * @param board The board in which the piece is placed
     * @return All the legal moves of the piece
     */
    public abstract List<Move> legalMoves(final Board board);

    public PiecesType getType() {
        return type;
    }

    public void setType(PiecesType type) {
        this.type = type;
    }

    public int getPiecePosI() {
        return piecePosI;
    }

    public void setPiecePosI(int piecePosI) {
        this.piecePosI = piecePosI;
    }

    public int getPiecePosJ() {
        return piecePosJ;
    }

    public void setPiecePosJ(int piecePosJ) {
        this.piecePosJ = piecePosJ;
    }

    public PieceKind getPieceKind() {
        return pieceKind;
    }

    public void setPieceKind(PieceKind pieceKind) {
        this.pieceKind = pieceKind;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return piecePosI == piece.piecePosI && piecePosJ == piece.piecePosJ && type == piece.type && pieceKind == piece.pieceKind;
    }

    @Override
    public int hashCode() {
        return Objects.hash(piecePosI, piecePosJ, type, pieceKind);
    }

    public enum PieceKind {
        PAWN("P"),
        KNIGHT("N"),
        BISHOP("B"),
        ROOK("R"),
        QUEEN("Q"),
        KING("K");

        private final String pieceName;

        PieceKind(String pieceName) {
            this.pieceName = pieceName;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }
    }
}
