package com.example.chess.move;

import com.example.chess.board.Board;
import com.example.chess.piece.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CaptureMoveTest {

    Board board;
    Move move;
    Piece piece;
    Piece ally;
    Piece enemy;
    Piece enemy2;

    @Before
    public void setUp() {
        piece = new Rook(7, 5, PiecesType.WHITE);
        ally = new Knight(5, 0, PiecesType.WHITE);
        enemy = new Pawn(2, 5, PiecesType.BLACK);
        enemy2 = new Bishop(0, 0, PiecesType.BLACK);
        Board.Builder builder = new Board.Builder();
        builder.setPiece(piece);
        builder.setPiece(ally);
        builder.setPiece(enemy);
        builder.setPiece(enemy2);
        builder.setMoveMaker(PiecesType.WHITE);
        board = builder.build();
        move = new CaptureMove(board, board.getTile(7, 5), board.getTile(2, 5), piece, enemy);
    }

    @Test
    public void execute() {
        board = move.execute();
        // piece
        Assert.assertEquals(2, piece.getPiecePosI());
        Assert.assertEquals(5, piece.getPiecePosJ());
        Assert.assertTrue(piece.hasMoved());
        // ally
        Assert.assertEquals(5, ally.getPiecePosI());
        Assert.assertEquals(0, ally.getPiecePosJ());
        // enemy
        Assert.assertNull(board.getTileByPiece(enemy));
        // enemy 2
        Assert.assertEquals(0, enemy2.getPiecePosI());
        Assert.assertEquals(0, enemy2.getPiecePosJ());
    }
}