package dev.phlorion.chess.engine;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.move.Move;

public class HumanProvider implements MoveProvider {
    private MoveCallback pendingCallback;

    @Override
    public void requestMove(Board board, MoveCallback moveCallback) {
        this.pendingCallback = moveCallback;
    }

    public void submitMove(Move move) {
        if (pendingCallback != null) {
            pendingCallback.onMoveSelected(move);
            pendingCallback = null;
        }
    }
}
