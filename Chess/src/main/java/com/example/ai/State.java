package com.example.ai;

import com.example.chess.board.Board;
import com.example.chess.move.Move;

import java.util.ArrayList;
import java.util.Collection;

public class State {

    Board board;
    Move move;
    int score;
    Collection<State> successors;

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

    public Collection<State> getSuccessors() {
        return successors;
    }

    public void setSuccessors(Collection<State> successors) {
        this.successors = successors;
    }

    public boolean hasChildren() {
        return !successors.isEmpty();
    }
}
