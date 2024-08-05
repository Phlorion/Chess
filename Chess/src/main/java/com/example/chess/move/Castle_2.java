package com.example.chess.move;

import com.example.chess.board.Board_2;
import com.example.chess.board.Tile;
import com.example.chess.piece.Piece;

public class Castle_2 extends Move_2{
    Piece castlingRook;
    Tile castlingRookTile;
    public Castle_2(Tile from, Tile to, Piece piece, Tile[] board, Tile castlingRookTile) {
        super(from, to, piece, board);
        this.castlingRookTile = castlingRookTile;
        this.castlingRook = castlingRookTile.getPiece();
    }

    @Override
    public Tile[] makeMoveInBoard() {
        Tile[] existingBoard = this.board;
        Tile[] newBoard = new Tile[existingBoard.length];
        for (int i = 0; i < existingBoard.length; i++) {
            //Don't copy the changing pieces (piece[king] and castlingRook)
            if(this.piece.equals(existingBoard[i].getPiece()) || this.castlingRook.equals(existingBoard[i].getPiece())){
                newBoard[i] = new Tile();
                newBoard[i].setI(existingBoard[i].getI());
                newBoard[i].setJ(existingBoard[i].getJ());
                continue;
            }
            //Copy all the rest to the new board
            newBoard[i] = new Tile(existingBoard[i].getI(), existingBoard[i].getJ(),existingBoard[i].getPiece());
        }
        //Move the piece[king]
//        piece.setPiecePosI(to.getI());
//        piece.setPiecePosJ(to.getJ());
//        piece.setMoves(piece.getMoves()+1);
//        if (!piece.hasMoved()){
//            piece.setHasMoved(true);
//        }
        newBoard[Board_2.NUM_TILES_PER_ROW*to.getI()+to.getJ()] = new Tile(to.getI(),to.getJ(),piece);

        //Move the castlingRook
//        castlingRook.setPiecePosI(to.getI());
//        if (castlingRook.getPiecePosJ() < from.getJ()){
//            castlingRook.setPiecePosJ(to.getJ() + 1);
//        }else {
//            castlingRook.setPiecePosJ(to.getJ() - 1);
//        }
        if(castlingRookTile.getJ() < from.getJ()){
            newBoard[Board_2.NUM_TILES_PER_ROW* to.getI()+ to.getJ()+1].setPiece(castlingRook);
        }else{
            newBoard[Board_2.NUM_TILES_PER_ROW* to.getI()+ to.getJ()-1].setPiece(castlingRook);
        }
//        castlingRook.setMoves(castlingRook.getMoves() + 1);
//        castlingRook.setHasMoved(true);
        return newBoard;
    }

    public Piece getCastlingRook() {
        return castlingRook;
    }

    public Tile getCastlingRookTile() {
        return castlingRookTile;
    }
}
