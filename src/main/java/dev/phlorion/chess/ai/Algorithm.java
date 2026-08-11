package dev.phlorion.chess.ai;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.move.Move;

public interface Algorithm {
    Move selectMove(Board board);
}
