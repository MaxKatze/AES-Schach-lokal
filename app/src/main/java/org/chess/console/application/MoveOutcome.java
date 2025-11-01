package org.chess.console.application;

import org.chess.console.domain.piece.Piece;

/**
 * Represents the state after a successful move execution.
 */
public record MoveOutcome(Piece piece, boolean capture, boolean check, boolean checkmate, String notation) {
}

