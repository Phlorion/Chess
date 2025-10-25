package dev.phlorion.chess;

import dev.phlorion.chess.misc.BoardReader;
import dev.phlorion.chess.misc.Vector2;
import dev.phlorion.chess.pieces.*;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board board = new Board("/home/phlorion/Java/Chess/src/main/resources/test3");
        System.out.println(board);

        boolean endGame = false;

        Scanner scanner = new Scanner(System.in);
        while (!endGame) {
            Player currentPlayer = board.getCurrentPlayer();
            currentPlayer.setPieces(board.getPieces(currentPlayer.getType()));

            System.out.println(currentPlayer + ": ");
            Vector2 currentPos = new Vector2(scanner.nextInt(), scanner.nextInt());
            Vector2 targetPos = new Vector2(scanner.nextInt(), scanner.nextInt());

            Piece selectedPiece = board.getPieceAt(currentPos);

            // check if we own this piece
            if (selectedPiece.getPieceColor() != currentPlayer.getType()) {
                System.out.println("Select " + currentPlayer.getType() + " pieces only.");
                continue;
            }

            // if failed to make the move
            if (currentPlayer.makeMove(board, selectedPiece, targetPos) == null) {
                System.out.println("Illegal move.");
                continue;
            }

            // after making the move, change player turn
            board.setCurrentPlayer(currentPlayer.getType().getOpposite());

            board.flip();
            System.out.println("\n" + board);
        }
    }
}
