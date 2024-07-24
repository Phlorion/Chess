package com.example.chess.move;

import com.example.chess.board.Board;
import com.example.chess.board.Board_2;
import com.example.chess.board.Tile;
import com.example.chess.piece.Piece;

import java.util.Arrays;
import java.util.Objects;

public abstract class Move_2 {
    Tile from;
    Tile to;
    Piece piece;
    Tile[] board;

    //Constructor
    public Move_2(Tile from, Tile to, Piece piece, Tile[] board) {
        this.from = from;
        this.to = to;
        this.piece = piece;
        this.board = board;
    }

    //Getters && Setters

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

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Tile[] getBoard() {
        return board;
    }

    public void setBoard(Tile[] board) {
        this.board = board;
    }

    //Getters && Setters -END

    //Methods

    //Like the execute method in Move
    /**
     * Make the move by returning a Tile[]. Does not mean that the original board is updated.
     * @return The new tiles.
     * */
    public abstract Tile[] makeMoveInBoard();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move_2 move2 = (Move_2) o;
        return Objects.equals(from, move2.from) && Objects.equals(to, move2.to) && Objects.equals(piece, move2.piece) && Arrays.equals(board, move2.board);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(from, to, piece);
        result = 31 * result + Arrays.hashCode(board);
        return result;
    }

    @Override
    public String toString() {
        return "from ("+from.getI()+", "+from.getJ()+") - to ("+to.getI()+", "+to.getJ()+") - Piece "+piece;
    }
    //What is the CompareMoves method in Move?

    //Methods - END
}
