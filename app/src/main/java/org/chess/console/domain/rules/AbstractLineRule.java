package org.chess.console.domain.rules;

import org.chess.console.domain.exceptions.DomainErrorCode;

/**
 * Base for rules that move along rays (rook, bishop, queen).
 */
public abstract class AbstractLineRule implements MoveRule {

    @Override
    public MoveValidationResult validate(RuleContext context) {
        var move = context.move();
        int deltaFile = move.from().deltaFile(move.to());
        int deltaRank = move.from().deltaRank(move.to());

        if (!supportsDirection(deltaFile, deltaRank)) {
            return MoveValidationResult.illegal(DomainErrorCode.INVALID_DIRECTION_FOR_PIECE, supports());
        }

        int stepFile = Integer.compare(deltaFile, 0);
        int stepRank = Integer.compare(deltaRank, 0);

        boolean pathClear = BoardNavigator.isPathClear(context.board(), move.from(), move.to(), stepFile, stepRank);
        if (!pathClear) {
            return MoveValidationResult.illegal(DomainErrorCode.PATH_BLOCKED);
        }
        return MoveValidationResult.SUCCESS;
    }

    protected abstract boolean supportsDirection(int deltaFile, int deltaRank);
}

