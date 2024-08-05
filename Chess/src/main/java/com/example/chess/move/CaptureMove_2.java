package com.example.chess.move;

import com.example.chess.board.Board_2;
import com.example.chess.board.Tile;
import com.example.chess.piece.Piece;

public class CaptureMove_2 extends Move_2{
    private Piece capturingPiece;
    public CaptureMove_2(Tile from, Tile to, Piece piece, Tile[] board, Piece capturingPiece) {
        super(from, to, piece, board);
        this.capturingPiece = capturingPiece;
    }

    @Override
    public Tile[] makeMoveInBoard() {
        Tile[] existingBoard = this.board;
        Tile[] newBoard = new Tile[existingBoard.length];
        for (int i = 0; i < existingBoard.length; i++) {
            //Don't copy the changing pieces (attacking and capturing)
            if(this.piece.equals(existingBoard[i].getPiece()) || this.capturingPiece.equals(existingBoard[i].getPiece())){
                newBoard[i] = new Tile();
                newBoard[i].setI(existingBoard[i].getI());
                newBoard[i].setJ(existingBoard[i].getJ());
                continue;
            }
            //Copy all the rest to the new board
            newBoard[i] = new Tile(existingBoard[i].getI(), existingBoard[i].getJ(),existingBoard[i].getPiece());
        }
        //Move the attacking piece
//        piece.setPiecePosI(to.getI());
//        piece.setPiecePosJ(to.getJ());
        piece.setMoves(piece.getMoves()+1);
        if (!piece.hasMoved()){
            piece.setHasMoved(true);
        }
        newBoard[Board_2.NUM_TILES_PER_ROW*to.getI()+to.getJ()] = new Tile(to.getI(),to.getJ(),piece);

        //TODO Remove the captured piece from the Board afterward
        return newBoard;
    }

    public Piece getCapturingPiece() {
        return capturingPiece;
    }

    public void setCapturingPiece(Piece capturingPiece) {
        this.capturingPiece = capturingPiece;
    }
}
