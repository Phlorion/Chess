package dev.phlorion.chess.pieces;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.misc.Vector2;
import dev.phlorion.chess.move.Move;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    private List<Vector2> CANDIDATE_VECTOR_MOVE_COORDINATES = new ArrayList<>(List.of(
            new Vector2(0, -1),
            new Vector2(0, 1),
            new Vector2(-1, 0),
            new Vector2(1, 0)
    ));

    public Rook() {
        super();
        pieceKind = PieceKind.ROOK;
    }

    public Rook(int x, int y, PieceColor color) {
        super(x, y, color);
        pieceKind = PieceKind.ROOK;
    }

    public List<Move> legalMoves(Board board) {
        List<Move> possibleMoves = new ArrayList<>();
        for (Vector2 dir : CANDIDATE_VECTOR_MOVE_COORDINATES) {
            Vector2 targetPos = Vector2.add(position, dir);
            // while we can travel to that direction keep adding to the possible moves list
            while (board.inBounds(targetPos) && !board.isOccupied(targetPos, pieceColor)) {
                possibleMoves.add(new Move(this, targetPos));
                // if we capture an enemy piece then stop
                if (board.isOccupied(targetPos, pieceColor.getOpposite()))
                    break;

                targetPos = Vector2.add(targetPos, dir); // move to the next tile in this direction
            }
        }

        return possibleMoves;
    }
}
