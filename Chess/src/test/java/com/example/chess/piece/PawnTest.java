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

public class PawnTest {

    Board board;
    Piece piece;
    Piece piece2;
    Piece enemy;
    Piece enemy2;
    Piece ally;

    @Before
    public void setUp() {
        piece = new Pawn(6, 4, PiecesType.WHITE);
        piece2 = new Pawn(6, 0, PiecesType.WHITE);
        ally = new Knight(4, 4, PiecesType.WHITE);
        enemy = new Pawn(4, 5, PiecesType.BLACK);
        enemy2 = new Pawn(5, 1, PiecesType.BLACK);
        Piece kingBlack = new King(0, 4, PiecesType.BLACK);
        Piece kingWhite = new King(7, 4, PiecesType.WHITE);
        Board.Builder builder = new Board.Builder();
        builder.setPiece(piece);
        builder.setPiece(piece2);
        builder.setPiece(ally);
        builder.setPiece(enemy);
        builder.setPiece(enemy2);
        builder.setPiece(kingWhite);
        builder.setPiece(kingBlack);
        builder.setMoveMaker(PiecesType.WHITE);
        board = builder.build();
    }

    @Test
    public void legalMoves() {
        // moves for piece
        List<Move> possibleMoves = new ArrayList<>();
        Tile from = board.getTile(6, 4);
        possibleMoves.add(new RegularMove(board, from, board.getTile(5, 4), piece));
        // sort lists so we don't have different indexes
        possibleMoves.sort(new Move.CompareMoves());
        List<Move> actual = piece.legalMoves(board);
        actual.sort(new Move.CompareMoves());
        Assert.assertEquals(possibleMoves, actual);

        // move for piece2
        List<Move> possibleMoves2 = new ArrayList<>();
        Tile from2 = board.getTile(6, 0);
        possibleMoves2.add(new RegularMove(board, from2, board.getTile(5, 0), piece2));
        possibleMoves2.add(new RegularMove(board, from2, board.getTile(4, 0), piece2));
        possibleMoves2.add(new CaptureMove(board, from2, board.getTile(5, 1), piece2, enemy2));
        // sort lists so we don't have different indexes
        possibleMoves2.sort(new Move.CompareMoves());
        List<Move> actual2 = piece2.legalMoves(board);
        actual2.sort(new Move.CompareMoves());
        Assert.assertEquals(possibleMoves2, actual2);

        // set piece2 as has moved so jump-move is not possible
        piece2.setHasMoved(true);
        List<Move> possibleMoves3 = new ArrayList<>();
        possibleMoves3.add(new RegularMove(board, from2, board.getTile(5, 0), piece2));
        possibleMoves3.add(new CaptureMove(board, from2, board.getTile(5, 1), piece2, enemy2));
        // sort lists so we don't have different indexes
        possibleMoves3.sort(new Move.CompareMoves());
        List<Move> actual3 = piece2.legalMoves(board);
        actual3.sort(new Move.CompareMoves());
        Assert.assertEquals(possibleMoves3, actual3);
    }

    @Test
    public void _toString() {
        Assert.assertEquals("P", piece.toString());
    }
}