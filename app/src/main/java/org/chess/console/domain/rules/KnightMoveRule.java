package org.chess.console.domain.rules;

import org.chess.console.domain.exceptions.DomainErrorCode;
import org.chess.console.domain.piece.PieceType;

public final class KnightMoveRule implements MoveRule {
    @Override
    public PieceType supports() {
        return PieceType.KNIGHT;
    }

    @Override
    public MoveValidationResult validate(RuleContext context) {
        int deltaFile = Math.abs(context.move().from().deltaFile(context.move().to()));
        int deltaRank = Math.abs(context.move().from().deltaRank(context.move().to()));
        boolean valid = (deltaFile == 2 && deltaRank == 1) || (deltaFile == 1 && deltaRank == 2);
        if (!valid) {
            return MoveValidationResult.illegal(DomainErrorCode.KNIGHT_MOVES_L_SHAPE_ONLY);
        }
        return MoveValidationResult.SUCCESS;
    }
}

