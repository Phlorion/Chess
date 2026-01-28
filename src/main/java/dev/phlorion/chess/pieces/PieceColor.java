package dev.phlorion.chess.pieces;

public enum PieceColor {
    WHITE {
        @Override
        public PieceColor getOpposite() {
            return PieceColor.BLACK;
        }
    },
    BLACK {
        @Override
        public PieceColor getOpposite() {
            return PieceColor.WHITE;
        }
    };

    public abstract PieceColor getOpposite();

    public int getDirection() {
        return (this == PieceColor.WHITE) ? -1 : 1;
    }
}
