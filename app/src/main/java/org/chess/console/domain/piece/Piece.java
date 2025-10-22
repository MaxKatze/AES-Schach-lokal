package org.chess.console.domain.piece;

/**
 * Base class for all chess pieces.
 */
public abstract class Piece {
    private final PieceColor color;
    private final PieceType type;
    private final boolean hasMoved;

    protected Piece(PieceColor color, PieceType type) {
        this(color, type, false);
    }

    protected Piece(PieceColor color, PieceType type, boolean hasMoved) {
        this.color = color;
        this.type = type;
        this.hasMoved = hasMoved;
    }

    public PieceColor color() {
        return color;
    }

    public PieceType type() {
        return type;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public abstract Piece copy();

    public abstract Piece moved();

    @Override
    public String toString() {
        return color.name().charAt(0) + type.getSymbol();
    }
}

