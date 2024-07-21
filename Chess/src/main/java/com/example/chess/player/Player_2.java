package com.example.chess.player;

import com.example.chess.board.Board;
import com.example.chess.board.Board_2;
import com.example.chess.board.Tile;
import com.example.chess.move.Move;
import com.example.chess.move.Move_2;
import com.example.chess.piece.King;
import com.example.chess.piece.Piece;
import com.example.chess.piece.PiecesType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public abstract class Player_2 {
    //Variables
    protected Board_2 board;
    protected Collection<Move> potentialMoves;
    protected Collection<Move> legalMoves;
    private boolean isCheckMated;
    private boolean isStaleMated;
    //Constructors

    public Player_2(Board_2 board, Collection<Move> potentialMoves, Collection<Move> legalMoves, boolean isCheckMated, boolean isStaleMated) {
        this.board = board;
        this.potentialMoves = potentialMoves;
        this.legalMoves = legalMoves;
        this.isCheckMated = isCheckMated;
        this.isStaleMated = isStaleMated;
    }

    public Player_2(Board_2 board) {
        this.board = board;
        this.isCheckMated = false;
        this.isStaleMated = false;
    }
    //Getters && Setters
    public Board_2 getBoard() {
        return board;
    }

    public void setBoard(Board_2 board) {
        this.board = board;
    }

    public Collection<Move> getPotentialMoves() {
        return potentialMoves;
    }

    public void setPotentialMoves(Collection<Move> potentialMoves) {
        this.potentialMoves = potentialMoves;
    }

    public Collection<Move> getLegalMoves() {
        return legalMoves;
    }

    public void setLegalMoves(Collection<Move> legalMoves) {
        this.legalMoves = legalMoves;
    }

    public boolean isCheckMated() {
        return isCheckMated;
    }

    public void setCheckMated(boolean checkMated) {
        isCheckMated = checkMated;
    }

    public boolean isStaleMated() {
        return isStaleMated;
    }

    public void setStaleMated(boolean staleMated) {
        isStaleMated = staleMated;
    }
    //Getters && Setters - END

    //Methods

    /**
     * Calculate all the potential moves of the player
     * @param board the board to which the moves are going to be calculated
     * */
    protected List<Move_2> calculateAllPotentialMoves(Board_2 board){
        Collection<Piece> pieces = getActivePieces();
        List<Move_2> potentialMoves = new ArrayList<>();

        for (Piece piece: pieces){
            potentialMoves.addAll(piece.calculatePotentialMoves(board));
        }

        return potentialMoves;
    }

    /**
     * Calculate all the legal moves of the player
     * @param board the board to which the legal moves are going to be calculated
     * */
    protected List<Move_2> calculateAllLegalMoves(Board_2 board){
        List<Move_2> playersPotentialMoves = this.calculateAllPotentialMoves(board);
        List<Move_2> playersLegalMoves = new ArrayList<>();
        Tile[] potentialBoard;
        for(Move_2 move: playersPotentialMoves){
            potentialBoard = move.makeMoveInBoard(board);
            if(board.isMyKingSafe(this,potentialBoard)){
                playersLegalMoves.add(move);
            }else{
                System.out.println("Move: "+move.toString() +" not added.");
            }
        }
        return playersLegalMoves;
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
     * Get all the pieces of this player that are still in the game
     * @return The pieces
     */
    public abstract Collection<Piece> getActivePieces();

    /**
     * Get the type of the player (black or white)
     * @return Player type
     */
    public abstract PiecesType getType();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player_2 player2 = (Player_2) o;
        return isCheckMated == player2.isCheckMated && isStaleMated == player2.isStaleMated && Objects.equals(board, player2.board) && Objects.equals(potentialMoves, player2.potentialMoves) && Objects.equals(legalMoves, player2.legalMoves);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, potentialMoves, legalMoves, isCheckMated, isStaleMated);
    }

    @Override
    public String toString() {
        return "Player_2{" +
                "board=" + board +
                ", potentialMoves=" + potentialMoves +
                ", legalMoves=" + legalMoves +
                ", isCheckMated=" + isCheckMated +
                ", isStaleMated=" + isStaleMated +
                '}';
    }
    //Methods - END
}
