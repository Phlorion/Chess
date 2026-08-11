package dev.phlorion.chess.ai;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.move.Move;

import java.util.List;
import java.util.Random;

/**
 * Performs a legal random move
 */
public class RandomChoiceAlgorithm implements Algorithm {
    private final Random random = new Random();
    // delay the output in ms
    private int thinkingDelay;

    public RandomChoiceAlgorithm(int thinkingDelay) {
        this.thinkingDelay = thinkingDelay;
    }

    @Override
    public Move selectMove(Board board) {
        if (thinkingDelay > 0) {
            try {
                Thread.sleep(thinkingDelay);
            } catch (InterruptedException ignored) {}
        }

        List<Move> legalMoves = board.getCurrentPlayer().getPlayerLegalMoves(board);
        if (legalMoves.isEmpty()) return null;

        return legalMoves.get(random.nextInt(legalMoves.size()));
    }
}
