package com.example.chess.move;

import com.example.chess.board.Board;
import com.example.chess.piece.Piece;
import com.example.chess.board.Tile;

public class CaptureMove extends Move {
    Piece capturingPiece;

    public CaptureMove(Board board, Tile from, Tile to, Piece piece, Piece capturingPiece) {
        super(board, from, to, piece);
        this.capturingPiece = capturingPiece;
    }

    /**
     * Executes the move. This means a new updated board is made.
     * @return The new board
     */
    @Override
    public Board execute() {
        Board.Builder builder = new Board.Builder();

        // set current player
        for (Piece p : this.board.getCurrentPlayer().getActivePieces()) {
            if (!this.piece.equals(p)) {
                builder.setPiece(p);
            }
        }

        // set opponent player
        for (Piece p : this.board.getOpponentPlayer().getActivePieces()) {
            if (!this.capturingPiece.equals(p)) {
                builder.setPiece(p);
            }
        }

        // move the moved piece
        piece.setPiecePosI(to.getI());
        piece.setPiecePosJ(to.getJ());
        if (!piece.hasMoved()) piece.setHasMoved(true);
        builder.setPiece(piece);
        // remove the captured piece
        this.board.getOpponentPlayer().getActivePieces().remove(capturingPiece);

        // pass the turn to the opponent
        builder.setMoveMaker(this.board.getOpponentPlayer().getType());

        return builder.build();
    }
}
