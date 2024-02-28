package com.example.scrap;

import com.example.chess.board.Board;
import com.example.chess.board.Tile;
import com.example.chess.move.CaptureMove;
import com.example.chess.move.Move;
import com.example.chess.move.RegularMove;
import com.example.chess.piece.*;

import java.util.Scanner;

public class TestGame {

    public static void main(String[] args) {

        Board board = Board.createStandardBoard();
        /*
        Board.Builder builder = new Board.Builder();
        builder.setPiece(new King(0, 4, PiecesType.BLACK));
        builder.setPiece(new King(7, 4, PiecesType.WHITE));
        builder.setPiece(new Bishop(4, 6, PiecesType.BLACK));
        builder.setPiece(new Knight(6, 4, PiecesType.BLACK));
        builder.setPiece(new Rook(7, 0, PiecesType.WHITE));
        builder.setPiece(new Pawn(6, 0, PiecesType.WHITE));
        builder.setPiece(new Pawn(6, 3, PiecesType.BLACK));
        builder.setMoveMaker(PiecesType.WHITE);
        Board board = builder.build();
         */
        /*
        Board.Builder builder = new Board.Builder();
        builder.setPiece(new Rook(0, 0, PiecesType.BLACK));
        builder.setPiece(new Rook(0, 7, PiecesType.BLACK));
        builder.setPiece(new Rook(7, 0, PiecesType.WHITE));
        builder.setPiece(new Rook(7, 7, PiecesType.WHITE));
        builder.setPiece(new King(0, 4, PiecesType.BLACK));
        builder.setPiece(new King(7, 4, PiecesType.WHITE));
        //builder.setPiece(new Queen(3, 1, PiecesType.BLACK));
        builder.setPiece(new Queen(3, 5, PiecesType.WHITE));
        //builder.setPiece(new Knight(7, 2, PiecesType.WHITE));
        builder.setMoveMaker(PiecesType.WHITE);
        Board board = builder.build();
         */

        System.out.println(board);

        boolean gameLoop = true;

        while(gameLoop) {
            System.out.println(board.getCurrentPlayer() + "'s turn");

            if (board.getCurrentPlayer().isCheckMated()) {
                System.out.println(board.getOpponentPlayer() + " WINS!");
                break;
            } else if (board.getCurrentPlayer().isStaleMated()) {
                System.out.println("TIE");
                break;
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Move: ");

            String input = scanner.nextLine().toLowerCase();

            if (input.equals("stop")) {
                gameLoop = false;
            } else {
                char[] charArray = input.trim().replace(" ", "").toCharArray();
                int fromI = charArray[0] - '0';
                int fromJ = charArray[1] - '0';
                int toI = charArray[2] - '0';
                int toJ = charArray[3] - '0';

                Tile from = board.getTile(fromI, fromJ);
                Tile to = board.getTile(toI, toJ);
                Piece movingPiece = from.getPiece();

                // if there is no piece or the piece does not belong to the player ask again for a move
                if (movingPiece == null || movingPiece.getType() != board.getCurrentPlayer().getType()) {
                    System.out.println("There is no " + board.getCurrentPlayer().getType().toString().toLowerCase() + " piece in (" + fromI + ", " + fromJ + ")");
                    continue;
                }

                boolean validMove = false;
                for (Move m : board.getCurrentPlayer().getAllLegalMoves()) {
                    System.out.println(m);
                    if (m.getFrom().equals(from) && m.getTo().equals(to) && m.getPiece().equals(movingPiece)) {
                        validMove = true;
                        board = board.getCurrentPlayer().makeMove(m);
                        System.out.println(board);
                        break;
                    }
                }
                // if move wasn't found in legal moves
                if (!validMove) System.out.println("Invalid move");

            }
        }

        gameLoop = false;
    }
}
