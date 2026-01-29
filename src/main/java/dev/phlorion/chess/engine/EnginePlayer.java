package dev.phlorion.chess.engine;

import dev.phlorion.chess.Player;

public class EnginePlayer {
    private Player player;
    private MoveProvider moveProvider;

    public EnginePlayer(Player player, MoveProvider moveProvider) {
        this.player = player;
        this.moveProvider = moveProvider;
    }

    public Player getPlayer() {
        return player;
    }

    public MoveProvider getMoveProvider() {
        return moveProvider;
    }

    @Override
    public String toString() {
        return player.toString();
    }
}
