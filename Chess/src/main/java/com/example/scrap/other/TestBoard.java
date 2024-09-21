package com.example.scrap.other;

import com.example.chess.board.Board;
import com.example.chess.piece.*;
import com.example.chess.player.Player;

import static com.example.chess.board.Board.NUM_TILES_PER_ROW;

public class TestBoard {

    public static void main(String args[]) {
        Board board = new Board();

//        for (int i = 0; i <8 ; i++) {
//            for (int j = 0; j < 8; j++) {
//                board.getBoard()[NUM_TILES_PER_ROW* i + j].setI(i);
//                board.getBoard()[NUM_TILES_PER_ROW* i + j].setJ(j);
//                //piece is already null - I think
//            }
//        }
        board.setPieceOnBoard(new Rook(PiecesType.BLACK),0,0);
        board.setPieceOnBoard(new Rook(PiecesType.BLACK),0,2);
        board.setPieceOnBoard(new Rook(PiecesType.BLACK),0,7);
        board.setPieceOnBoard(new Knight(PiecesType.BLACK),0,1);
        board.setPieceOnBoard(new King(PiecesType.BLACK),0,4);
        board.setPieceOnBoard(new Queen(PiecesType.BLACK),1,4);
        board.setPieceOnBoard(new King(PiecesType.WHITE),7,4);
        board.setPieceOnBoard(new Rook(PiecesType.WHITE),7,0);
        board.setPieceOnBoard(new Rook(PiecesType.WHITE),7,7);

        Player whitePlayer = board.getWhitePlayer();
        Player blackPlayer = board.getBlackPlayer();

        board.setBlackPieces(board.findAllActivePieces(board.getBoard(), PiecesType.BLACK));
        board.setWhitePieces(board.findAllActivePieces(board.getBoard(), PiecesType.WHITE));

        whitePlayer.setPotentialMoves(whitePlayer.calculateAllPotentialMoves(board.getBoard()));
        blackPlayer.setPotentialMoves(blackPlayer.calculateAllPotentialMoves(board.getBoard()));

        whitePlayer.setLegalMoves(whitePlayer.calculateAllLegalMoves(board.getBoard()));
        blackPlayer.setLegalMoves(blackPlayer.calculateAllLegalMoves(board.getBoard()));

        System.out.println(board);
        System.out.println(blackPlayer.getLegalMoves());
        System.out.println(whitePlayer.getLegalMoves());

        // pieces
        /*Knight nBlack = new Knight(1, 1, PiecesType.BLACK);
        King kBlack = new King(0, 1, PiecesType.BLACK);
        Rook rWhite = new Rook(6, 5, PiecesType.WHITE);
        King kWhite = new King(7, 7, PiecesType.WHITE);

        // create initial board
        Board.Builder builder = new Board.Builder();
        builder.setPiece(nBlack);
        builder.setPiece(kBlack);
        builder.setPiece(rWhite);
        builder.setPiece(kWhite);
        builder.setMoveMaker(PiecesType.BLACK);
        Board initBoard = builder.build();

        System.out.println(initBoard);

        // check assigned with =
        Board newBoard1 = new Board(initBoard);
        System.out.println(initBoard.equals(newBoard1));
        initBoard = initBoard.getCurrentPlayer().makeMove(new RegularMove(initBoard, initBoard.getTile(1, 1), initBoard.getTile(3, 2), nBlack));
        System.out.println("--------------------------AFTER MOVE--------------------------");
        System.out.println("Initial board:");
        System.out.println(initBoard);
        System.out.println("\nNew board:");
        System.out.println(newBoard1);
*/
    }

}
