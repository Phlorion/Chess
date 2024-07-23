package com.example.chess.move;

import com.example.chess.board.Board_2;
import com.example.chess.board.Tile;
import com.example.chess.piece.Piece;

public class RegularMove_2 extends Move_2{
    public RegularMove_2(Tile from, Tile to, Piece piece, Tile[] board) {
        super(from, to, piece, board);
    }

    @Override
    public Tile[] makeMoveInBoard() {
        Tile[] existingBoard = this.board;
        Tile[] newBoard = new Tile[existingBoard.length];
        for (int i = 0; i < existingBoard.length; i++) {
            //Don't copy the changing piece
            if(this.piece.equals(existingBoard[i].getPiece())){
                continue;
            }
            //Copy all the rest to the new board
            newBoard[i] = new Tile(existingBoard[i].getI(), existingBoard[i].getJ(),existingBoard[i].getPiece());
        }
        //Move the piece
        piece.setPiecePosI(to.getI());
        piece.setPiecePosJ(to.getJ());
        piece.setMoves(piece.getMoves()+1);
        if (!piece.hasMoved()){
            piece.setHasMoved(true);
        }
        newBoard[Board_2.NUM_TILES_PER_ROW*piece.getPiecePosI()+piece.getPiecePosJ()] = new Tile(piece.getPiecePosI(), piece.getPiecePosJ(),piece);

        return newBoard;
    }
}
