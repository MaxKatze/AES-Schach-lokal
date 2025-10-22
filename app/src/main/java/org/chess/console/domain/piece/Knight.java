package org.chess.console.domain.piece;

public final class Knight extends Piece {
    public Knight(PieceColor color) {
        super(color, PieceType.KNIGHT);
    }

    private Knight(PieceColor color, boolean moved) {
        super(color, PieceType.KNIGHT, moved);
    }

    @Override
    public Piece copy() {
        return new Knight(color(), hasMoved());
    }

    @Override
    public Piece moved() {
        return new Knight(color(), true);
    }
}

