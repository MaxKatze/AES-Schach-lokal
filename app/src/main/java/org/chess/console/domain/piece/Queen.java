package org.chess.console.domain.piece;

public final class Queen extends Piece {
    public Queen(PieceColor color) {
        super(color, PieceType.QUEEN);
    }

    private Queen(PieceColor color, boolean moved) {
        super(color, PieceType.QUEEN, moved);
    }

    @Override
    public Piece copy() {
        return new Queen(color(), hasMoved());
    }

    @Override
    public Piece moved() {
        return new Queen(color(), true);
    }
}

