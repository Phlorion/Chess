package dev.phlorion.chess.pieces;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.misc.Vector2;
import dev.phlorion.chess.move.Move;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    private final int direction;
    private final Vector2 CANDIDATE_MOVE_COORDINATES;

    private final List<Vector2> CANDIDATE_ATTACK_COORDINATES;

    private final Vector2 CANDIDATE_JUMP_MOVE_COORDINATES;

    public Pawn() {
        super();
        pieceKind = PieceKind.PAWN;
        direction = getPieceColor().getDirection();
        CANDIDATE_MOVE_COORDINATES = new Vector2(direction, 0);
        CANDIDATE_ATTACK_COORDINATES = new ArrayList<>(List.of(
                new Vector2(direction, -1),
                new Vector2(direction, 1)
        ));
        CANDIDATE_JUMP_MOVE_COORDINATES = new Vector2(direction * 2, 0);
    }

    public Pawn(int x, int y, PieceColor color) {
        super(x, y, color);
        pieceKind = PieceKind.PAWN;
        direction = getPieceColor().getDirection();
        CANDIDATE_MOVE_COORDINATES = new Vector2(direction, 0);
        CANDIDATE_ATTACK_COORDINATES = new ArrayList<>(List.of(
                new Vector2(direction, -1),
                new Vector2(direction, 1)
        ));
        CANDIDATE_JUMP_MOVE_COORDINATES = new Vector2(direction * 2, 0);
    }

    public boolean canPromote(Board board) {
        return (direction == 1 && position.x == board.getBoardShape().x - 1) || (direction == -1 && position.x == 0);
    }

    @Override
    public List<Move> legalMoves(Board board) {
        List<Move> possibleMoves = new ArrayList<>();

        Vector2 targetPos;
        // check basic pawn move
        targetPos = Vector2.add(position, CANDIDATE_MOVE_COORDINATES);
        if (board.inBounds(targetPos) && !board.isOccupied(targetPos, pieceColor) && !board.isOccupied(targetPos, pieceColor.getOpposite()))
            possibleMoves.add(new Move(this, targetPos));

        // check if pawn can attack
        for (Vector2 candidateMove : CANDIDATE_ATTACK_COORDINATES) {
            targetPos = Vector2.add(position, candidateMove);
            if (board.inBounds(targetPos) && board.isOccupied(targetPos, pieceColor.getOpposite()))
                possibleMoves.add(new Move(this, targetPos));
        }

        // check if pawn can jump (meaning it hasn't moved yet)
        if (!hasMoved) {
            targetPos = Vector2.add(position, CANDIDATE_JUMP_MOVE_COORDINATES);
            Vector2 previousTile = Vector2.add(position, CANDIDATE_MOVE_COORDINATES);
            if (board.inBounds(targetPos) &&
                    !board.isOccupied(previousTile, pieceColor) &&
                    !board.isOccupied(previousTile, pieceColor.getOpposite()) &&
                    !board.isOccupied(targetPos, pieceColor) &&
                    !board.isOccupied(targetPos, pieceColor.getOpposite()))
                possibleMoves.add(new Move(this, targetPos));
        }

        return possibleMoves;
    }
}
