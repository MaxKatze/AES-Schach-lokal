package org.chess.console.domain.piece;

public final class King extends Piece {
    public King(PieceColor color) {
        super(color, PieceType.KING);
    }

    private King(PieceColor color, boolean moved) {
        super(color, PieceType.KING, moved);
    }

    @Override
    public Piece copy() {
        return new King(color(), hasMoved());
    }

    @Override
    public Piece moved() {
        return new King(color(), true);
    }
}

