package com.example.chess.move;

import com.example.chess.board.Board;
import com.example.chess.piece.Pawn;
import com.example.chess.piece.Piece;
import com.example.chess.piece.PiecesType;
import com.example.chess.piece.Rook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CompareMovesTest {

    Board board;
    Move move1;
    Move move2;
    Move.CompareMoves comparator;

    @Before
    public void setUp() {
        comparator = new Move.CompareMoves();
        Piece piece1 = new Pawn(5, 5, PiecesType.WHITE);
        Piece piece2 = new Rook(7, 0, PiecesType.BLACK);
        Board.Builder builder = new Board.Builder();
        builder.setPiece(piece1);
        builder.setPiece(piece2);
        builder.setMoveMaker(PiecesType.WHITE);
        board = builder.build();
        move1 = new RegularMove(board, board.getTile(5, 5), board.getTile(6, 5), piece1);
        move2 = new RegularMove(board, board.getTile(7, 0), board.getTile(0, 0), piece2);
    }

    @Test
    public void compareTest() {
        int move1Pos = Board.NUM_TILES_PER_ROW * move1.getTo().getI() + move1.getTo().getJ();
        int move2Pos = Board.NUM_TILES_PER_ROW * move2.getTo().getI() + move2.getTo().getJ();
        Assert.assertEquals(move1Pos - move2Pos, comparator.compare(move1, move2));
    }
}