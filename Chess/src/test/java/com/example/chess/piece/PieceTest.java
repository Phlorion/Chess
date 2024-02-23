package com.example.chess.piece;

import com.example.chess.board.Board;
import com.example.chess.board.Tile;
import com.example.chess.move.CaptureMove;
import com.example.chess.move.Move;
import com.example.chess.move.RegularMove;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class PieceTest {

    Board board;
    Piece piece;
    Piece enemyPiece;
    Piece allyPiece;

    @Before
    public void setUp() {
        piece = new Knight(4, 4, PiecesType.WHITE);
        enemyPiece = new Pawn(3, 6, PiecesType.BLACK);
        allyPiece = new Pawn(6, 5, PiecesType.WHITE);
        // build a board with a knight, an ally pawn and an opponent pawn
        Board.Builder builder = new Board.Builder();
        builder.setPiece(piece);
        builder.setPiece(enemyPiece);
        builder.setPiece(allyPiece);
        builder.setPiece(new King(0, 4, PiecesType.BLACK));
        builder.setPiece(new King(7, 4, PiecesType.WHITE));
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
    public void getType() {
        Assert.assertEquals(PiecesType.WHITE, piece.getType());
    }

    @Test
    public void setType() {
        allyPiece.setType(PiecesType.BLACK);
        Assert.assertEquals(PiecesType.BLACK, allyPiece.getType());
    }

    @Test
    public void getPiecePosI() {
        Assert.assertEquals(4, piece.getPiecePosI());
    }

    @Test
    public void setPiecePosI() {
        piece.setPiecePosI(5);
        Assert.assertEquals(5, piece.getPiecePosI());
    }

    @Test
    public void getPiecePosJ() {
        Assert.assertEquals(4, piece.getPiecePosJ());
    }

    @Test
    public void setPiecePosJ() {
        piece.setPiecePosJ(5);
        Assert.assertEquals(5, piece.getPiecePosJ());
    }

    @Test
    public void getPieceKind() {
        Assert.assertEquals(Piece.PieceKind.KNIGHT, piece.getPieceKind());
    }

    @Test
    public void setPieceKind() {
        allyPiece.setPieceKind(Piece.PieceKind.QUEEN);
        Assert.assertEquals(Piece.PieceKind.QUEEN, allyPiece.getPieceKind());
    }

    @Test
    public void hasMoved() {
        Assert.assertFalse(piece.hasMoved());
        Move toExecuteMove = piece.legalMoves(board).get(0);
        board = toExecuteMove.execute(board);
        Assert.assertTrue(piece.hasMoved());
    }

    @Test
    public void setHasMoved() {
        allyPiece.setHasMoved(true);
        Assert.assertTrue(allyPiece.hasMoved());
    }

    @Test
    public void equals() {
        Piece newPiece = new Knight(4, 4, PiecesType.WHITE);
        assertEquals(piece, newPiece);

        int hash = Objects.hash(newPiece.getPiecePosI(), newPiece.getPiecePosJ(), newPiece.getType(), newPiece.getPieceKind());
        Assert.assertEquals(hash, piece.hashCode());
    }
}