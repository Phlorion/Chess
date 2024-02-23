package com.example.chess.board;

import com.example.chess.move.Move;
import com.example.chess.piece.*;
import com.example.chess.player.Player;
import com.example.chess.player.PlayerBlack;
import com.example.chess.player.PlayerWhite;

import java.util.*;

public class Board {
    public final static int NUM_TILES = 64;
    public final static int NUM_TILES_PER_ROW = 8;
    public final static int NUM_TILES_PER_COL = 8;
    List<Tile> board;
    Collection<Piece> whitePieces;
    Collection<Piece> blackPieces;
    Player playerWhite;
    Player playerBlack;
    Player currentPlayer;
    Player opponentPlayer;

    private Board(Builder builder, boolean fake) {
        // build the board
        board = createBoard(builder);
        // get the active pieces of each player
        whitePieces = getAllActivePieces(board, PiecesType.WHITE);
        blackPieces = getAllActivePieces(board, PiecesType.BLACK);
        // get the players
        playerWhite = new PlayerWhite(this);
        playerBlack = new PlayerBlack(this);
        // get the current player
        this.currentPlayer = builder.nextMoveMaker.choosePlayer(playerWhite, playerBlack);
        this.opponentPlayer = builder.nextMoveMaker.chooseOpponent(playerWhite, playerBlack);

        if (!fake) {
            // set each player's legal moves
            opponentPlayer.setLegalMoves(calculateAllLegalMoves(this, opponentPlayer));
            currentPlayer.setLegalMoves(calculateAllLegalMoves(this, currentPlayer));
        }
    }

    public Collection<Piece> getWhitePieces() {return whitePieces;}
    public Collection<Piece> getBlackPieces() {
        return blackPieces;
    }
    public Player getPlayerWhite() {
        return playerWhite;
    }

    public Player getPlayerBlack() {
        return playerBlack;
    }

    public Player getCurrentPlayer() {return currentPlayer;}

    public Player getOpponentPlayer() {
        return opponentPlayer;
    }

    /**
     * Creates a game board of "NUM_TILES" tiles
     * @param builder The builder we are using to build this board object
     * @return A list of all the tiles of this board
     */
    private static List<Tile> createBoard(Builder builder) {
        Tile[] tiles = new Tile[NUM_TILES];
        for (int z=0; z<NUM_TILES; z++) {
            tiles[z] = new Tile(z / NUM_TILES_PER_ROW, z % NUM_TILES_PER_ROW, builder.boardConfig.get(z));
        }
        return Arrays.asList(tiles);
    }

    /**
     * Create a standard chess board with all the pieces at their starting position
     * @return The chess board
     */
    public static Board createStandardBoard() {
        Builder builder = new Builder();
        // set black pieces
        builder.setPiece(new Rook(0, 0, PiecesType.BLACK));
        builder.setPiece(new Knight(0, 1, PiecesType.BLACK));
        builder.setPiece(new Bishop(0, 2, PiecesType.BLACK));
        builder.setPiece(new Queen(0, 3, PiecesType.BLACK));
        builder.setPiece(new King(0, 4, PiecesType.BLACK));
        builder.setPiece(new Bishop(0, 5, PiecesType.BLACK));
        builder.setPiece(new Knight(0, 6, PiecesType.BLACK));
        builder.setPiece(new Rook(0, 7, PiecesType.BLACK));
        for (int j=0; j<NUM_TILES_PER_COL; j++) builder.setPiece(new Pawn(1, j, PiecesType.BLACK));
        // set white pieces
        builder.setPiece(new Rook(7, 0, PiecesType.WHITE));
        builder.setPiece(new Knight(7, 1, PiecesType.WHITE));
        builder.setPiece(new Bishop(7, 2, PiecesType.WHITE));
        builder.setPiece(new Queen(7, 3, PiecesType.WHITE));
        builder.setPiece(new King(7, 4, PiecesType.WHITE));
        builder.setPiece(new Bishop(7, 5, PiecesType.WHITE));
        builder.setPiece(new Knight(7, 6, PiecesType.WHITE));
        builder.setPiece(new Rook(7, 7, PiecesType.WHITE));
        for (int j=0; j<NUM_TILES_PER_COL; j++) builder.setPiece(new Pawn(6, j, PiecesType.WHITE));
        // set white first player
        builder.setMoveMaker(PiecesType.WHITE);
        // build the new standard chess board
        return builder.build();
    }

    public static class Builder {
        Map<Integer, Piece> boardConfig;
        PiecesType nextMoveMaker;

        public Builder() {
            boardConfig = new HashMap<>();
        }

        public Builder setPiece(Piece piece) {
            boardConfig.put(NUM_TILES_PER_ROW* piece.getPiecePosI() + piece.getPiecePosJ(), piece);
            return this;
        }

        public Builder setMoveMaker(PiecesType nextMoveMaker) {
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Board build() {
            return new Board(this, false);
        }
        public Board fakeBuild() {
            return new Board(this, true);
        }
    }

    /**
     * How many pieces of one player are still remaining in the game
     * @param board The board we want to search for all the pieces of the given type
     * @param type Black or White
     * @return All the active pieces of the given type
     */
    private Collection<Piece> getAllActivePieces(List<Tile> board, PiecesType type) {
        List<Piece> activePieces = new ArrayList<>();

        for (Tile t : board) {
            if (t.getPiece() != null && t.getPiece().getType().equals(type)) {
                activePieces.add(t.getPiece());
            }
        }
        return activePieces;
    }

    /**
     * Get all the legal moves a player can make
     * @param player The player's pieces
     * @return All the possible legal moves that can be made
     */
    private Collection<Move> calculateAllLegalMoves(Board board, Player player) {
        Collection<Piece> pieces = player.getActivePieces();
        List<Move> legalMoves = new ArrayList<>();

        for (Piece p : pieces) {
            legalMoves.addAll(p.legalMoves(board));
            //System.out.println(legalMoves);
        }

        // if the player we are calculating all the legal moves for is the current one,
        // then remove the moves that are going to put him in a check position and the ones
        // that are not going to help him get out of a check position
        if (player.equals(currentPlayer) && currentPlayer.isChecked()) {
            List<Move> tempLegalMoves = new ArrayList<>(legalMoves);
            Board temp;
            Tile kingTile;
            for (Move m : tempLegalMoves) {
                //System.out.println(m);
                temp = board;
                //System.out.println(temp);
                temp = m.fakeExecute(temp);
                //System.out.println(temp.getOpponentPlayer().getActivePieces());
                //System.out.println(temp);
                kingTile = temp.getTileByPiece(temp.getCurrentPlayer().getKing());
                temp.getOpponentPlayer().setLegalMoves(calculateAllLegalMoves(temp, temp.getOpponentPlayer()));
                if (!Player.isAttackingOnTile(kingTile, temp.getOpponentPlayer().getAllLegalMoves()).isEmpty()) {
                    board = m.reverseFakeExecute(temp);
                    legalMoves.remove(m);
                    //System.out.println("Illegal");
                } else {
                    board = m.reverseFakeExecute(temp);
                    //System.out.println("Legal");
                }
                //System.out.println("-----------------------------------------");
            }
            System.out.println(legalMoves);

        } else if (player.equals(currentPlayer)) {
            List<Move> tempLegalMoves = new ArrayList<>(legalMoves);
            for (Move m : tempLegalMoves) {
                if (m.getPiece().equals(player.getKing())) {
                    if (!Player.isAttackingOnTile(m.getTo(), board.getOpponentPlayer().getAllLegalMoves()).isEmpty()) {
                        legalMoves.remove(m);
                    }
                }
            }
            System.out.println(legalMoves);
        }

        // checkmate and stalemate
        if (legalMoves.isEmpty() && player.isChecked()) {
            player.setIsCheckMated(true);
        } else if (legalMoves.isEmpty() && !player.isChecked()) {
            player.setIsStaleMated(true);
        }

        return legalMoves;
    }

    /**
     * Get all tiles
     * @return All the tiles
     */
    public ArrayList<Tile> getAllTiles() {
        return new ArrayList<>(board);
    }

    /**
     * Is the coordinate of a tile valid
     * @param i The i position
     * @param j The j position
     * @return Boolean true / false
     */
    public static boolean isValidCoordinate(int i, int j) {
        return !(i < 0 || i > NUM_TILES_PER_ROW-1 || j < 0 || j > NUM_TILES_PER_COL-1);
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
        return board.get(NUM_TILES_PER_ROW * i + j);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int z=0; z<NUM_TILES; z++) {
            if (z % NUM_TILES_PER_ROW == 0) res.append("\n");
            Tile tile = board.get(z);
            res.append(" ").append(tile.toString()).append(" ");
        }
        res.append("\n");
        return res.toString();
    }
}
