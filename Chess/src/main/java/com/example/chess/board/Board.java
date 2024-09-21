package com.example.chess.board;

import com.example.chess.move.CaptureMove;
import com.example.chess.move.Castle;
import com.example.chess.move.Move;
import com.example.chess.move.RegularMove;
import com.example.chess.piece.*;
import com.example.chess.player.PlayerBlack;
import com.example.chess.player.PlayerWhite;
import com.example.chess.player.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Board {
    public final static int NUM_TILES = 64;
    public final static int NUM_TILES_PER_ROW = 8;
    public final static int NUM_TILES_PER_COL = 8;
    private Tile[] board;
    private Collection<Piece> whitePieces;
    private Collection<Piece> blackPieces;
    private Collection<Piece> whiteCapturedPieces;
    private Collection<Piece> blackCapturedPieces;
    private Player currentPlayer;
    private Player opponentPlayer;
    private Player whitePlayer;
    private Player blackPlayer;
    private List<Move> movesHistory;

    //Constructor

    public Board(){
        //set the board/ tiles
        this.board = new Tile[NUM_TILES];
        for (int i = 0; i < this.board.length; i++) {
            this.board[i] = new Tile(i/NUM_TILES_PER_ROW ,i%NUM_TILES_PER_ROW,null);
        }
        //set the players
        //white
        this.setWhitePlayer(new PlayerWhite(this));
        //black
        this.setBlackPlayer(new PlayerBlack(this));
        //current player
        this.setCurrentPlayer(this.getWhitePlayer());
        //opponent player
        this.setOpponentPlayer(this.getBlackPlayer());
        //set the move history
        this.setMovesHistory(new ArrayList<Move>());
        //set the Pieces' Collections
        this.setWhitePieces(new ArrayList<>());
        this.setWhiteCapturedPieces(new ArrayList<>());
        this.setBlackPieces(new ArrayList<>());
        this.setBlackCapturedPieces(new ArrayList<>());
    }

    // Copy Constructor
    public Board(Board original) {
        //set the board/tiles
        this.board = new Tile[NUM_TILES];
        // copy the pieces
        ArrayList<Piece> whites = new ArrayList<>();
        ArrayList<Piece> blacks = new ArrayList<>();
        for (int i = 0; i < this.board.length; i++) {
            Tile originalTile = original.getTile(i / NUM_TILES_PER_ROW, i % NUM_TILES_PER_ROW);
            Piece originalPiece = originalTile.getPiece();
            // if there is no piece in this tile skip
            if (originalPiece == null) {
                this.board[i] = new Tile(originalTile.getI(), originalTile.getJ(), null);
                continue;
            }

            // if piece != null find piece kind and copy it
            Piece copiedPiece = switch (originalPiece.getPieceKind()) {
                case KING -> new King((King) originalPiece);
                case ROOK -> new Rook((Rook) originalPiece);
                case QUEEN -> new Queen((Queen) originalPiece);
                case KNIGHT -> new Knight((Knight) originalPiece);
                case BISHOP -> new Bishop((Bishop) originalPiece);
                default -> new Pawn((Pawn) originalPiece);
            };
            this.board[i] = new Tile(originalTile.getI(), originalTile.getJ(), copiedPiece);
            // add it to the corresponding array based on its type
            if (copiedPiece.getType().equals(PiecesType.WHITE))
                whites.add(copiedPiece);
            else
                blacks.add(copiedPiece);
        }
        //set the players
        //white
        this.setWhitePlayer(new PlayerWhite(this));
        //black
        this.setBlackPlayer(new PlayerBlack(this));
        //current player && opponent player
        if (original.getCurrentPlayer().getType().equals(PiecesType.WHITE)) {
            this.setCurrentPlayer(this.getWhitePlayer());
            this.setOpponentPlayer(this.getBlackPlayer());
        }
        else {
            this.setCurrentPlayer(this.getBlackPlayer());
            this.setOpponentPlayer(this.getWhitePlayer());
        }

        //set the move history
        this.setMovesHistory(new ArrayList<Move>());

        //set the Pieces' Collections
        this.setWhitePieces(whites);
        this.setWhiteCapturedPieces(original.getWhiteCapturedPieces());
        this.setBlackPieces(blacks);
        this.setBlackCapturedPieces(original.getBlackCapturedPieces());

        // COPY POTENTIAL AND LEGAL MOVES FOR CURRENT PLAYER AND OPPONENT
        // Potential for current
        List<Move> copiedPotentialMoves = new ArrayList<>();
        for (Move m : original.getCurrentPlayer().getPotentialMoves()) {
            Move copyMove = null;
            if (m.getClass().getName().contains("RegularMove"))
                copyMove = new RegularMove(m, this.getBoard());
            else if (m.getClass().getName().contains("CaptureMove"))
                copyMove = new CaptureMove((CaptureMove) m, this.getBoard());
            else if (m.getClass().getName().contains("Castle"))
                copyMove = new Castle((Castle) m, this.getBoard());
            copiedPotentialMoves.add(copyMove);
        }
        this.getCurrentPlayer().setPotentialMoves(copiedPotentialMoves);

        // potential for opponent
        copiedPotentialMoves = new ArrayList<>();
        for (Move m : original.getOpponentPlayer().getPotentialMoves()) {
            Move copyMove = null;
            if (m.getClass().getName().contains("RegularMove"))
                copyMove = new RegularMove(m, this.getBoard());
            else if (m.getClass().getName().contains("CaptureMove"))
                copyMove = new CaptureMove((CaptureMove) m, this.getBoard());
            else if (m.getClass().getName().contains("Castle"))
                copyMove = new Castle((Castle) m, this.getBoard());
            copiedPotentialMoves.add(copyMove);
        }
        this.getOpponentPlayer().setPotentialMoves(copiedPotentialMoves);

        // legal for current
        List<Move> copiedLegalMoves = new ArrayList<>();
        for (Move m : original.getCurrentPlayer().getLegalMoves()) {
            Move copyMove = null;
            if (m.getClass().getName().contains("RegularMove"))
                copyMove = new RegularMove(m, this.getBoard());
            else if (m.getClass().getName().contains("CaptureMove"))
                copyMove = new CaptureMove((CaptureMove) m, this.getBoard());
            else if (m.getClass().getName().contains("Castle"))
                copyMove = new Castle((Castle) m, this.getBoard());
            copiedLegalMoves.add(copyMove);
        }
        this.getCurrentPlayer().setLegalMoves(copiedLegalMoves);

        // legal for opponent
        copiedLegalMoves = new ArrayList<>();
        for (Move m : original.getOpponentPlayer().getLegalMoves()) {
            Move copyMove = null;
            if (m.getClass().getName().contains("RegularMove"))
                copyMove = new RegularMove(m, this.getBoard());
            else if (m.getClass().getName().contains("CaptureMove"))
                copyMove = new CaptureMove((CaptureMove) m, this.getBoard());
            else if (m.getClass().getName().contains("Castle"))
                copyMove = new Castle((Castle) m, this.getBoard());
            copiedLegalMoves.add(copyMove);
        }
        this.getOpponentPlayer().setLegalMoves(copiedLegalMoves);

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

    public Player getOpponentPlayer() {return opponentPlayer;}

    public void setOpponentPlayer(Player opponentPlayer) {this.opponentPlayer = opponentPlayer;}

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

    public List<Move> getMovesHistory() {
        return movesHistory;
    }

    public void setMovesHistory(List<Move> movesHistory) {
        this.movesHistory = movesHistory;
    }
    //Getter + Setter - END

    //Methods
    /**
     * Set a piece to the board
     * @param piece The piece to be set
     */
    public void setPieceOnBoard(Piece piece, int posI, int posJ){
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

    /**
     * This method makes a move in the board by changing the Tile[] board,
        updating the pieces involved in the move (Increasing the moves of the piece and its hasMoved param),
        updating the moveHistory, and checking if we have a checkmate of stalemate.
     * @param move The move to be made in the board.
     * */
    public void makeMove(Move move){
        //Check if the move is from the current player
        if(!move.getPiece().getType().equals(currentPlayer.getType())){
            System.out.println("Tried to move opponent's piece.");
            return;
        }
        //Find out what move is to be made
        String moveType = move.getClass().getSimpleName();
        //Change the board, because the move is valid
        this.board = move.makeMoveInBoard();
        move.getPiece().setMoves(move.getPiece().getMoves()+1);
        if (!move.getPiece().hasMoved()){
            move.getPiece().setHasMoved(true);
        }

        /**
         * Add the move to the moveHistory
         */
        //Add the move to the moveHistory
        this.addMoveInMoveHistory(move);
        switch (moveType){
            case "RegularMove":
                System.out.println("Regular move");
                break;
            case "CaptureMove":
                System.out.println("Capture move");
                //Remove the piece from the board and place it into the captured pieces
                if(currentPlayer.getType().equals(PiecesType.WHITE)){
                    this.blackPieces.remove(((CaptureMove) move).getCapturingPiece());
                    this.blackCapturedPieces.add(((CaptureMove) move).getCapturingPiece());
                }else {
                    this.whitePieces.remove(((CaptureMove) move).getCapturingPiece());
                    this.whiteCapturedPieces.add(((CaptureMove) move).getCapturingPiece());
                }
                break;
            case "Castle":
                System.out.println("Castle move");
                Castle castleMove = (Castle) move;
                castleMove.getCastlingRook().setMoves(castleMove.getCastlingRook().getMoves() + 1);
                castleMove.getCastlingRook().setHasMoved(true);
                break;
            default:
                System.out.println("Unknown move.");
                break;
        }

        //Calculate the new potential moves for both players - for castle
        getWhitePlayer().setPotentialMoves(getWhitePlayer().calculateAllPotentialMoves(this.board));
        getBlackPlayer().setPotentialMoves(getBlackPlayer().calculateAllPotentialMoves(this.board));
        //Calculate the new legal moves for both players
        getWhitePlayer().setLegalMoves(getWhitePlayer().calculateAllLegalMoves(this.board));
        getBlackPlayer().setLegalMoves(getBlackPlayer().calculateAllLegalMoves(this.board));
        //Change the current player
        if(currentPlayer.getType().equals(PiecesType.WHITE)){
            this.currentPlayer = this.getBlackPlayer();
        } else if (currentPlayer.getType().equals(PiecesType.BLACK)) {
            this.currentPlayer = this.getWhitePlayer();
        }
        if(this.getCurrentPlayer().getLegalMoves().isEmpty()){
            if(isMyKingSafe(this.getCurrentPlayer(),this.getBoard())){
                this.currentPlayer.setStaleMated(true);
            }else{
                this.currentPlayer.setCheckMated(true);
            }
        }

    }
    /**
     * Add a move to the move history.
     * @param move The move that will be added.
     * */
    private void addMoveInMoveHistory(Move move){
        this.getMovesHistory().add(move);
    }
    /**
     * Check if the player's King is safe on a board.
        We use this method to decide whether a move will set our king in danger.
        If it does, we cannot perform this move.
     * @param player The player whom King we will check
     * @param testingBoard The testing board to which we will check the King's safety
     * @return boolean True if player's King is safe, false otherwise
     * */
    public boolean isMyKingSafe(Player player, Tile[] testingBoard){
        //use currentPlayer
        PiecesType type = player.getType();
        Collection<Piece> opponentPieces = new ArrayList<>();
        List<Move> opponentMoves = new ArrayList<>();
        //find the pieces of the opponent
        if(type == PiecesType.WHITE){
//            opponentPieces = getBlackPieces();
            opponentPieces = findAllActivePieces(testingBoard,PiecesType.BLACK);
        } else if (type == PiecesType.BLACK) {
//            opponentPieces = getWhitePieces();
            opponentPieces = findAllActivePieces(testingBoard,PiecesType.WHITE);
        }
        //calculate the potential moves of the opponent's pieces
        for(Piece piece : opponentPieces){
            opponentMoves.addAll(piece.calculatePotentialMoves(testingBoard));
        }
        //if any piece can attack the king of the examining player, return false
        //otherwise the player's king is safe in this position
//        return player.isAttackingOnTile(getTileByPiece(player.findMyKing()), opponentMoves).isEmpty();
        return player.isAttackingOnTile(getTileByPiece(testingBoard, findKingOnBoard(testingBoard,player)), opponentMoves).isEmpty();
    }

    /**
     * How many pieces of one player are still remaining in the game
     * @param board The board we want to search for all the pieces of the given type
     * @param type Black or White
     * @return All the active pieces of the given type
     */
    public Collection<Piece> findAllActivePieces(Tile[] board, PiecesType type) {
        List<Piece> activePieces = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if (board[i].getPiece() != null && board[i].getPiece().getType().equals(type)) {
                activePieces.add(board[i].getPiece());
            }
        }
        return activePieces;
    }

    /**
     * Get the piece king of this player
     * @return The piece
     */
    public King findKingOnBoard(Tile[] board, Player player) {
        for (Piece p : findAllActivePieces(board, player.getType())) {
            if (p.getPieceKind().equals(Piece.PieceKind.KING)) {
                return (King) p;
            }
        }
        return null;
    }
    /**
     * Finds the tile that contains the specific piece
     * @param piece the piece
     * @return the piece
     */
    public Tile getTileByPiece(Tile[] board, Piece piece) {
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
