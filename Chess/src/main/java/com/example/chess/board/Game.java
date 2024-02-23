package com.example.chess.board;

import com.example.chess.board.Board;
import com.example.chess.move.CaptureMove;
import com.example.chess.move.Move;
import com.example.chess.move.RegularMove;
import com.example.chess.piece.Piece;

import java.util.Scanner;

public class Game {

    public static void main(String[] args) {

        Board board = Board.createStandardBoard();
        System.out.println(board);

        boolean gameLoop = true;

        while(gameLoop) {
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

                // if piece has no legal moves
                if (movingPiece.legalMoves(board).isEmpty()) {
                    System.out.println("Piece " + movingPiece.toString() + " has no legal moves");
                    continue;
                }

                boolean validMove = false;
                for (Move m : movingPiece.legalMoves(board)) {
                    System.out.println(m);
                    if (m.getFrom().equals(from) && m.getTo().equals(to)) {
                        validMove = true;
                        board = board.currentPlayer.makeMove(m);
                        System.out.println(board);
                        System.out.println(board.getCurrentPlayer() + "'s turn");
                        break;
                    }
                }
                // if move wasn't found in legal moves
                if (!validMove) System.out.println("Invalid move");

            }
        }

    }
}
