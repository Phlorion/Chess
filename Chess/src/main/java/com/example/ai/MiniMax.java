package com.example.ai;

import com.example.chess.piece.PiecesType;

public class MiniMax {

    int depth;

    public MiniMax() {
        depth = 3;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void miniMax(State state, PiecesType max) {}

    private void buildTree(State root) {

    }

    private int maxValue(State state) {
        if (!state.hasChildren())
            return utility(state);

        int v = Integer.MIN_VALUE;

        for (State s : state.getSuccessors()) {
            v = Math.max(v, minValue(s));
        }

        return v;
    }

    private int minValue(State state) {
        if (!state.hasChildren())
            return utility(state);

        int v = Integer.MAX_VALUE;

        for (State s : state.getSuccessors()) {
            v = Math.min(v, maxValue(s));
        }

        return v;
    }

    private int utility(State state) {return 0;}
}
