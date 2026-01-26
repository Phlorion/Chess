package dev.phlorion.chess.pieces;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.misc.Vector2;
import dev.phlorion.chess.move.Move;

import java.util.ArrayList;
import java.util.List;

public class Queen extends SlidingPiece {

    public Queen() {
        super();
        pieceKind = PieceKind.QUEEN;

        CANDIDATE_VECTOR_MOVE_COORDINATES = new ArrayList<>(List.of(
                new Vector2(-1, 0),
                new Vector2(-1, -1),
                new Vector2(-1, 1),
                new Vector2(1, 0),
                new Vector2(1, -1),
                new Vector2(1, 1),
                new Vector2(0, -1),
                new Vector2(0, 1)
        ));
    }

    public Queen(int x, int y, PieceColor color) {
        super(x, y, color);
        pieceKind = PieceKind.QUEEN;

        CANDIDATE_VECTOR_MOVE_COORDINATES = new ArrayList<>(List.of(
                new Vector2(-1, 0),
                new Vector2(-1, -1),
                new Vector2(-1, 1),
                new Vector2(1, 0),
                new Vector2(1, -1),
                new Vector2(1, 1),
                new Vector2(0, -1),
                new Vector2(0, 1)
        ));
    }
}
