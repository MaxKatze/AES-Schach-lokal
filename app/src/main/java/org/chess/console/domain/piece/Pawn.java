package org.chess.console.domain.piece;

public final class Pawn extends Piece {
    public Pawn(PieceColor color) {
        super(color, PieceType.PAWN);
    }

    private Pawn(PieceColor color, boolean moved) {
        super(color, PieceType.PAWN, moved);
    }

    @Override
    public Piece copy() {
        return new Pawn(color(), hasMoved());
    }

    @Override
    public Piece moved() {
        return new Pawn(color(), true);
    }
}

