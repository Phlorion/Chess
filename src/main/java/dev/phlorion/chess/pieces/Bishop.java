package dev.phlorion.chess.pieces;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.misc.Vector2;
import dev.phlorion.chess.move.Move;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends SlidingPiece {

    public Bishop() {
        super();
        pieceKind = PieceKind.BISHOP;

        CANDIDATE_VECTOR_MOVE_COORDINATES = new ArrayList<>(List.of(
                new Vector2(-1, -1),
                new Vector2(-1, 1),
                new Vector2(1, -1),
                new Vector2(1, 1)
        ));
    }

    public Bishop(int x, int y, PieceColor color) {
        super(x, y, color);
        pieceKind = PieceKind.BISHOP;

        CANDIDATE_VECTOR_MOVE_COORDINATES = new ArrayList<>(List.of(
                new Vector2(-1, -1),
                new Vector2(-1, 1),
                new Vector2(1, -1),
                new Vector2(1, 1)
        ));
    }
}
