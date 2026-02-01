package dev.phlorion.chess;

import dev.phlorion.chess.misc.Vector2;
import dev.phlorion.chess.move.CastlingMove;
import dev.phlorion.chess.move.Move;
import dev.phlorion.chess.pieces.King;
import dev.phlorion.chess.pieces.Piece;
import dev.phlorion.chess.pieces.PieceColor;
import dev.phlorion.chess.pieces.Rook;

import java.util.ArrayList;
import java.util.List;

public class Player {
    final PieceColor type;
    List<Piece> pieces;

    public Player(PieceColor playerType) {
        type = playerType;
    }

    public PieceColor getType() {
        return type;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public List<Move> getPlayerLegalMoves(Board board) {
        List<Move> allLegalsMoves = new ArrayList<>();
        for (Piece p : pieces) {
            List<Move> pieceLegalMoves = p.legalMoves(board);
            pieceLegalMoves.removeIf(m -> !isKingSafeAfterMove(board, m)); // make sure the move doesn't leave the king in check
            allLegalsMoves.addAll(pieceLegalMoves);
        }

        // check for castling
        allLegalsMoves.addAll(castleMoves(board));

        return allLegalsMoves;
    }

    private List<Move> castleMoves(Board board) {
        King king = null;
        List<Rook> rooks = new ArrayList<>();

        List<Move> castlingMoves = new ArrayList<>();

        for (Piece p : pieces) {
            if (p instanceof King && !p.hasMoved())
                king = (King) p;
            if (p instanceof Rook && !p.hasMoved())
                rooks.add((Rook) p);
        }

        // if there are no such pieces on board, or pieces were moved return empty array
        if (king == null || rooks.isEmpty()) return castlingMoves;
        // if king is in checked then return empty array
        if (isKingChecked(board, board.getOpponentPlayer())) return castlingMoves;

        // check if king can move towards the rooks
        for (Rook r : rooks) {
            int dir = king.getPosition().y < r.getPosition().y ? 1 : -1;
            for (int i=1; i<3; i++) {
                Vector2 nextPos = new Vector2(king.getPosition().x, king.getPosition().y + i*dir);
                // if there are pieces between the king and the rook can't castle
                if (board.getPieceAt(nextPos) != null)
                    break;

                // if pieces are attacking the between positions can't castle
                Move move = new Move(king, nextPos);
                if (!isKingSafeAfterMove(board, move))
                    break;

                if (i == 2) {
                    Vector2 rookTargetPosition = new Vector2(nextPos.x, nextPos.y - dir);
                    castlingMoves.add(new CastlingMove(king, nextPos, r, rookTargetPosition));

                }
            }
        }

        return castlingMoves;
    }

    private boolean isKingSafeAfterMove(Board board, Move move) {
        // fake execute the move
        move.execute(board);

        // check if the king is exposed
        Player opponent = board.getOpponentPlayer();
        Vector2 kingPos = board.findKingPositionOnBoard(this.type);
        boolean isKingSafe = !board.isPositionAttacked(opponent, kingPos);

        // redo the move execution
        move.redo(board);

        return isKingSafe;
    }

    public boolean isKingChecked(Board board, Player opponent) {
        Vector2 kingPos = board.findKingPositionOnBoard(this.type);
        return board.isPositionAttacked(opponent, kingPos);
    }

    public boolean isCheckMated(Board board, Player opponent) {
        return isKingChecked(board, opponent) && getPlayerLegalMoves(board).isEmpty();
    }

    public boolean isStaleMated(Board board, Player opponent) {
        return !isKingChecked(board, opponent) && getPlayerLegalMoves(board).isEmpty();
    }

    public void makeMove(Board board, Move move) {
        move.execute(board);
    }

    @Override
    public String toString() {
        return "Player " + type;
    }
}
