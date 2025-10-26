package org.chess.console.domain.rules;

import org.chess.console.domain.piece.PieceType;

/**
 * Strategy interface describing how a piece behaves on the board.
 */
public interface MoveRule {
    PieceType supports();

    MoveValidationResult validate(RuleContext context);
}

