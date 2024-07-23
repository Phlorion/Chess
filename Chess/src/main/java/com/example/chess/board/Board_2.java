package com.example.chess.board;

import com.example.chess.move.Move;
import com.example.chess.move.Move_2;
import com.example.chess.piece.*;
import com.example.chess.player.Player;
import com.example.chess.player.Player_2;

import java.util.Collection;
import java.util.List;

public class Board_2 {
    public final static int NUM_TILES = 64;
    public final static int NUM_TILES_PER_ROW = 8;
    public final static int NUM_TILES_PER_COL = 8;
    private Tile[] board;
    private Collection<Piece> whitePieces;
    private Collection<Piece> blackPieces;
    private Collection<Piece> whiteCapturedPieces;
    private Collection<Piece> blackCapturedPieces;
    private Player_2 currentPlayer;
    private Player_2 whitePlayer;
    private Player_2 blackPlayer;
    private List<Move_2> movesHistory;

    //Constructor

    public Board_2(){
        this.board = new Tile[NUM_TILES];
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

    public Player_2 getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player_2 currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player_2 getWhitePlayer() {
        return whitePlayer;
    }

    public void setWhitePlayer(Player_2 whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    public Player_2 getBlackPlayer() {
        return blackPlayer;
    }

    public void setBlackPlayer(Player_2 blackPlayer) {
        this.blackPlayer = blackPlayer;
    }

    public Collection<Piece> getWhiteCapturedPieces() {
        return whiteCapturedPieces;
    }

    public void setWhiteCapturedPieces(Collection<Piece> whiteCapturedPieces) {
        this.whiteCapturedPieces = whiteCapturedPieces;
    }

    public Collection<Piece> getBlackCapturedPieces() {
        return blackCapturedPieces;
    }

    public void setBlackCapturedPieces(Collection<Piece> blackCapturedPieces) {
        this.blackCapturedPieces = blackCapturedPieces;
    }

    public List<Move_2> getMovesHistory() {
        return movesHistory;
    }

    public void setMovesHistory(List<Move_2> movesHistory) {
        this.movesHistory = movesHistory;
    }
    //Getter + Setter - END

    //Methods
    /**
     * Set a piece to the board
     * @param piece The piece to be set
     */
    private void setPieceOnBoard(Piece piece){
        try {
            this.board[NUM_TILES_PER_ROW* piece.getPiecePosI() + piece.getPiecePosJ()].setI(piece.getPiecePosI());
            this.board[NUM_TILES_PER_ROW* piece.getPiecePosI() + piece.getPiecePosJ()].setJ(piece.getPiecePosJ());
            this.board[NUM_TILES_PER_ROW* piece.getPiecePosI() + piece.getPiecePosJ()].setPiece(piece);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Initialize the pieces to the board, plus the (i,j) on the tiles without any piece on them.
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

            //set the (i,j) on the rest of the tiles
            for (int i = 2; i <5 ; i++) {
                for (int j = 0; j < 8; j++) {
                    this.board[NUM_TILES_PER_ROW* i + j].setI(i);
                    this.board[NUM_TILES_PER_ROW* i + j].setJ(j);
                    //piece is already null - I think
                }
            }

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
    public boolean isValidCoordinate(int i, int j) {
        return !(i < 0 || i > NUM_TILES_PER_ROW-1 || j < 0 || j > NUM_TILES_PER_COL-1);
    }

    protected void makeMove(Move move){

    }
    public boolean isMyKingSafe(Player_2 player, Tile[] testingBoard){
        //use currentPlayer
        PiecesType type = player.getType();
        Collection<Piece> opponentPieces=null;
        List<Move_2> opponentMoves = null;
        if(type == PiecesType.WHITE){
            opponentPieces = getBlackPieces();
            for(Piece piece : opponentPieces){
                opponentMoves.addAll(piece.calculatePotentialMoves(this));
            }

            if(!player.isAttackingOnTile(getTileByPiece(player.getKing()),opponentMoves).isEmpty()){
                return false;
            }
        } else if (type == PiecesType.BLACK) {
        }
        return true;
    }

    /**
     * Finds the tile that contains the specific piece
     * @param piece the piece
     * @return the piece
     */
    public Tile getTileByPiece(Piece piece) {
        for (Tile t : board) {
            if (t.getPiece() != null && t.getPiece().equals(piece)) {
                return t;
            }
        }
        return null;
    }
    /**
     * Finds the tile with the given coordinates
     * @param i The i position
     * @param j The j position
     * @return The tile
     */
    public Tile getTile(int i, int j) {
        return board[NUM_TILES_PER_ROW * i + j];
    }
    //Methods - END
}
