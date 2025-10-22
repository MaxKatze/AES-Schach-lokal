package org.chess.console.domain.piece;

public final class Bishop extends Piece {
    public Bishop(PieceColor color) {
        super(color, PieceType.BISHOP);
    }

    private Bishop(PieceColor color, boolean moved) {
        super(color, PieceType.BISHOP, moved);
    }

    @Override
    public Piece copy() {
        return new Bishop(color(), hasMoved());
    }

    @Override
    public Piece moved() {
        return new Bishop(color(), true);
    }
}

