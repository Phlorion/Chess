package dev.phlorion.chess.engine;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.ai.Algorithm;
import dev.phlorion.chess.move.Move;
import dev.phlorion.chess.pieces.Pawn;
import dev.phlorion.chess.pieces.PieceKind;

import javax.swing.*;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class AIProvider implements MoveProvider {
    Algorithm selectedMethod;

    public AIProvider(Algorithm selectedMethod) {
        this.selectedMethod = selectedMethod;
    }

    @Override
    public void requestMove(Board board, MoveCallback moveCallback) {
        CompletableFuture.runAsync(() -> {
            Move chosenMove = selectedMethod.selectMove(board);

            if (chosenMove != null) {
                // If pawn promotion, choose queen for now
                // TODO: Let the AI decide what it will promote to I guess?
                if (chosenMove.getPiece() instanceof Pawn pawn && pawn.canPromote(board, chosenMove)) {
                    chosenMove.setPromotionPiece(PieceKind.QUEEN);
                }

                SwingUtilities.invokeLater(() -> moveCallback.onMoveSelected(chosenMove));
            }
        });
    }
}
