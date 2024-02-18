package com.example.chess.board;

import com.example.chess.piece.Pawn;
import com.example.chess.piece.Piece;
import com.example.chess.piece.PiecesType;
import com.example.chess.piece.Rook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class TileTest {

    Tile tileDefault;
    Piece myPiece;
    Tile myTile;

    @Before
    public void setUp() {
        tileDefault = new Tile();
        myPiece = new Pawn(4, 4, PiecesType.WHITE);
        myTile = new Tile(4, 4, myPiece);
    }

    @Test
    public void getI() {
        Assert.assertEquals(0, tileDefault.getI());
        Assert.assertEquals(4, myTile.getI());
    }

    @Test
    public void setI() {
        myTile.setI(5);
        Assert.assertEquals(5, myTile.getI());
    }

    @Test
    public void getJ() {
        Assert.assertEquals(0, tileDefault.getJ());
        Assert.assertEquals(4, myTile.getJ());
    }

    @Test
    public void setJ() {
        myTile.setJ(5);
        Assert.assertEquals(5, myTile.getJ());
    }

    @Test
    public void getPiece() {
        Assert.assertEquals(myPiece, myTile.getPiece());
    }

    @Test
    public void setPiece() {
        Piece newPiece = new Rook(4, 4, PiecesType.BLACK);
        myTile.setPiece(newPiece);
        Assert.assertNotEquals(myPiece, myTile.getPiece());
        Assert.assertEquals(newPiece, myTile.getPiece());
    }

    @Test
    public void isEmpty() {
        myTile.setPiece(null);
        Assert.assertTrue(myTile.isEmpty());
    }

    @Test
    public void _toString() {
        Assert.assertEquals("P", myTile.toString());
        myTile.setPiece(null);
        Assert.assertEquals("-", myTile.toString());
    }

    @Test
    public void equals() {
        Tile newTile = new Tile(4, 4, myPiece);
        Assert.assertEquals(myTile, newTile);

        int hash = Objects.hash(4, 4);
        Assert.assertEquals(hash, myTile.hashCode());
    }
}