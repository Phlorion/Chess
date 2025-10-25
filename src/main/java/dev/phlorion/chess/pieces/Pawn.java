package dev.phlorion.chess.pieces;

import dev.phlorion.chess.Board;
import dev.phlorion.chess.misc.Vector2;
import dev.phlorion.chess.move.Move;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    private final Vector2 CANDIDATE_MOVE_COORDINATES = new Vector2(-1, 0);

    private final List<Vector2> CANDIDATE_ATTACK_COORDINATES = new ArrayList<>(List.of(
            new Vector2(-1, -1),
            new Vector2(-1, 1)
    ));

    private final Vector2 CANDIDATE_JUMP_MOVE_COORDINATES = new Vector2(-2, 0);

    private final List<Vector2> CANDIDATE_JUMP_ATTACK_COORDINATES = new ArrayList<>(List.of(
            new Vector2(-2, -1),
            new Vector2(-2, 1)
    ));

    public Pawn() {
        super();
        pieceKind = PieceKind.PAWN;
    }

    public Pawn(int x, int y, PieceColor color) {
        super(x, y, color);
        pieceKind = PieceKind.PAWN;
    }

    @Override
    public List<Move> legalMoves(Board board) {
        List<Move> possibleMoves = new ArrayList<>();

        Vector2 targetPos;
        // check basic pawn move
        targetPos = Vector2.add(position, CANDIDATE_MOVE_COORDINATES);
        if (board.inBounds(targetPos) && !board.isOccupied(targetPos, pieceColor))
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
            if (board.inBounds(targetPos) && !board.isOccupied(targetPos, pieceColor))
                possibleMoves.add(new Move(this, targetPos));

            for (Vector2 candidateMove : CANDIDATE_JUMP_ATTACK_COORDINATES) {
                targetPos = Vector2.add(position, candidateMove);
                if (board.inBounds(targetPos) && board.isOccupied(targetPos, pieceColor.getOpposite()))
                    possibleMoves.add(new Move(this, targetPos));
            }
        }

        return possibleMoves;
    }
}
