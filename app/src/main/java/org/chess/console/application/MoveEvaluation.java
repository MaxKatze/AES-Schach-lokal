package org.chess.console.application;

/**
 * Result of validating a move request.
 */
public record MoveEvaluation(boolean success, String message, MoveOutcome outcome) {
    public static MoveEvaluation success(MoveOutcome outcome) {
        return new MoveEvaluation(true, "Zug ausgeführt", outcome);
    }

    public static MoveEvaluation failure(String message) {
        return new MoveEvaluation(false, message, null);
    }
}

