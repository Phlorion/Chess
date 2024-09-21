package com.example.chess.move;

//import com.example.chess.board.Board;
import com.example.chess.board.Board;
import com.example.chess.board.Tile;
import com.example.chess.piece.Piece;

import java.util.Arrays;
import java.util.Objects;

public abstract class Move {
    Tile from;
    Tile to;
    Piece piece;
    Tile[] board;

    private static int counter = 0;
    private final int uid;

    //Constructor
    public Move(Tile from, Tile to, Piece piece, Tile[] board) {
        counter++;
        uid = counter;
        this.from = from;
        this.to = to;
        this.piece = piece;
        this.board = board;
    }

    public Move(Move move, Tile[] board) {
        this.board = board;
        this.piece = this.board[Board.NUM_TILES_PER_ROW*move.getFrom().getI() + move.getFrom().getJ()].getPiece();
        this.from = this.board[Board.NUM_TILES_PER_ROW*move.getFrom().getI() + move.getFrom().getJ()];
        this.to = this.board[Board.NUM_TILES_PER_ROW*move.getTo().getI() + move.getTo().getJ()];
        uid = move.getUid();
    }

    //Getters && Setters


    public int getUid() {
        return uid;
    }

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
        Move move2 = (Move) o;
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
