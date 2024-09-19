package com.example.scrap.game;

import com.example.chess.board.Board;
import com.example.chess.board.Tile;
import com.example.chess.move.Move;
import com.example.chess.piece.Piece;
import com.example.chess.player.Player;

import java.util.Scanner;

public class PvAITestGame implements TestGame {
    @Override
    public void startGameLoop() {
        Board board = new Board();
        board.setStartingPieces();

        Player player = board.getWhitePlayer();
        Player ai = board.getBlackPlayer();

        player.setLegalMoves(player.calculateAllLegalMoves(board.getBoard()));
        ai.setLegalMoves(ai.calculateAllLegalMoves(board.getBoard()));

        System.out.println(board);

        boolean gameLoop = true;
        while (gameLoop) {
            Player currentPlayer = board.getCurrentPlayer();
            Player opponentPlayer = board.getOpponentPlayer();
            System.out.println(currentPlayer.getType() + "'s turn");

            if (currentPlayer.getCheckMated()) {
                System.out.println(opponentPlayer.getType() + " wins");
                break;
            } else if (currentPlayer.getStaleMated()) {
                System.out.println("TIE");
                break;
            }

            if (player.equals(currentPlayer)) {
                // if player gave input "stop" game loop is set to false
                gameLoop = playerTurn(board);
            }
        }
    }

    /**
     * If it is player's turn, same behaviour with the pvp class
     * @param board The board where the game is played
     * @return True if player made a move
     */
    private boolean playerTurn(Board board) {
        System.out.println("Current Player's Moves");
        for (int i = 0; i < board.getCurrentPlayer().getLegalMoves().size(); i++) {
            System.out.println("MOVE "+i+":"+board.getCurrentPlayer().getLegalMoves().get(i));
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose a move: ");
        String input = scanner.nextLine().toLowerCase();

        // if player enters "stop" stop the game loop
        boolean keepPlaying = true;
        if (input.equals("stop")) {
            keepPlaying = false;
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
            for (Move m : board.getCurrentPlayer().getLegalMoves()) {
                System.out.print(m);
                System.out.println(m.getPiece().equals(movingPiece));
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

        return keepPlaying;
    }
}
