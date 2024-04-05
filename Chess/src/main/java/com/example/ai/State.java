package com.example.ai;

import com.example.chess.board.Board;
import com.example.chess.move.Move;
import com.example.chess.piece.Piece;

import java.util.ArrayList;
import java.util.Collection;

public class State {
    private Board root;
    private ArrayList<Board> childred;

    public State(Board root) {
        this.root = root;
        this.childred = calculateChildren();
    }

    /**
     * Calculates all the possible outcomes from the root.
     * @return An array with all the possible states after making each possible move.
     */
    private ArrayList<Board> calculateChildren() {
        ArrayList<Board> temp = new ArrayList<>();

        // get all legal moves of the current player
        Collection<Move> legalMoves = this.root.getCurrentPlayer().getAllLegalMoves();
        // iterate through all of them
        for (Move move : legalMoves) {
            // create a copy of the root board
            Board tempBoard = new Board(this.root);
            // fake execute the move on the copy board
            tempBoard = move.fakeExecute(tempBoard);
            // add it to the list
            temp.add(tempBoard);
            // undo the move
            move.reverseFakeExecute(tempBoard);
        }

        return temp;
    }

    public Board getRoot() {
        return root;
    }

    public ArrayList<Board> getChildred() {
        return childred;
    }
}
