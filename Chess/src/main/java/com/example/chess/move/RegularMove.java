package com.example.chess.move;

import com.example.chess.board.Board;
import com.example.chess.board.Tile;
import com.example.chess.piece.Piece;

public class RegularMove extends Move {
    public RegularMove(Tile from, Tile to, Piece piece, Tile[] board) {
        super(from, to, piece, board);
    }

    public RegularMove(Move move, Tile[] board) {
        super(move, board);
    }

    @Override
    public Tile[] makeMoveInBoard() {
        Tile[] existingBoard = this.board;
        Tile[] newBoard = new Tile[existingBoard.length];
        for (int i = 0; i < existingBoard.length; i++) {
            //Don't copy the changing piece
            if(this.piece.equals(existingBoard[i].getPiece())){
                newBoard[i] = new Tile();
                newBoard[i].setI(existingBoard[i].getI());
                newBoard[i].setJ(existingBoard[i].getJ());
                continue;
            }
            //Copy all the rest to the new board
            newBoard[i] = new Tile(existingBoard[i].getI(), existingBoard[i].getJ(), existingBoard[i].getPiece());
        }
        //Move the piece
//        piece.setPiecePosI(to.getI());
//        piece.setPiecePosJ(to.getJ());
//        piece.setMoves(piece.getMoves()+1);
//        if (!piece.hasMoved()){
//            piece.setHasMoved(true);
//        }
        newBoard[Board.NUM_TILES_PER_ROW*to.getI()+to.getJ()] = new Tile(to.getI(),to.getJ(),piece);

//        System.out.println("\nExisting Board");
//        for (int i = 0; i < existingBoard.length; i++) {
//            if (i % 8 == 0) System.out.println();
//            System.out.print(existingBoard[i]);
//        }
//        System.out.println("\nNew Board");
//        for (int i = 0; i < newBoard.length; i++) {
//            if (i % 8 == 0) System.out.println();
//            System.out.print(newBoard[i].getI()+","+newBoard[i].getJ()+"-"+newBoard[i].getPiece()+"\t\t\t.");
//        }
//        System.out.println();
        return newBoard;
    }
}
