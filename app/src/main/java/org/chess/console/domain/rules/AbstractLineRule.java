package org.chess.console.domain.rules;

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
            return MoveValidationResult.illegal("Ungültige Richtung für " + supports());
        }

        int stepFile = Integer.compare(deltaFile, 0);
        int stepRank = Integer.compare(deltaRank, 0);

        boolean pathClear = BoardNavigator.isPathClear(context.board(), move.from(), move.to(), stepFile, stepRank);
        if (!pathClear) {
            return MoveValidationResult.illegal("Der Weg ist blockiert");
        }
        return MoveValidationResult.SUCCESS;
    }

    protected abstract boolean supportsDirection(int deltaFile, int deltaRank);
}

