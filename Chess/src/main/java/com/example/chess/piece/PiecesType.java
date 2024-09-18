package com.example.chess.piece;

import com.example.chess.player.Player;

public enum PiecesType {
    WHITE {
        @Override
        public int getDirection() {
            return 1;
        }

        @Override
        public Player choosePlayer(Player whitePlayer, Player blackPlayer) {
            return whitePlayer;
        }

        @Override
        public Player chooseOpponent(Player whitePlayer, Player blackPlayer) {
            return blackPlayer;
        }

        @Override
        public String toString() {
            return "WHITE";
        }
    },
    BLACK {
        @Override
        public int getDirection() {return -1;}

        @Override
        public Player choosePlayer(Player whitePlayer, Player blackPlayer) {
            return blackPlayer;
        }

        @Override
        public Player chooseOpponent(Player whitePlayer, Player blackPlayer) {
            return whitePlayer;
        }

        @Override
        public String toString() {
            return "BLACK";
        }
    };

    public abstract int getDirection();
    public abstract Player choosePlayer(Player whitePlayer, Player blackPlayer);
    public abstract Player chooseOpponent(Player whitePlayer, Player blackPlayer);
}
