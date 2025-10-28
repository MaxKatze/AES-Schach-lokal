package org.chess.console.domain.rules;

import org.chess.console.domain.piece.PieceType;

public final class RookMoveRule extends AbstractLineRule {
    @Override
    public PieceType supports() {
        return PieceType.ROOK;
    }

    @Override
    protected boolean supportsDirection(int deltaFile, int deltaRank) {
        return (deltaFile == 0 && deltaRank != 0) || (deltaRank == 0 && deltaFile != 0);
    }
}

