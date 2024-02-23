package com.example.chess.move;

import com.example.chess.board.Board;
import com.example.chess.piece.*;
import com.example.chess.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RegularMoveTest {

    Board board;
    Move move;
    Piece piece;
    Piece ally;
    Piece enemy;

    @Before
    public void setUp() {
        piece = new Rook(7, 5, PiecesType.WHITE);
        ally = new Knight(5, 0, PiecesType.WHITE);
        enemy = new Pawn(2, 4, PiecesType.BLACK);
        Board.Builder builder = new Board.Builder();
        builder.setPiece(piece);
        builder.setPiece(ally);
        builder.setPiece(enemy);
        builder.setMoveMaker(PiecesType.WHITE);
        board = builder.build();
        move = new RegularMove(board, board.getTile(7, 5), board.getTile(5, 5), piece);
    }

    @Test
    public void execute() {
        board = move.execute(board);
        // piece
        Assert.assertEquals(5, piece.getPiecePosI());
        Assert.assertEquals(5, piece.getPiecePosJ());
        Assert.assertTrue(piece.hasMoved());
        // ally
        Assert.assertEquals(5, ally.getPiecePosI());
        Assert.assertEquals(0, ally.getPiecePosJ());
        // enemy
        Assert.assertEquals(2, enemy.getPiecePosI());
        Assert.assertEquals(4, enemy.getPiecePosJ());
        assertEquals(board.getCurrentPlayer().getType(), PiecesType.BLACK);
    }
}