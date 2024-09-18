package com.example.chess.piece;

//import com.example.chess.board.Board;
import com.example.chess.board.Board;
//import com.example.chess.move.Move;
import com.example.chess.board.Tile;
import com.example.chess.move.Move;

import java.util.List;
import java.util.Objects;

public abstract class Piece {
//    int piecePosI;
//    int piecePosJ;
    private static int counter = 0;
    private final int uid;
    PiecesType type; // black or white
    PieceKind pieceKind;
    boolean hasMoved;
    int moves;

    public Piece(/*int pos_i, int pos_j,*/ PiecesType type) {
//        this.piecePosI = pos_i;
//        this.piecePosJ = pos_j;
        this.uid = counter;
        counter = counter +1;
        this.type = type;
        this.pieceKind = PieceKind.PAWN;
        this.hasMoved = false;
        this.moves = 0;
    }
    public Piece(Piece otherPiece){
//        this.piecePosI = otherPiece.getPiecePosI();
//        this.piecePosJ = otherPiece.getPiecePosJ();
        this.uid = otherPiece.getUid();
        this.type = otherPiece.getType();
        this.pieceKind = PieceKind.PAWN;
        this.hasMoved = otherPiece.hasMoved();
        this.moves = otherPiece.getMoves();
    }

    /**
     * Calculate all the legal moves for a piece
     * @param board The board in which the piece is placed
     * @return All the legal moves of the piece
     */
//    public abstract List<Move> legalMoves(final Board board);
    public abstract List<Move> calculatePotentialMoves(final Tile[] board);

    public PiecesType getType() {
        return type;
    }

    public void setType(PiecesType type) {
        this.type = type;
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

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public int getUid() {
        return uid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return this.uid == piece.getUid() && type == piece.type && pieceKind == piece.pieceKind;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uid, type, pieceKind);
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

    //Some methods that assist the calculateAllPotentialMoves and calculateAllLegalMoves
    protected Tile getTileByPiece(Tile[] board, Piece piece){
        for (Tile t : board) {
            if (t.getPiece() != null && t.getPiece().equals(piece)) {
                return t;
            }
        }
        return null;
    }
    protected boolean isValidCoordinate(int i, int j) {
        return !(i < 0 || i > Board.NUM_TILES_PER_ROW-1 || j < 0 || j > Board.NUM_TILES_PER_COL-1);
    }
}
