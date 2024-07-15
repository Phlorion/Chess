package com.example.chess.board;

import com.example.chess.move.Move;
import com.example.chess.piece.*;
import com.example.chess.player.Player;

import java.util.Collection;
import java.util.List;

public class Board_2 {
    public final static int NUM_TILES = 64;
    public final static int NUM_TILES_PER_ROW = 8;
    public final static int NUM_TILES_PER_COL = 8;
    private Tile[] board;
    private Collection<Piece> whitePieces;
    private Collection<Piece> blackPieces;
    private Player currentPlayer;
    private Player whitePlayer;
    private Player blackPlayer;
    private List<Move> movesHistory;

    //Constructor

    public Board_2(){

    }

    //Constructor - END


    //Getter + Setter
    public Tile[] getBoard() {
        return board;
    }

    public void setBoard(Tile[] board) {
        this.board = board;
    }

    public Collection<Piece> getWhitePieces() {
        return whitePieces;
    }

    public void setWhitePieces(Collection<Piece> whitePieces) {
        this.whitePieces = whitePieces;
    }

    public Collection<Piece> getBlackPieces() {
        return blackPieces;
    }

    public void setBlackPieces(Collection<Piece> blackPieces) {
        this.blackPieces = blackPieces;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public void setWhitePlayer(Player whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public void setBlackPlayer(Player blackPlayer) {
        this.blackPlayer = blackPlayer;
    }
    //Getter + Setter - END

    //Methods
    /**
     * Set a piece to the board
     * @param piece The piece to be set
     */
    private void setPieceOnBoard(Piece piece){
        try {
            this.board[NUM_TILES_PER_ROW* piece.getPiecePosI() + piece.getPiecePosJ()].setPiece(piece);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Initialize the pieces to the board
     */
    private void setStartingPieces(){
        try {
            //set BLACK pieces
            setPieceOnBoard(new Rook(0, 0, PiecesType.BLACK));
            setPieceOnBoard(new Knight(0, 1, PiecesType.BLACK));
            setPieceOnBoard(new Rook(0, 0, PiecesType.BLACK));
            setPieceOnBoard(new Bishop(0, 2, PiecesType.BLACK));
            setPieceOnBoard(new Queen(0, 3, PiecesType.BLACK));
            setPieceOnBoard(new King(0, 4, PiecesType.BLACK));
            setPieceOnBoard(new Bishop(0, 5, PiecesType.BLACK));
            setPieceOnBoard(new Knight(0, 6, PiecesType.BLACK));
            setPieceOnBoard(new Rook(0, 7, PiecesType.BLACK));
            for (int j=0; j<NUM_TILES_PER_COL; j++) setPieceOnBoard(new Pawn(1, j, PiecesType.BLACK));

            //set WHITE pieces
            setPieceOnBoard(new Rook(7, 0, PiecesType.WHITE));
            setPieceOnBoard(new Knight(7, 1, PiecesType.WHITE));
            setPieceOnBoard(new Bishop(7, 2, PiecesType.WHITE));
            setPieceOnBoard(new Queen(7, 3, PiecesType.WHITE));
            setPieceOnBoard(new King(7, 4, PiecesType.WHITE));
            setPieceOnBoard(new Bishop(7, 5, PiecesType.WHITE));
            setPieceOnBoard(new Knight(7, 6, PiecesType.WHITE));
            setPieceOnBoard(new Rook(7, 7, PiecesType.WHITE));
            for (int j=0; j<NUM_TILES_PER_COL; j++) setPieceOnBoard(new Pawn(6, j, PiecesType.WHITE));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Is the coordinate of a tile valid
     * @param i The i position
     * @param j The j position
     * @return Boolean true / false
     */
    protected boolean isValidCoordinate(int i, int j) {
        return !(i < 0 || i > NUM_TILES_PER_ROW-1 || j < 0 || j > NUM_TILES_PER_COL-1);
    }

    protected void makeMove(Move move){

    }
    protected boolean isMyKingSafe(Player player, Tile[] testingBoard){
        //use currentPlayer
        PiecesType type = player.getType();
        if(type == PiecesType.WHITE){
            for (int i = 0; i < blackPieces.size(); i++) {
                if(true){
                    return false;
                }
            }
        } else if (type == PiecesType.BLACK) {
            for (int i = 0; i < whitePieces.size(); i++) {
                if(true){
                    return false;
                }
            }
        }
        return true;
    }
    //Methods - END
}
