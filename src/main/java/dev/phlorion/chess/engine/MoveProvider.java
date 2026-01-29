package dev.phlorion.chess.engine;

import dev.phlorion.chess.Board;

public interface MoveProvider {
    void requestMove(Board board, MoveCallback moveCallback);
}
