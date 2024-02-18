package com.example.chess.board;

import com.example.chess.piece.Piece;
import com.example.chess.piece.PiecesType;

import java.util.Objects;

public class Tile {
    int i;
    int j;
    Piece piece;

    public Tile() {
        i = j = 0;
        piece = null;
    }

    public Tile(int i, int j, Piece piece) {
        this.i = i; this.j = j;
        this.piece = piece;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean isEmpty() {
        return piece == null;
    }

    @Override
    public String toString() {
        if (!isEmpty()) {
            return getPiece().getType().equals(PiecesType.BLACK) ? getPiece().toString().toLowerCase() : getPiece().toString();
        }
        return "-";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return i == tile.i && j == tile.j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }
}
