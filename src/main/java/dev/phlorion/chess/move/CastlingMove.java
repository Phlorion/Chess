package dev.phlorion.chess.move;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.misc.Vector2;
import dev.phlorion.chess.pieces.Piece;
import dev.phlorion.chess.pieces.Rook;

public class CastlingMove extends Move {
    Rook rook;
    Vector2 rookPreviousPos;
    Vector2 rookTargetPosition;

    public CastlingMove(Piece king, Vector2 kingTargetPosition, Rook rook, Vector2 rookTargetPosition) {
        super(king, kingTargetPosition);

        this.rook = rook;
        this.rookPreviousPos = rook.getPosition();
        this.rookTargetPosition = rookTargetPosition;
    }

    @Override
    public void execute(Board board) {
        // king
        piece.setPosition(targetedPos);
        if (!piece.hasMoved())  {
            piece.setHasMoved(true); // if piece's first move
        }
        board.setOnBoard(previousPos, null);
        board.setOnBoard(targetedPos, piece);

        // rook
        rook.setPosition(rookTargetPosition);
        if (!rook.hasMoved())  {
            rook.setHasMoved(true);
        }
        board.setOnBoard(rookPreviousPos, null);
        board.setOnBoard(rookTargetPosition, rook);

        // save move performed
        board.addMoveToHistory(this);

        // set pieces on both players
        board.getCurrentPlayer().setPieces(board.getPieces(board.getCurrentPlayer().getType()));
        board.getOpponentPlayer().setPieces(board.getPieces(board.getOpponentPlayer().getType()));
    }
}
