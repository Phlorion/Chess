package com.example.chess.piece;

//import com.example.chess.board.Board;
import com.example.chess.board.Board;
import com.example.chess.board.Tile;
import com.example.chess.move.*;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    final int[][] CANDIDATE_MOVE_VECTOR_COORDINATES = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

    public Bishop(/*int pos_i, int pos_j,*/ PiecesType type) {
        super(/*pos_i, pos_j,*/ type);
        pieceKind = PieceKind.BISHOP;
    }
    public Bishop(Bishop other){
        super(other);
        this.pieceKind = PieceKind.BISHOP;
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
        boolean checkedDirection;
//        Tile currentTile = board.getTile(piecePosI, piecePosJ);
//        Tile currentTile = board.getTileByPiece(board.getBoard(),this);
        Tile currentTile = getTileByPiece(board,this);
        List<Move> legalMoves = new ArrayList<>();

        for (int[] currentVec : CANDIDATE_MOVE_VECTOR_COORDINATES) {
            checkedDirection = false;
            int steps = 0;
            while (!checkedDirection) {
                candidateDestinationCoordinateI = currentTile.getI() + currentVec[0] * (steps+1);
                candidateDestinationCoordinateJ = currentTile.getJ() + currentVec[1] * (steps+1);

                // if the tile has valid coordinates
                if (isValidCoordinate(candidateDestinationCoordinateI, candidateDestinationCoordinateJ)) {
//                if (board.isValidCoordinate(candidateDestinationCoordinateI, candidateDestinationCoordinateJ)) {
                    Tile candidateDestinationTile = board[Board.NUM_TILES_PER_ROW* candidateDestinationCoordinateI + candidateDestinationCoordinateJ];
//                    Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinateI, candidateDestinationCoordinateJ);

                    // if tile is not occupied
                    if (candidateDestinationTile.isEmpty()) {
                        legalMoves.add(new RegularMove(currentTile, candidateDestinationTile, this, board)); // regular move
                        steps++;
                    } else {
                        Piece pieceAtDestination = candidateDestinationTile.getPiece();

                        // if it is an enemy piece where we want to go
                        if (pieceAtDestination.getType() != this.type) {
                            legalMoves.add(new CaptureMove(currentTile, candidateDestinationTile, this, board, candidateDestinationTile.getPiece())); // capture move
                        }
                        checkedDirection = true;
                    }
                } else {
                    checkedDirection = true;
                }
            }
        }

        return legalMoves;
    }

    @Override
    public String toString() {
        if (type.equals(PiecesType.WHITE))
            return PieceKind.BISHOP.toString();
        else
            return PieceKind.BISHOP.toString().toLowerCase();
    }
}
