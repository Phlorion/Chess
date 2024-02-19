package com.example.chess.piece;

import com.example.chess.board.Board;
import com.example.chess.board.Tile;
import com.example.chess.move.CaptureMove;
import com.example.chess.move.Move;
import com.example.chess.move.RegularMove;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class KingTest {

    Board board;
    Piece piece;
    Piece ally;
    Piece enemy;

    @Before
    public void setUp() {
        piece = new King(7, 4, PiecesType.WHITE);
        ally = new Rook(7, 3, PiecesType.WHITE);
        enemy = new Knight(6, 5, PiecesType.BLACK);
        Piece kingBlack = new King(0, 4, PiecesType.BLACK);
        Board.Builder builder = new Board.Builder();
        builder.setPiece(piece);
        builder.setPiece(ally);
        builder.setPiece(enemy);
        builder.setPiece(kingBlack);
        builder.setMoveMaker(PiecesType.WHITE);
        board = builder.build();
    }

    @Test
    public void legalMoves() {
        List<Move> possibleMoves = new ArrayList<>();
        Tile from = board.getTile(7, 4);
        possibleMoves.add(new RegularMove(board, from, board.getTile(6, 3), piece));
        possibleMoves.add(new RegularMove(board, from, board.getTile(6, 4), piece));
        possibleMoves.add(new CaptureMove(board, from, board.getTile(6, 5), piece, enemy));
        possibleMoves.add(new RegularMove(board, from, board.getTile(7, 5), piece));

        // sort lists so we don't have different indexes
        possibleMoves.sort(new Move.CompareMoves());
        List<Move> actual = piece.legalMoves(board);
        actual.sort(new Move.CompareMoves());

        Assert.assertEquals(possibleMoves, actual);
    }

    @Test
    public void _toString() {
        Assert.assertEquals("K", piece.toString());
    }
}