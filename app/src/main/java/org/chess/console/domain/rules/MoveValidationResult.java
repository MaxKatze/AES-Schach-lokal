package org.chess.console.domain.rules;

/**
 * Outcome of evaluating a rule.
 */
public record MoveValidationResult(boolean legal, String message) {
    public static final MoveValidationResult SUCCESS = new MoveValidationResult(true, "");

    public static MoveValidationResult illegal(String message) {
        return new MoveValidationResult(false, message);
    }
}

