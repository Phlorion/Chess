package com.example.chess.move;

import com.example.chess.board.Board;
import com.example.chess.board.Tile;
import com.example.chess.piece.Piece;

public class Castle extends Move {
    Piece rook;

    public Castle(Board board, Tile from, Tile to, Piece piece, Piece rook) {
        super(board, from, to, piece);
        this.rook = rook;
    }

    /**
     * Executes the move. This means a new updated board is made.
     * @return The new board
     */
    @Override
    public Board execute(Board board) {
        Board.Builder builder = new Board.Builder();

        // set current player
        for (Piece p : board.getCurrentPlayer().getActivePieces()) {
            if (!this.piece.equals(p) && !this.rook.equals(p)) {
                builder.setPiece(p);
            }
        }

        // set opponent player
        for (Piece p : board.getOpponentPlayer().getActivePieces()) {
            builder.setPiece(p);
        }

        // move the king and the rook
        piece.setPiecePosI(to.getI());
        piece.setPiecePosJ(to.getJ());
        piece.setMoves(piece.getMoves()+1);
        if (!piece.hasMoved()) piece.setHasMoved(true);
        builder.setPiece(piece);

        rook.setPiecePosI(to.getI());
        if (rook.getPiecePosJ() < from.getJ()) rook.setPiecePosJ(to.getJ() + 1);
        else rook.setPiecePosJ(to.getJ() - 1);
        rook.setMoves(rook.getMoves() + 1);
        rook.setHasMoved(true);
        builder.setPiece(rook);

        builder.setLastMoveMade(this);

        // pass the turn to the opponent
        builder.setMoveMaker(board.getOpponentPlayer().getType());

        return builder.build();
    }

    @Override
    public Board fakeExecute(Board board) {
        return null;
    }

    @Override
    public void reverseFakeExecute(Board board) {

    }
}
