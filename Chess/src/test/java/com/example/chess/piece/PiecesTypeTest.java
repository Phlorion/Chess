package com.example.chess.piece;

import com.example.chess.board.Board;
import com.example.chess.player.Player;
import com.example.chess.player.PlayerBlack;
import com.example.chess.player.PlayerWhite;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PiecesTypeTest {

    PiecesType piecesTypeWhite;
    PiecesType piecesTypeBlack;
    Player playerWhite;
    Player playerBlack;

    @Before
    public void setUp() {
        piecesTypeWhite = PiecesType.WHITE;
        piecesTypeBlack = PiecesType.BLACK;
        Piece kingWhite = new King(7, 4, PiecesType.WHITE);
        Piece kingBlack = new King(0, 4, PiecesType.BLACK);
        Board.Builder builder = new Board.Builder();
        builder.setMoveMaker(PiecesType.WHITE);
        builder.setPiece(kingWhite);
        builder.setPiece(kingBlack);
        Board board = builder.build();
        playerWhite = new PlayerWhite(board);
        playerBlack = new PlayerBlack(board);
    }

    @Test
    public void getDirection() {
        Assert.assertEquals(1, piecesTypeWhite.getDirection());
        Assert.assertEquals(-1, piecesTypeBlack.getDirection());
    }

    @Test
    public void choosePlayer() {
        Assert.assertEquals(playerWhite, piecesTypeWhite.choosePlayer(playerWhite, playerBlack));
        Assert.assertEquals(playerBlack, piecesTypeBlack.choosePlayer(playerWhite, playerBlack));
    }

    @Test
    public void chooseOpponent() {
        Assert.assertEquals(playerBlack, piecesTypeWhite.chooseOpponent(playerWhite, playerBlack));
        Assert.assertEquals(playerWhite, piecesTypeBlack.chooseOpponent(playerWhite, playerBlack));
    }

    @Test
    public void _toString() {
        Assert.assertEquals("WHITE", piecesTypeWhite.toString());
        Assert.assertEquals("BLACK", piecesTypeBlack.toString());
    }
}