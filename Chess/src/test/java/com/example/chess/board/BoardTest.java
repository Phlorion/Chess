package com.example.chess.board;

import com.example.chess.piece.*;
import com.example.chess.player.Player;
import com.example.chess.player.PlayerBlack;
import com.example.chess.player.PlayerWhite;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class BoardTest {

    Board board;
    Piece blackKing;
    Piece whiteKing;
    Piece blackRook;
    Piece whiteQueen;
    Piece whiteBishop;

    @Before
    public void setUp() {
        blackKing = new King(0, 4, PiecesType.BLACK);
        whiteKing = new King(7, 4, PiecesType.WHITE);
        blackRook = new Rook(7, 5, PiecesType.BLACK);
        whiteQueen = new Queen(5, 6, PiecesType.WHITE);
        whiteBishop = new Bishop(5, 5, PiecesType.WHITE);
        Board.Builder builder = new Board.Builder();
        builder.setPiece(blackKing);
        builder.setPiece(whiteKing);
        builder.setPiece(blackRook);
        builder.setPiece(whiteQueen);
        builder.setPiece(whiteBishop);
        builder.setMoveMaker(PiecesType.WHITE);
        board = builder.build();
    }

    @Test
    public void getWhitePieces() {
        Collection<Piece> pieces = new ArrayList<>();
        pieces.add(whiteQueen);
        pieces.add(whiteKing);
        Assert.assertEquals(pieces, board.getWhitePieces());
    }

    @Test
    public void getBlackPieces() {
        Collection<Piece> pieces = new ArrayList<>();
        pieces.add(blackKing);
        pieces.add(blackRook);
        Assert.assertEquals(pieces, board.getBlackPieces());
    }

    @Test
    public void getPlayerWhite() {
        Player white = new PlayerWhite(board);
        Assert.assertEquals(white, board.getPlayerWhite());
    }

    @Test
    public void getPlayerBlack() {
        Player black = new PlayerBlack(board);
        Assert.assertEquals(black, board.getPlayerBlack());
    }

    @Test
    public void getCurrentPlayer() {
        Assert.assertEquals(board.getPlayerWhite(), board.getCurrentPlayer());
    }

    @Test
    public void getOpponentPlayer() {
        Assert.assertEquals(board.getPlayerBlack(), board.getOpponentPlayer());
    }

    @Test
    public void createStandardBoard() {
    }

    @Test
    public void getAllTiles() {
    }

    @Test
    public void isValidCoordinate() {
        Assert.assertTrue(Board.isValidCoordinate(5, 5));
        Assert.assertFalse(Board.isValidCoordinate(8, 10));
    }

    @Test
    public void getTileByPiece() {
        Tile tile = board.getTile(0, 4);
        Assert.assertEquals(tile, board.getTileByPiece(blackKing));
    }

    @Test
    public void getTile() {
        Tile tile = board.getTile(0, 4);
        Assert.assertEquals(board.board.get(4), tile);
    }

    @Test
    public void _toString() {
    }
}