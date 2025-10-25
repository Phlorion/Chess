package dev.phlorion.chess.pieces;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.misc.Vector2;
import dev.phlorion.chess.move.Move;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    private final List<Vector2> CANDIDATE_MOVE_COORDINATES = new ArrayList<>(List.of(
            new Vector2(-1, -1),
            new Vector2(0, -1),
            new Vector2(1, -1),
            new Vector2(-1, 0),
            new Vector2(1, 0),
            new Vector2(-1, 1),
            new Vector2(0, 1),
            new Vector2(1, 1)
    ));

    public King() {
        super();
        pieceKind = PieceKind.KING;
    }

    public King(int x, int y, PieceColor color) {
        super(x, y, color);
        pieceKind = PieceKind.KING;
    }

    @Override
    public List<Move> legalMoves(Board board) {
        List<Move> possibleMoves = new ArrayList<>();
        for (Vector2 candidateMove : CANDIDATE_MOVE_COORDINATES) {
            Vector2 targetPos = Vector2.add(position, candidateMove);
            if (board.inBounds(targetPos) && !board.isOccupied(targetPos, pieceColor))
                possibleMoves.add(new Move(this, targetPos));
        }

        return possibleMoves;
    }
}
