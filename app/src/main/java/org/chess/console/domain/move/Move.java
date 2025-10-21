package org.chess.console.domain.move;

import org.chess.console.domain.board.Position;
import org.chess.console.domain.piece.PieceType;

import java.util.Objects;
import java.util.Optional;

/**
 * Value object that represents a user intent to move a piece.
 */
public final class Move {
    private final Position from;
    private final Position to;
    private final Optional<PieceType> promotion;

    public Move(Position from, Position to) {
        this(from, to, Optional.empty());
    }

    public Move(Position from, Position to, Optional<PieceType> promotion) {
        this.from = Objects.requireNonNull(from, "from");
        this.to = Objects.requireNonNull(to, "to");
        this.promotion = Objects.requireNonNull(promotion, "promotion");
    }

    public Position from() {
        return from;
    }

    public Position to() {
        return to;
    }

    public Optional<PieceType> promotion() {
        return promotion;
    }

    @Override
    public String toString() {
        return from + "->" + to + promotion.map(type -> "=" + type.getSymbol()).orElse("");
    }
}

