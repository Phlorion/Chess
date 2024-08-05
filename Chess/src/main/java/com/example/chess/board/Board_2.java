package com.example.chess.board;

import com.example.chess.move.CaptureMove_2;
import com.example.chess.move.Move_2;
import com.example.chess.piece.*;
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
    private void setPieceOnBoard(Piece piece, int posI, int posJ){
        try {
            this.board[NUM_TILES_PER_ROW* posI + posJ].setI(posI);
            this.board[NUM_TILES_PER_ROW* posI + posJ].setJ(posJ);
            this.board[NUM_TILES_PER_ROW* posI + posJ].setPiece(piece);
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
            setPieceOnBoard(new Rook(PiecesType.BLACK),0,0);
            setPieceOnBoard(new Knight(PiecesType.BLACK),0,1);
            setPieceOnBoard(new Bishop(PiecesType.BLACK),0,2);
            setPieceOnBoard(new Queen(PiecesType.BLACK),0,3);
            setPieceOnBoard(new King(PiecesType.BLACK),0,4);
            setPieceOnBoard(new Bishop(PiecesType.BLACK),0,5);
            setPieceOnBoard(new Knight(PiecesType.BLACK),0,6);
            setPieceOnBoard(new Rook(PiecesType.BLACK),0,7);
            for (int j=0; j<NUM_TILES_PER_COL; j++) setPieceOnBoard(new Pawn(PiecesType.BLACK),1,j);

            //set the black pieces in the collection
            this.setBlackPieces(this.findAllActivePieces(this.board, PiecesType.BLACK));

            //set the (i,j) on the rest of the tiles
            for (int i = 2; i <6 ; i++) {
                for (int j = 0; j < 8; j++) {
                    this.board[NUM_TILES_PER_ROW* i + j].setI(i);
                    this.board[NUM_TILES_PER_ROW* i + j].setJ(j);
                    //piece is already null - I think
                }
            }

            //set WHITE pieces
            setPieceOnBoard(new Rook(PiecesType.WHITE),7,0);
            setPieceOnBoard(new Knight(PiecesType.WHITE),7,1);
            setPieceOnBoard(new Bishop(PiecesType.WHITE),7,2);
            setPieceOnBoard(new Queen(PiecesType.WHITE),7,3);
            setPieceOnBoard(new King(PiecesType.WHITE),7,4);
            setPieceOnBoard(new Bishop(PiecesType.WHITE),7,5);
            setPieceOnBoard(new Knight(PiecesType.WHITE),7,6);
            setPieceOnBoard(new Rook(PiecesType.WHITE),7,7);
            for (int j=0; j<NUM_TILES_PER_COL; j++) setPieceOnBoard(new Pawn(PiecesType.WHITE),6,j);

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

    public void makeMove(Move_2 move){
        //Check if the move is from the current player
        if(!move.getPiece().getType().equals(currentPlayer.getType())){
            System.out.println("Tried to move opponent's piece.");
            return;
        }
        //Find out what move is to be made
        String moveType = move.getClass().getSimpleName();
        //Change the board, because the move is valid
        this.board = move.makeMoveInBoard();

        switch (moveType){
            case "RegularMove_2":
                System.out.println("Regular move");
                break;
            case "CaptureMove_2":
                System.out.println("Capture move");
                //Remove the piece from the board and place it into the captured pieces
                if(currentPlayer.getType().equals(PiecesType.WHITE)){
                    this.blackPieces.remove(((CaptureMove_2) move).getCapturingPiece());
                    this.blackCapturedPieces.add(((CaptureMove_2) move).getCapturingPiece());
                }else {
                    this.whitePieces.remove(((CaptureMove_2) move).getCapturingPiece());
                    this.whiteCapturedPieces.add(((CaptureMove_2) move).getCapturingPiece());
                }
                break;
            case "Castle_2":
                System.out.println("Castle move");
                break;
            default:
                System.out.println("Unknown move.");
                break;
        }
        System.out.println(this);
        //Change the board for the players
        getWhitePlayer().setBoard(this);
        getBlackPlayer().setBoard(this);
        //Calculate the new legal moves for both players
        getWhitePlayer().setLegalMoves(getWhitePlayer().calculateAllLegalMoves());
        getBlackPlayer().setLegalMoves(getBlackPlayer().calculateAllLegalMoves());
        //Change the current player
        if(currentPlayer.getType().equals(PiecesType.WHITE)){
            this.currentPlayer = this.getBlackPlayer();
        } else if (currentPlayer.getType().equals(PiecesType.BLACK)) {
            this.currentPlayer = this.getWhitePlayer();
        }

    }
    public boolean isMyKingSafe(Player_2 player, Tile[] testingBoard){
        //use currentPlayer
        PiecesType type = player.getType();
        Collection<Piece> opponentPieces = new ArrayList<>();
        List<Move_2> opponentMoves = new ArrayList<>();
        //find the pieces of the opponent -- use the testing board TODO
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
        //otherwise the player's king is safe in this position
        return player.isAttackingOnTile(getTileByPiece(player.findMyKing()), opponentMoves).isEmpty();
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
