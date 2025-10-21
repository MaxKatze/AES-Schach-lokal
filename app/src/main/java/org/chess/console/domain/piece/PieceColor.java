package org.chess.console.domain.piece;

/**
 * Represents the two sides that can participate in a chess match.
 */
public enum PieceColor {
    WHITE,
    BLACK;

    public PieceColor opposite() {
        return this == WHITE ? BLACK : WHITE;
    }

    public int forwardDirection() {
        return this == WHITE ? 1 : -1;
    }

    public static PieceColor fromTurn(int turnIndex) {
        return turnIndex % 2 == 0 ? WHITE : BLACK;
    }
}

