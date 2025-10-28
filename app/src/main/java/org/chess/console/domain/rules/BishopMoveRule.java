package org.chess.console.domain.rules;

import org.chess.console.domain.piece.PieceType;

public final class BishopMoveRule extends AbstractLineRule {
    @Override
    public PieceType supports() {
        return PieceType.BISHOP;
    }

    @Override
    protected boolean supportsDirection(int deltaFile, int deltaRank) {
        return Math.abs(deltaFile) == Math.abs(deltaRank) && deltaFile != 0;
    }
}

