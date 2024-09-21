package com.example.ai;

import com.example.chess.board.Board;
import com.example.chess.move.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class State {

    Board board;
    Move move;
    int score;
    List<State> successors;

    public State(Board board) {
        this.board = board;
        successors = new ArrayList<>();
    }

    public Board getBoard() {
        return board;
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

    public void setSuccessors(List<State> successors) {
        this.successors = successors;
    }

    public boolean hasChildren() {
        return !successors.isEmpty();
    }
}
