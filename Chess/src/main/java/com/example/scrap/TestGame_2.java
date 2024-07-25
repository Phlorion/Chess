package com.example.scrap;

import com.example.chess.board.Board_2;
import com.example.chess.board.Tile;
import com.example.chess.move.Move_2;
import com.example.chess.piece.Piece;

import java.util.Scanner;

public class TestGame_2 {
    public static void main(String[] args) {
        Board_2 board = new Board_2();
        board.setStartingPieces();

        board.getWhitePlayer().setPotentialMoves(board.getWhitePlayer().calculateAllPotentialMoves());
        board.getBlackPlayer().setPotentialMoves(board.getBlackPlayer().calculateAllPotentialMoves());
        board.getWhitePlayer().setLegalMoves(board.getWhitePlayer().calculateAllLegalMoves());
        board.getBlackPlayer().setLegalMoves(board.getBlackPlayer().calculateAllLegalMoves());
        Move_2 move = board.getWhitePlayer().getLegalMoves().get(0);

        System.out.println(board);
        boolean gameLoop = true;
        while(gameLoop) {
            System.out.println(board.getCurrentPlayer().getType() + "'s turn");

            if (board.getCurrentPlayer().isCheckMated()) {
                System.out.println(/*board.getOpponentPlayer() +*/ "Opponent WINS!");
                break;
            } else if (board.getCurrentPlayer().isStaleMated()) {
                System.out.println("TIE");
                break;
            }
            System.out.println("Current Player's Moves");
            for (int i = 0; i < board.getCurrentPlayer().getLegalMoves().size(); i++) {
                System.out.println("MOVE "+i+":"+board.getCurrentPlayer().getLegalMoves().get(i));
            }
            System.out.println("White Player's Moves");
            for (int i = 0; i < board.getCurrentPlayer().getLegalMoves().size(); i++) {
                System.out.println("MOVE "+i+":"+board.getWhitePlayer().getLegalMoves().get(i));
            }
            System.out.println("Black Player's Moves");
            for (int i = 0; i < board.getCurrentPlayer().getLegalMoves().size(); i++) {
                System.out.println("MOVE "+i+":"+board.getBlackPlayer().getLegalMoves().get(i));
            }
            Scanner scanner = new Scanner(System.in);
            System.out.print("Choose a move: ");

            String input = scanner.nextLine().toLowerCase();
            if (input.equals("stop")) {
                gameLoop = false;
            }else {
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
                }
                boolean validMove = false;
                for (Move_2 m : board.getCurrentPlayer().getLegalMoves()) {
                    System.out.println(m);
                    if (m.getFrom().equals(from) && m.getTo().equals(to) && m.getPiece().equals(movingPiece)) {
                        validMove = true;
                        board.makeMove(m);
                        System.out.println(board);
                        break;
                    }
                }
                // if move wasn't found in legal moves
                if (!validMove) System.out.println("Invalid move");
            }
        }
    }
}
