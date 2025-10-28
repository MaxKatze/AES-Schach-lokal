package org.chess.console.domain.rules;

import org.chess.console.domain.piece.PieceType;

public final class QueenMoveRule extends AbstractLineRule {
    @Override
    public PieceType supports() {
        return PieceType.QUEEN;
    }

    @Override
    protected boolean supportsDirection(int deltaFile, int deltaRank) {
        boolean straight = (deltaFile == 0 && deltaRank != 0) || (deltaRank == 0 && deltaFile != 0);
        boolean diagonal = Math.abs(deltaFile) == Math.abs(deltaRank) && deltaFile != 0;
        return straight || diagonal;
    }
}

