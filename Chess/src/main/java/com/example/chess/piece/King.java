package com.example.chess.piece;

//import com.example.chess.board.Board;
import com.example.chess.board.Board;
import com.example.chess.board.Tile;
import com.example.chess.move.*;
//import com.example.chess.player.Player;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    final int[][] CANDIDATE_MOVE_VECTOR_COORDINATES = {{0, -1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}};

    public King(/*int pos_i, int pos_j,*/ PiecesType type) {
        super(/*pos_i, pos_j,*/ type);
        pieceKind = PieceKind.KING;
    }
    public King(King other){
        super(other);
        this.pieceKind = PieceKind.KING;
    }

    /**
     * Calculate all the legal moves for a piece
     * @param board The board in which the piece is placed
     * @return All the legal moves of the piece
     */
    @Override
    public List<Move> calculatePotentialMoves(Tile[] board) {
        int candidateDestinationCoordinateI;
        int candidateDestinationCoordinateJ;
        List<Move> legalMoves = new ArrayList<>();
//        Tile currentTile = board.getTile(piecePosI, piecePosJ);
        Tile currentTile = getTileByPiece(board,this);
//        Tile currentTile = board.getTileByPiece(board.getBoard(),this);

        for (int[] current : CANDIDATE_MOVE_VECTOR_COORDINATES) {
            candidateDestinationCoordinateI = currentTile.getI() + current[0];
            candidateDestinationCoordinateJ = currentTile.getJ() + current[1];

            // if the tile has valid coordinates
            if (isValidCoordinate(candidateDestinationCoordinateI, candidateDestinationCoordinateJ)) {
//            if (board.isValidCoordinate(candidateDestinationCoordinateI, candidateDestinationCoordinateJ)) {
                Tile candidateDestinationTile = board[Board.NUM_TILES_PER_ROW* candidateDestinationCoordinateI + candidateDestinationCoordinateJ];
//                Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinateI, candidateDestinationCoordinateJ);

                // if tile is not occupied
                if (candidateDestinationTile.isEmpty()) {
                    legalMoves.add(new RegularMove(currentTile, candidateDestinationTile, this, board)); // regular move
                } else {
                    Piece pieceAtDestination = candidateDestinationTile.getPiece();

                    // if it is an enemy piece where we want to go
                    if (pieceAtDestination.getType() != this.type) {
                        legalMoves.add(new CaptureMove(currentTile, candidateDestinationTile, this, board, candidateDestinationTile.getPiece())); // capture move
                    }
                }
            }
        }

        return legalMoves;
    }

    @Override
    public String toString() {
        if (type.equals(PiecesType.WHITE))
            return PieceKind.KING.toString();
        else
            return PieceKind.KING.toString().toLowerCase();
    }
}
