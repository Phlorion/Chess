package com.example.ai;

import com.example.chess.board.Board;
import com.example.chess.move.Move;
import com.example.chess.piece.PiecesType;
import com.example.chess.player.Player;

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

    public void buildTree(State root, int currentLevel) {
        Player currentPlayer = root.getBoard().getCurrentPlayer();
        int i = 0;
        for (Move m : currentPlayer.getLegalMoves()) {
            Board copy = new Board(root.getBoard());
            Move copiedMove = currentPlayer.getMoveByID(m.getUid());
            copy.makeMove(copiedMove);

            System.out.println((currentLevel+1) + " " + i);
            System.out.println(copy);
            i++;

            State successor = new State(copy);
            root.getSuccessors().add(successor);

            if (currentLevel < depth - 1) {
                buildTree(successor, currentLevel + 1);
            }
        }
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
