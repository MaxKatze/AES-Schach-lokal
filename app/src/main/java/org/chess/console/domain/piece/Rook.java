package org.chess.console.domain.piece;

public final class Rook extends Piece {
    public Rook(PieceColor color) {
        super(color, PieceType.ROOK);
    }

    private Rook(PieceColor color, boolean moved) {
        super(color, PieceType.ROOK, moved);
    }

    @Override
    public Piece copy() {
        return new Rook(color(), hasMoved());
    }

    @Override
    public Piece moved() {
        return new Rook(color(), true);
    }
}

