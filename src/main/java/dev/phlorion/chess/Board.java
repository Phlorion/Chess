package dev.phlorion.chess;

import dev.phlorion.chess.misc.BoardReader;
import dev.phlorion.chess.misc.Vector2;
import dev.phlorion.chess.move.Move;
import dev.phlorion.chess.pieces.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Board {
    Piece[][] board;
    Player pWhite;
    Player pBlack;
    Player currentPlayer;
    Player opponentPlayer;

    public Board(String path) {
        BoardReader boardReader = new BoardReader();
        try {
            setBoard(boardReader.read(path));
        } catch (IOException e) {
            System.err.println(e);
        }

        pWhite = new Player(PieceColor.WHITE);
        pWhite.setPieces(getPieces(PieceColor.WHITE));
        pBlack = new Player(PieceColor.BLACK);
        pBlack.setPieces(getPieces(PieceColor.BLACK));
        currentPlayer = pWhite;
        opponentPlayer = pBlack;
    }

    public Board(Board other) {
        board = other.getBoard().clone(); // The reference of the pieces is copied, so be carful not to change the pieces attributes
        pWhite = other.pWhite;
        pBlack = other.pBlack;
        currentPlayer = other.currentPlayer;
        opponentPlayer = other.opponentPlayer;
    }

    public Piece[][] standardBoard() {
        Piece[][] temp = new Piece[8][8];
        // white
        temp[7][0] = new Rook(7, 0, PieceColor.WHITE);
        temp[7][7] = new Rook(7, 7, PieceColor.WHITE);
        temp[7][1] = new Knight(7, 1, PieceColor.WHITE);
        temp[7][6] = new Knight(7, 6, PieceColor.WHITE);
        temp[7][2] = new Bishop(7, 2, PieceColor.WHITE);
        temp[7][5] = new Bishop(7, 5, PieceColor.WHITE);
        temp[7][3] = new Queen(7, 3, PieceColor.WHITE);
        temp[7][4] = new King(7, 5, PieceColor.WHITE);
        for (int j=0; j<temp[6].length; j++) {
            temp[6][j] = new Pawn(6, j, PieceColor.WHITE);
        }

        // black
        temp[0][0] = new Rook(0, 0, PieceColor.BLACK);
        temp[0][7] = new Rook(0, 7, PieceColor.BLACK);
        temp[0][1] = new Knight(0, 1, PieceColor.BLACK);
        temp[0][6] = new Knight(0, 6, PieceColor.BLACK);
        temp[0][2] = new Bishop(0, 2, PieceColor.BLACK);
        temp[0][5] = new Bishop(0, 5, PieceColor.BLACK);
        temp[0][3] = new Queen(0, 3, PieceColor.BLACK);
        temp[0][4] = new King(0, 5, PieceColor.BLACK);
        for (int j=0; j<temp[1].length; j++) {
            temp[1][j] = new Pawn(1, j, PieceColor.BLACK);
        }

        return temp;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public Player getOpponentPlayer() {return opponentPlayer;}

    public void setCurrentPlayer(PieceColor color) {
        if (color.equals(PieceColor.WHITE)) {
            currentPlayer = pWhite;
            opponentPlayer = pBlack;
        }
        else {
            currentPlayer = pBlack;
            opponentPlayer =  pWhite;
        }
    }

    public void setOnBoard(Vector2 position, Piece piece) {
        board[position.x][position.y] = piece;
    }

    public void setBoard(Piece[][] other) {
        board = other;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public Piece getPieceAt(Vector2 position) {
        return board[position.x][position.y];
    }

    public List<Piece> getPieces(PieceColor color) {
        List<Piece> pieces = new ArrayList<>();
        for (Piece[] row : board) {
            for (Piece p : row) {
                if (p != null && p.getPieceColor().equals(color))
                    pieces.add(p);
            }
        }

        return pieces;
    }

    public Vector2 findKingPositionOnBoard(PieceColor color) {
        for (int i=0; i<board.length; i++) {
            for (int j=0; j<board[i].length; j++) {
                Piece p = board[i][j];
                if (p != null && p.getPieceKind().equals(PieceKind.KING) && p.getPieceColor().equals(color))
                    return new Vector2(i, j);
            }
        }

        return null;
    }

    public boolean isPositionAttacked(Player player, Vector2 position) {
        for (Piece piece : player.getPieces()) {
            for (Move m : piece.legalMoves(this)) {
                if (m.getTargetedPos().equals(position)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean inBounds(Vector2 position) {
        return position.x >=0 &&
                position.x < board[0].length &&
                position.y >= 0 &&
                position.y < board.length;
    }

    public boolean isOccupied(Vector2 position, PieceColor color) {
        Piece occupier = board[position.x][position.y];
        return occupier != null && occupier.getPieceColor() == color; // must be allied piece to be considered occupied
    }

    public Vector2 getBoardShape() {
        return new Vector2(board.length, board[0].length);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (Piece[] pieces : board) {
            for (Piece piece : pieces) {
                if (piece == null)
                    res.append("- ");
                else
                    res.append(piece.toString()).append(" ");
            }
            res.append("\n");
        }

        return res.toString();
    }
}
