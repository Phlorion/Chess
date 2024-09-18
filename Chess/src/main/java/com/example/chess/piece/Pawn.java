package com.example.chess.piece;

//import com.example.chess.board.Board;
import com.example.chess.board.Board;
import com.example.chess.board.Tile;
import com.example.chess.move.*;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    final int[][] CANDIDATE_MOVE_DESTINATION_COORDINATES = {{-1, 0}, {-2, 0}, {-1, -1}, {-1, 1}};

    public Pawn(/*int pos_i, int pos_j,*/ PiecesType type) {
        super(/*pos_i, pos_j,*/ type);
    }

    public Pawn(Pawn other) {
        super(other);
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
        Tile currentTile = getTileByPiece(board,this);
//        Tile currentTile = board.getTileByPiece(board.getBoard(),this);
        List<Move> legalMoves = new ArrayList<>();

        for (int[] current : CANDIDATE_MOVE_DESTINATION_COORDINATES) {
            candidateDestinationCoordinateI = currentTile.getI() + (type.getDirection() * current[0]);
            candidateDestinationCoordinateJ = currentTile.getJ() + current[1];

            // if the tile has valid coordinates
//            if (board.isValidCoordinate(candidateDestinationCoordinateI, candidateDestinationCoordinateJ)) {
            if (isValidCoordinate(candidateDestinationCoordinateI, candidateDestinationCoordinateJ)) {
//                Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinateI, candidateDestinationCoordinateJ);
                Tile candidateDestinationTile = board[Board.NUM_TILES_PER_ROW* candidateDestinationCoordinateI + candidateDestinationCoordinateJ];
//                System.out.println("Pawn candidate tile I"+candidateDestinationTile.getI());
//                System.out.println("Pawn candidate tile J"+candidateDestinationTile.getJ());
                // regular move
                if (candidateDestinationTile.isEmpty() && current[0] == -1 && current[1] == 0) {
                    legalMoves.add(new RegularMove(currentTile, candidateDestinationTile, this,board));
                }
                // if at starting rank can move 2 tiles
                else if (!hasMoved && current[0] == -2 && candidateDestinationTile.isEmpty() && board[Board.NUM_TILES_PER_ROW*(candidateDestinationCoordinateI + type.getDirection())+ candidateDestinationCoordinateJ].isEmpty()) {
                    legalMoves.add(new RegularMove(currentTile, candidateDestinationTile, this,board));
                }
                // if enemy piece near, can capture
                else if ((current[1] == -1 || current[1] == 1) &&
                        !candidateDestinationTile.isEmpty() && type != candidateDestinationTile.getPiece().getType()) {
                    legalMoves.add(new CaptureMove(currentTile, candidateDestinationTile, this, board, candidateDestinationTile.getPiece()));
                }
                if(!legalMoves.isEmpty()){
                    Move lastaddedmove = legalMoves.get(legalMoves.size()-1);
//                    System.out.println("Pawn candidate tile I"+lastaddedmove.getTo().getI());
//                    System.out.println("Pawn candidate tile J"+lastaddedmove.getTo().getJ());
                }
            }
        }

        return legalMoves;
    }

    @Override
    public String toString() {
        if (type.equals(PiecesType.WHITE))
            return PieceKind.PAWN.toString();
        else
            return PieceKind.PAWN.toString().toLowerCase();
    }
}
