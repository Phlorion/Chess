package dev.phlorion.chess.engine;

import dev.phlorion.chess.move.Move;

public interface MoveCallback {
    void onMoveSelected(Move move);
}
