package dev.phlorion.chess.engine;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.Player;
import dev.phlorion.chess.gui.GridPanel;
import dev.phlorion.chess.move.CastlingMove;
import dev.phlorion.chess.move.Move;
import dev.phlorion.chess.pieces.Pawn;

import javax.sound.sampled.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public class GameEngine {
    private EnginePlayer whitePlayer;
    private EnginePlayer blackPlayer;
    private EnginePlayer currentPlayer;
    private Board board;
    private GridPanel grid;

    public GameEngine(Board board, GridPanel grid, EnginePlayer whitePlayer, EnginePlayer blackPlayer) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.board = board;
        this.grid = grid;

        this.currentPlayer = this.whitePlayer;
    }

    public void start() {
        System.out.println("Engine: Starting game. Waiting for " + currentPlayer.getPlayer().getType());
        System.out.println(board);
        requestCurrentPlayerMove();
    }

    public void nextTurn() {
        this.currentPlayer = (this.currentPlayer == this.whitePlayer) ? this.blackPlayer : this.whitePlayer;
        board.setCurrentPlayer(this.currentPlayer.getPlayer().getType());

        requestCurrentPlayerMove();
    }

    private void requestCurrentPlayerMove() {
        this.currentPlayer.getMoveProvider().requestMove(this.board, (move) -> {
            // make move
            currentPlayer.getPlayer().makeMove(board, move);

            // play sound
            playSound();

            // log
            System.out.println(board);
            System.out.println("Last move performed: " + board.getLastMove());
            if (board.getLastMove().getPiece() instanceof Pawn) {
                System.out.println("Can promote pawn? " + ((Pawn) board.getLastMove().getPiece()).canPromote(board));
            }

            // update UI
            grid.updateUI(board);

            // start next turn
            nextTurn();
        });
    }

    private void playSound() {
        // determine the sound
        URL soundName;
        if (board.getOpponentPlayer().isKingChecked(board, board.getCurrentPlayer())) {
            soundName = getClass().getResource("/sounds/move-check.wav");
        } else if (board.getLastMove() instanceof CastlingMove) {
            soundName = getClass().getResource("/sounds/castle.wav");
        } else if (board.getLastMove().getCapturedPiece() != null) {
            soundName = getClass().getResource("/sounds/capture.wav");
        } else {
            soundName = getClass().getResource("/sounds/move-self.wav");
        }

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName.toURI()).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (URISyntaxException | UnsupportedAudioFileException | LineUnavailableException | IOException exception) {
            System.err.println(exception.getMessage());
        }
    }

    public boolean isCheckmated() {
        return this.getCurrentPlayer().getPlayer().isCheckMated(board, board.getOpponentPlayer());
    }

    public boolean isStaleMated() {
        return this.getCurrentPlayer().getPlayer().isStaleMated(board, board.getOpponentPlayer());
    }

    public EnginePlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getBoard() {
        return board;
    }
}
