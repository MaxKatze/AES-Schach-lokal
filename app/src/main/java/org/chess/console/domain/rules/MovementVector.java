package org.chess.console.domain.rules;

/**
 * Represents a directional unit vector on the chess board.
 */
public record MovementVector(int fileStep, int rankStep) {
    public static MovementVector of(int fileStep, int rankStep) {
        if (fileStep == 0 && rankStep == 0) {
            throw new IllegalArgumentException("Vector must have a magnitude");
        }
        return new MovementVector(fileStep, rankStep);
    }
}

