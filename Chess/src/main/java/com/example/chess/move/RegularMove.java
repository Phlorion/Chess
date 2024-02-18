package com.example.chess.move;

import com.example.chess.board.Board;
import com.example.chess.piece.Piece;
import com.example.chess.board.Tile;

public class RegularMove extends Move {

    public RegularMove(Board board, Tile from, Tile to, Piece piece) {
        super(board, from, to, piece);
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
            builder.setPiece(p);
        }

        // move the moved piece
        piece.setPiecePosI(to.getI());
        piece.setPiecePosJ(to.getJ());
        if (!piece.hasMoved()) piece.setHasMoved(true);
        builder.setPiece(piece);

        // pass the turn to the opponent
        builder.setMoveMaker(this.board.getOpponentPlayer().getType());

        return builder.build();
    }
}
