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
    public Board execute(Board board) {
        Board.Builder builder = new Board.Builder();

        // set current player
        for (Piece p : board.getCurrentPlayer().getActivePieces()) {
            if (!this.piece.equals(p)) {
                builder.setPiece(p);
            }
        }

        // set opponent player
        for (Piece p : board.getOpponentPlayer().getActivePieces()) {
            if (!this.capturingPiece.equals(p)) {
                builder.setPiece(p);
            }
        }

        // move the moved piece
        piece.setPiecePosI(to.getI());
        piece.setPiecePosJ(to.getJ());
        piece.setMoves(piece.getMoves()+1);
        if (!piece.hasMoved()) piece.setHasMoved(true);
        builder.setPiece(piece);
        // remove the captured piece
        board.getOpponentPlayer().getActivePieces().remove(capturingPiece);

        // pass the turn to the opponent
        builder.setMoveMaker(board.getOpponentPlayer().getType());

        return builder.build();
    }

    @Override
    public Board fakeExecute(Board board) {
        Board.Builder builder = new Board.Builder();

        // set current player
        for (Piece p : board.getCurrentPlayer().getActivePieces()) {
            if (!this.piece.equals(p)) {
                builder.setPiece(p);
            }
        }

        // set opponent player
        for (Piece p : board.getOpponentPlayer().getActivePieces()) {
            if (!this.capturingPiece.equals(p)) {
                builder.setPiece(p);
            }
        }

        // move the moved piece
        piece.setPiecePosI(to.getI());
        piece.setPiecePosJ(to.getJ());
        piece.setMoves(piece.getMoves()+1);
        if (!piece.hasMoved()) piece.setHasMoved(true);
        builder.setPiece(piece);
        // remove the captured piece
        board.getOpponentPlayer().getActivePieces().remove(capturingPiece);

        // pass the turn to the opponent
        builder.setMoveMaker(board.getCurrentPlayer().getType());

        return builder.fakeBuild();
    }

    @Override
    public Board reverseFakeExecute(Board board) {
        Board.Builder builder = new Board.Builder();

        // set current player
        for (Piece p : board.getCurrentPlayer().getActivePieces()) {
            if (!this.piece.equals(p)) {
                builder.setPiece(p);
            }
        }

        // set opponent player
        for (Piece p : board.getOpponentPlayer().getActivePieces()) {
            builder.setPiece(p);
        }

        // move the moved piece
        piece.setPiecePosI(from.getI());
        piece.setPiecePosJ(from.getJ());
        piece.setMoves(piece.getMoves()-1);
        if (piece.getMoves() == 0) piece.setHasMoved(false);
        builder.setPiece(piece);
        // undo removed captured piece
        board.getOpponentPlayer().getActivePieces().add(capturingPiece);
        builder.setPiece(capturingPiece);

        // pass the turn to the opponent
        builder.setMoveMaker(board.getCurrentPlayer().getType());

        return builder.fakeBuild();
    }
}
