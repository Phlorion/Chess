package com.example.chess.move;

import com.example.chess.board.Board;
import com.example.chess.piece.Piece;
import com.example.chess.piece.PiecesType;
import com.example.chess.piece.Rook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class MoveTest {

    Board board;
    Move move;

    @Before
    public void setUp() {
        Piece piece = new Rook(7, 5, PiecesType.WHITE);
        Board.Builder builder = new Board.Builder();
        builder.setPiece(piece);
        builder.setMoveMaker(PiecesType.WHITE);
        board = builder.build();
        move = new RegularMove(board, board.getTile(7, 5), board.getTile(5, 5), piece);
    }

    @Test
    public void getFrom() {
        Assert.assertEquals(board.getTile(7, 5), move.getFrom());
    }

    @Test
    public void setFrom() {
        move.setFrom(board.getTile(6, 2));
        Assert.assertEquals(board.getTile(6, 2), move.getFrom());
    }

    @Test
    public void getTo() {
        Assert.assertEquals(board.getTile(5, 5), move.getTo());
    }

    @Test
    public void setTo() {
        move.setTo(board.getTile(2, 5));
        Assert.assertEquals(board.getTile(2, 5), move.getTo());
    }

    @Test
    public void equals() {
        Piece p = board.getTile(7, 5).getPiece();
        for (Move m : p.legalMoves(board)) {
            if (move.equals(m)) {
                Assert.assertTrue(move.equals(m));
            }
        }

        int hash = Objects.hash(move.from, move.to, move.piece, move.board);
        Assert.assertEquals(hash, move.hashCode());
    }

    @Test
    public void _toString() {
        Assert.assertEquals("Piece: R From: (7, 5) To: (5, 5)", move.toString());
    }
}