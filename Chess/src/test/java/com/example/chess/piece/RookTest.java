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

public class RookTest {

    Board board;
    Piece piece;
    Piece enemy;
    Piece ally1;

    @Before
    public void setUp() {
        piece = new Rook(6, 5, PiecesType.WHITE);
        enemy = new Pawn(3, 5, PiecesType.BLACK);
        ally1 = new Pawn(6, 3, PiecesType.WHITE);
        Piece kingBlack = new King(0, 4, PiecesType.BLACK);
        Piece kingWhite = new King(7, 4, PiecesType.WHITE);
        Board.Builder builder = new Board.Builder();
        builder.setPiece(piece);
        builder.setPiece(enemy);
        builder.setPiece(ally1);
        builder.setPiece(kingWhite);
        builder.setPiece(kingBlack);
        builder.setMoveMaker(PiecesType.WHITE);
        board = builder.build();
    }

    @Test
    public void legalMoves() {
        List<Move> possibleMoves = new ArrayList<>();
        Tile from = board.getTile(6, 5);
        possibleMoves.add(new RegularMove(board, from, board.getTile(5, 5), piece));
        possibleMoves.add(new RegularMove(board, from, board.getTile(4, 5), piece));
        possibleMoves.add(new CaptureMove(board, from, board.getTile(3, 5), piece, enemy));
        possibleMoves.add(new RegularMove(board, from, board.getTile(6, 4), piece));
        possibleMoves.add(new RegularMove(board, from, board.getTile(6, 6), piece));
        possibleMoves.add(new RegularMove(board, from, board.getTile(6, 7), piece));
        possibleMoves.add(new RegularMove(board, from, board.getTile(7, 5), piece));

        // sort lists so we don't have different indexes
        possibleMoves.sort(new Move.CompareMoves());
        List<Move> actual = piece.legalMoves(board);
        actual.sort(new Move.CompareMoves());

        Assert.assertEquals(possibleMoves, actual);
    }

    @Test
    public void _toString() {
        Assert.assertEquals("R", piece.toString());
    }
}