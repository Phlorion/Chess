package com.example.ai;

import com.example.chess.board.Board;
import com.example.chess.move.Move;

public class State {

    Board board;
    Move move;
    int score;

    public State(Board board) {
        this.board = board;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Move getMove() {
        return move;
    }
}
