package dev.phlorion.chess.ai;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.move.Move;
import dev.phlorion.chess.pieces.Pawn;
import dev.phlorion.chess.pieces.Piece;
import dev.phlorion.chess.pieces.PieceColor;
import dev.phlorion.chess.pieces.PieceKind;

import java.util.List;

/**
 * Minimax algorithm implementation with Alpha-Beta Pruning.
 */
public class MinimaxAlgorithm implements Algorithm {
    private final int maxDepth;
    private final int thinkingDelay;

    public MinimaxAlgorithm() {
        this(3, 500); // Default search depth 3, 500ms thinking delay
    }

    public MinimaxAlgorithm(int maxDepth) {
        this(maxDepth, 500);
    }

    public MinimaxAlgorithm(int maxDepth, int thinkingDelay) {
        this.maxDepth = maxDepth;
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

        PieceColor aiColor = board.getCurrentPlayer().getType();
        Move bestMove = null;
        int bestValue = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        for (Move move : legalMoves) {
            if (move.getPiece() instanceof Pawn pawn && pawn.canPromote(board, move)) {
                move.setPromotionPiece(PieceKind.QUEEN);
            }

            move.execute(board);
            board.setCurrentPlayer(aiColor.getOpposite());

            int eval = minimax(board, maxDepth - 1, alpha, beta, false, aiColor);

            board.setCurrentPlayer(aiColor);
            move.redo(board);

            if (eval > bestValue) {
                bestValue = eval;
                bestMove = move;
            }
            alpha = Math.max(alpha, eval);
        }

        return bestMove != null ? bestMove : legalMoves.get(0);
    }

    private int minimax(Board board, int depth, int alpha, int beta, boolean isMaximizing, PieceColor aiColor) {
        // Check terminal state (checkmate / stalemate)
        if (board.getCurrentPlayer().isCheckMated(board, board.getOpponentPlayer())) {
            return isMaximizing ? -100000 - depth : 100000 + depth;
        }
        if (board.getCurrentPlayer().isStaleMated(board, board.getOpponentPlayer())) {
            return 0;
        }

        if (depth == 0) {
            return evaluate(board, aiColor);
        }

        List<Move> legalMoves = board.getCurrentPlayer().getPlayerLegalMoves(board);
        if (legalMoves.isEmpty()) {
            return evaluate(board, aiColor);
        }

        PieceColor activeColor = board.getCurrentPlayer().getType();

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (Move move : legalMoves) {
                if (move.getPiece() instanceof Pawn pawn && pawn.canPromote(board, move)) {
                    move.setPromotionPiece(PieceKind.QUEEN);
                }

                move.execute(board);
                board.setCurrentPlayer(activeColor.getOpposite());

                int eval = minimax(board, depth - 1, alpha, beta, false, aiColor);

                board.setCurrentPlayer(activeColor);
                move.redo(board);

                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Move move : legalMoves) {
                if (move.getPiece() instanceof Pawn pawn && pawn.canPromote(board, move)) {
                    move.setPromotionPiece(PieceKind.QUEEN);
                }

                move.execute(board);
                board.setCurrentPlayer(activeColor.getOpposite());

                int eval = minimax(board, depth - 1, alpha, beta, true, aiColor);

                board.setCurrentPlayer(activeColor);
                move.redo(board);

                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }

    private int evaluate(Board board, PieceColor aiColor) {
        int score = 0;

        for (Piece[] row : board.getBoard()) {
            for (Piece piece : row) {
                if (piece != null) {
                    int val = getPieceValue(piece.getPieceKind());
                    if (piece.getPieceColor() == aiColor) {
                        score += val;
                    } else {
                        score -= val;
                    }
                }
            }
        }

        return score;
    }

    private int getPieceValue(PieceKind kind) {
        if (kind == null) return 0;
        return switch (kind) {
            case PAWN -> 100;
            case KNIGHT -> 320;
            case BISHOP -> 330;
            case ROOK -> 500;
            case QUEEN -> 900;
            case KING -> 20000;
        };
    }
}
