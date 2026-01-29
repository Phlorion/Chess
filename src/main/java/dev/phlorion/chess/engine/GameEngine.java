package dev.phlorion.chess.engine;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.move.Move;

import java.awt.event.WindowEvent;

public class GameEngine {
    private EnginePlayer whitePlayer;
    private EnginePlayer blackPlayer;
    private EnginePlayer currentPlayer;
    private Board board;

    public GameEngine(Board board, EnginePlayer whitePlayer, EnginePlayer blackPlayer) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.board = board;

        this.currentPlayer = this.whitePlayer;
    }

    public void start() {
        System.out.println("Engine: Starting game. Waiting for " + currentPlayer.getPlayer().getType());
        requestCurrentPlayerMove();
    }

    public void nextTurn() {
        this.currentPlayer = (this.currentPlayer == this.whitePlayer) ? this.blackPlayer : this.whitePlayer;
        board.setCurrentPlayer(this.currentPlayer.getPlayer().getType());

        requestCurrentPlayerMove();
    }

    private void requestCurrentPlayerMove() {
        this.currentPlayer.getMoveProvider().requestMove(this.board, (move) -> {
            currentPlayer.getPlayer().makeMove(board, move);
            System.out.println(board);
            nextTurn();
        });
    }

    public boolean isCheckmated() {
        return this.getCurrentPlayer().getPlayer().isCheckMated(board, board.getOpponentPlayer());
    }

    public EnginePlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getBoard() {
        return board;
    }
}
