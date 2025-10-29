package org.chess.console.domain.rules;

import org.chess.console.domain.piece.PieceType;

public final class KingMoveRule implements MoveRule {
    @Override
    public PieceType supports() {
        return PieceType.KING;
    }

    @Override
    public MoveValidationResult validate(RuleContext context) {
        var from = context.move().from();
        var to = context.move().to();
        int deltaFile = Math.abs(from.deltaFile(to));
        int deltaRank = Math.abs(from.deltaRank(to));
        if (deltaFile <= 1 && deltaRank <= 1 && (deltaFile + deltaRank > 0)) {
            return MoveValidationResult.SUCCESS;
        }
        return MoveValidationResult.illegal("Der König bewegt sich nur ein Feld");
    }
}

