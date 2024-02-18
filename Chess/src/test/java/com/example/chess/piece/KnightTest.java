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

public class KnightTest {

    Board board;
    Piece piece;
    Piece enemyPiece;
    Piece allyPiece;

    @Before
    public void setUp() {
        piece = new Knight(4, 4, PiecesType.WHITE);
        enemyPiece = new Pawn(3, 6, PiecesType.BLACK);
        allyPiece = new Pawn(6, 5, PiecesType.WHITE);
        Piece kingBlack = new King(0, 4, PiecesType.BLACK);
        Piece kingWhite = new King(7, 4, PiecesType.WHITE);
        Board.Builder builder = new Board.Builder();
        builder.setPiece(piece);
        builder.setPiece(enemyPiece);
        builder.setPiece(allyPiece);
        builder.setPiece(kingWhite);
        builder.setPiece(kingBlack);
        builder.setMoveMaker(PiecesType.WHITE);
        board = builder.build();

    }

    @Test
    public void legalMoves() {
        List<Move> possibleMoves = new ArrayList<>();
        Tile from = board.getTile(4, 4);
        possibleMoves.add(new RegularMove(board, from, board.getTile(2, 3), piece));
        possibleMoves.add(new RegularMove(board, from, board.getTile(2, 5), piece));
        possibleMoves.add(new CaptureMove(board, from, board.getTile(3, 6), piece, enemyPiece));
        possibleMoves.add(new RegularMove(board, from, board.getTile(5, 6), piece));
        possibleMoves.add(new RegularMove(board, from, board.getTile(6, 3), piece));
        possibleMoves.add(new RegularMove(board, from, board.getTile(5, 2), piece));
        possibleMoves.add(new RegularMove(board, from, board.getTile(3, 2), piece));

        // sort lists so we don't have different indexes
        possibleMoves.sort(new Move.CompareMoves());
        List<Move> actual = piece.legalMoves(board);
        actual.sort(new Move.CompareMoves());

        Assert.assertEquals(possibleMoves, actual);
    }

    @Test
    public void _toString() {
        Assert.assertEquals("N", piece.toString());
    }
}