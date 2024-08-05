package com.example.chess.piece;

import com.example.chess.player.Player_2;

public enum PiecesType {
    WHITE {
        @Override
        public int getDirection() {
            return 1;
        }

        @Override
        public Player_2 choosePlayer(Player_2 whitePlayer, Player_2 blackPlayer) {
            return whitePlayer;
        }

        @Override
        public Player_2 chooseOpponent(Player_2 whitePlayer, Player_2 blackPlayer) {
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
        public Player_2 choosePlayer(Player_2 whitePlayer, Player_2 blackPlayer) {
            return blackPlayer;
        }

        @Override
        public Player_2 chooseOpponent(Player_2 whitePlayer, Player_2 blackPlayer) {
            return whitePlayer;
        }

        @Override
        public String toString() {
            return "BLACK";
        }
    };

    public abstract int getDirection();
    public abstract Player_2 choosePlayer(Player_2 whitePlayer, Player_2 blackPlayer);
    public abstract Player_2 chooseOpponent(Player_2 whitePlayer, Player_2 blackPlayer);
}
