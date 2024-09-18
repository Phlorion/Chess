package com.example.chess.player;

import com.example.chess.board.Board;
import com.example.chess.board.Tile;
import com.example.chess.move.Move;
import com.example.chess.piece.Piece;
import com.example.chess.piece.PiecesType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public abstract class Player {
    //Variables
    protected Board board;
    protected List<Move> potentialMoves;
    protected List<Move> legalMoves;
    private boolean isCheckMated;
    private boolean isStaleMated;
    //Constructors

    public Player(Board board, List<Move> potentialMoves, List<Move> legalMoves, boolean isCheckMated, boolean isStaleMated) {
        this.board = board;
        this.potentialMoves = potentialMoves;
        this.legalMoves = legalMoves;
        this.isCheckMated = isCheckMated;
        this.isStaleMated = isStaleMated;
    }

    public Player(Board board) {
        this.board = board;
        this.isCheckMated = false;
        this.isStaleMated = false;
    }
    //Getters && Setters
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Collection<Move> getPotentialMoves() {
        return potentialMoves;
    }

    public void setPotentialMoves(List<Move> potentialMoves) {
        this.potentialMoves = potentialMoves;
    }

    public List<Move> getLegalMoves() {
        return legalMoves;
    }

    public void setLegalMoves(List<Move> legalMoves) {
        this.legalMoves = legalMoves;
    }
    public boolean getCheckMated() {
        return isCheckMated;
    }

    public void setCheckMated(boolean checkMated) {
        isCheckMated = checkMated;
    }
    public boolean getStaleMated() {
        return isStaleMated;
    }

    public void setStaleMated(boolean staleMated) {
        isStaleMated = staleMated;
    }
    //Getters && Setters - END

    //Methods

    /**
     * Calculate all the potential moves of the player
     * */
    public List<Move> calculateAllPotentialMoves(Tile[] board){
        Collection<Piece> pieces = getActivePieces();
        List<Move> potentialMoves = new ArrayList<>();

        for (Piece piece: pieces){
            potentialMoves.addAll(piece.calculatePotentialMoves(board));
        }

        return potentialMoves;
    }

    /**
     * Calculate all the legal moves of the player
     * */
    public List<Move> calculateAllLegalMoves(Tile[] board){
        List<Move> playersPotentialMoves = this.calculateAllPotentialMoves(board);
        List<Move> playersLegalMoves = new ArrayList<>();
        Tile[] potentialBoard;
        for(Move move: playersPotentialMoves){
            potentialBoard = move.makeMoveInBoard();
            if(this.board.isMyKingSafe(this,potentialBoard)){
                playersLegalMoves.add(move);
            }else{
                System.out.println("Move: "+move.toString() +" not added.");
            }
        }
        return playersLegalMoves;
    }
//    /**
//     * Get the piece king of this player
//     * @return The piece
//     */
//    public King findMyKing(Tile[] board) {
//        for (Piece p : getActivePieces()) {
//            if (p.getPieceKind().equals(Piece.PieceKind.KING)) {
//                return (King) p;
//            }
//        }
//        return null;
//    }
    /**
     * Get all the moves that are currently attacking this tile
     * @param tile The tile that is getting attacked
     * @param moves The moves we are examining
     * @return The moves from the total set of legal moves that are attacking this tile
     */
    public List<Move> isAttackingOnTile(Tile tile, List<Move> moves) {
        List<Move> attacking = new ArrayList<>();
        for (Move m : moves) {
            if (tile.equals(m.getTo())) {
                attacking.add(m);
            }
        }
        return attacking;
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
        Player player2 = (Player) o;
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
