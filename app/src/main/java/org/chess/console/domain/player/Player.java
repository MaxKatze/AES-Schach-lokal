package org.chess.console.domain.player;

import org.chess.console.domain.piece.PieceColor;

import java.util.Objects;
import java.util.UUID;

/**
 * Entity representing a human player participating in a game.
 */
public final class Player {
    private final UUID id;
    private final String name;
    private final PieceColor color;

    public Player(String name, PieceColor color) {
        this(UUID.randomUUID(), name, color);
    }

    public Player(UUID id, String name, PieceColor color) {
        this.id = Objects.requireNonNull(id, "id");
        this.name = Objects.requireNonNull(name, "name");
        this.color = Objects.requireNonNull(color, "color");
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }

    public PieceColor color() {
        return color;
    }

    @Override
    public String toString() {
        return name + " (" + color + ")";
    }
}

