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

public class QueenTest {

    Board board;
    Piece piece;
    Piece enemy1;
    Piece enemy2;
    Piece ally1;
    Piece ally2;

    @Before
    public void setUp() {
        piece = new Queen(5, 2, PiecesType.WHITE);
        enemy1 = new Pawn(2, 5, PiecesType.BLACK);
        enemy2 = new Pawn(3, 2, PiecesType.BLACK);
        ally1 = new Pawn(5, 4, PiecesType.WHITE);
        ally2 = new Pawn(5, 0, PiecesType.WHITE);
        Piece kingBlack = new King(0, 4, PiecesType.BLACK);
        Piece kingWhite = new King(7, 4, PiecesType.WHITE);
        Board.Builder builder = new Board.Builder();
        builder.setPiece(piece);
        builder.setPiece(enemy1);
        builder.setPiece(enemy2);
        builder.setPiece(ally1);
        builder.setPiece(ally2);
        builder.setPiece(piece);
        builder.setPiece(kingWhite);
        builder.setPiece(kingBlack);
        builder.setMoveMaker(PiecesType.WHITE);
        board = builder.build();
    }

    @Test
    public void legalMoves() {
        List<Move> possibleMoves = new ArrayList<>();
        Tile from = board.getTile(5, 2);
        possibleMoves.add(new RegularMove(board, from, board.getTile(5, 1), piece));
        possibleMoves.add(new RegularMove(board, from, board.getTile(4, 1), piece));
        possibleMoves.add(new RegularMove(board, from, board.getTile(3, 0), piece));
        possibleMoves.add(new RegularMove(board, from, board.getTile(4, 2), piece));
        possibleMoves.add(new CaptureMove(board, from, board.getTile(3, 2), piece, enemy2));
        possibleMoves.add(new RegularMove(board, from, board.getTile(4, 3), piece));
        possibleMoves.add(new RegularMove(board, from, board.getTile(3, 4), piece));
        possibleMoves.add(new CaptureMove(board, from, board.getTile(2, 5), piece, enemy1));
        possibleMoves.add(new RegularMove(board, from, board.getTile(5, 3), piece));
        possibleMoves.add(new RegularMove(board, from, board.getTile(6, 3), piece));
        possibleMoves.add(new RegularMove(board, from, board.getTile(6, 2), piece));
        possibleMoves.add(new RegularMove(board, from, board.getTile(7, 2), piece));
        possibleMoves.add(new RegularMove(board, from, board.getTile(6, 1), piece));
        possibleMoves.add(new RegularMove(board, from, board.getTile(7, 0), piece));

        // sort lists so we don't have different indexes
        possibleMoves.sort(new Move.CompareMoves());
        List<Move> actual = piece.legalMoves(board);
        actual.sort(new Move.CompareMoves());

        Assert.assertEquals(possibleMoves, actual);
    }

    @Test
    public void testToString() {
        Assert.assertEquals("Q", piece.toString());
    }
}