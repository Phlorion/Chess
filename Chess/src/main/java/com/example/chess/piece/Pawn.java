package com.example.chess.piece;

import com.example.chess.board.Board;
import com.example.chess.board.Tile;
import com.example.chess.move.CaptureMove;
import com.example.chess.move.Move;
import com.example.chess.move.RegularMove;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    final int[][] CANDIDATE_MOVE_DESTINATION_COORDINATES = {{-1, 0}, {-2, 0}, {-1, -1}, {-1, 1}};
    boolean hasMoved;

    public Pawn(int pos_i, int pos_j, PiecesType type) {
        super(pos_i, pos_j, type);
    }

    /**
     * Calculate all the legal moves for a piece
     * @param board The board in which the piece is placed
     * @return All the legal moves of the piece
     */
    @Override
    public List<Move> legalMoves(Board board) {
        int candidateDestinationCoordinateI;
        int candidateDestinationCoordinateJ;
        Tile currentTile = board.getTile(piecePosI, piecePosJ);
        List<Move> legalMoves = new ArrayList<>();

        for (int[] current : CANDIDATE_MOVE_DESTINATION_COORDINATES) {
            candidateDestinationCoordinateI = piecePosI + (type.getDirection() * current[0]);
            candidateDestinationCoordinateJ = piecePosJ + current[1];

            // if the tile has valid coordinates
            if (Board.isValidCoordinate(candidateDestinationCoordinateI, candidateDestinationCoordinateJ)) {
                Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinateI, candidateDestinationCoordinateJ);

                // regular move
                if (candidateDestinationTile.isEmpty() && current[0] == -1 && current[1] == 0) {
                    legalMoves.add(new RegularMove(board, currentTile, candidateDestinationTile, this));
                }
                // if at starting rank can move 2 tiles
                if (!hasMoved && current[0] == -2 && candidateDestinationTile.isEmpty()) {
                    legalMoves.add(new RegularMove(board, currentTile, candidateDestinationTile, this));
                }
                // if enemy piece near, can capture
                if ((current[1] == -1 || current[1] == 1) &&
                        !candidateDestinationTile.isEmpty() && type != candidateDestinationTile.getPiece().getType()) {
                    legalMoves.add(new CaptureMove(board, currentTile, candidateDestinationTile, this, candidateDestinationTile.getPiece()));
                }
            }
        }

        return legalMoves;
    }

    @Override
    public String toString() {
        return PieceKind.PAWN.toString();
    }
}
