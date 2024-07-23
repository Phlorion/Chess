package com.example.chess.board;

import com.example.chess.move.Move;
import com.example.chess.move.Move_2;
import com.example.chess.piece.*;
import com.example.chess.player.Player;
import com.example.chess.player.PlayerBlack_2;
import com.example.chess.player.PlayerWhite_2;
import com.example.chess.player.Player_2;

import java.util.ArrayList;
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
        //set the board/ tiles
        this.board = new Tile[NUM_TILES];
        for (int i = 0; i < this.board.length; i++) {
            this.board[i] = new Tile();
//            System.out.println("Constructor board2 tile I"+this.board[i].getI());
//            System.out.println("Constructor board2 tile J"+this.board[i].getJ());
        }
        //set the players
        //white
        this.setWhitePlayer(new PlayerWhite_2(this));
        //black
        this.setBlackPlayer(new PlayerBlack_2(this));
        //current player
        this.setCurrentPlayer(this.getWhitePlayer());
        //set the move history
        this.setMovesHistory(new ArrayList<Move_2>());
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
    public void setStartingPieces(){
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

            //set the black pieces in the collection
            this.setBlackPieces(this.findAllActivePieces(this.board, PiecesType.BLACK));

            //set the (i,j) on the rest of the tiles
            for (int i = 2; i <5 ; i++) {
                for (int j = 0; j < 8; j++) {
                    this.board[NUM_TILES_PER_ROW* i + j].setI(i);
                    this.board[NUM_TILES_PER_ROW* i + j].setJ(j);
                    //piece is already null - I think
//                    System.out.println("setup starting tiles i"+this.board[NUM_TILES_PER_ROW* i + j].getI());
//                    System.out.println("setup starting tiles j"+this.board[NUM_TILES_PER_ROW* i + j].getJ());
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

            //set the white pieces in the collection
            this.setWhitePieces(this.findAllActivePieces(this.board, PiecesType.WHITE));

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

    protected void makeMove(Move_2 move){

    }
    public boolean isMyKingSafe(Player_2 player, Tile[] testingBoard){
        //use currentPlayer
        PiecesType type = player.getType();
        Collection<Piece> opponentPieces = new ArrayList<>();
        List<Move_2> opponentMoves = new ArrayList<>();
        //find the pieces of the opponent
        if(type == PiecesType.WHITE){
            opponentPieces = getBlackPieces();
        } else if (type == PiecesType.BLACK) {
            opponentPieces = getWhitePieces();
        }
        //calculate the potential moves of the opponent's pieces
        for(Piece piece : opponentPieces){
            opponentMoves.addAll(piece.calculatePotentialMoves(this));
        }
        //if any piece can attack the king of the examining player, return false
        if(!player.isAttackingOnTile(getTileByPiece(player.findMyKing()),opponentMoves).isEmpty()){
            return false;
        }
        //otherwise the player's king is safe in this position
        return true;
    }

    /**
     * How many pieces of one player are still remaining in the game
     * @param board The board we want to search for all the pieces of the given type
     * @param type Black or White
     * @return All the active pieces of the given type
     */
    private Collection<Piece> findAllActivePieces(Tile[] board, PiecesType type) {
        List<Piece> activePieces = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if (board[i].getPiece() != null && board[i].getPiece().getType().equals(type)) {
                activePieces.add(board[i].getPiece());
            }
        }
        return activePieces;
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
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int z=0; z<NUM_TILES; z++) {
            if (z % NUM_TILES_PER_ROW == 0) res.append("\n");
            Tile tile = board[z];
            res.append(" ").append(tile.toString()).append(" ");
        }
        res.append("\n");
        return res.toString();
    }
    //Methods - END
}
