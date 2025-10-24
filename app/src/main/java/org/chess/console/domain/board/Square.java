package org.chess.console.domain.board;

import org.chess.console.domain.piece.Piece;

import java.util.Optional;

/**
 * Represents a square on the board. Acts as an entity inside the Board aggregate.
 */
public final class Square {
    private final Position position;
    private Piece piece;

    public Square(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public Optional<Piece> getPiece() {
        return Optional.ofNullable(piece);
    }

    public boolean isEmpty() {
        return piece == null;
    }

    public void place(Piece newPiece) {
        this.piece = newPiece;
    }

    public Piece removePiece() {
        Piece removed = piece;
        piece = null;
        return removed;
    }

    @Override
    public String toString() {
        return position + ":" + (piece == null ? "-" : piece.toString());
    }
}

