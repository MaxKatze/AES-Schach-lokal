package org.chess.console.domain.piece;

/**
 * Simple factory for creating piece instances dynamically.
 */
public final class PieceFactory {
    public Piece create(PieceType type, PieceColor color) {
        return switch (type) {
            case KING -> new King(color);
            case QUEEN -> new Queen(color);
            case ROOK -> new Rook(color);
            case BISHOP -> new Bishop(color);
            case KNIGHT -> new Knight(color);
            case PAWN -> new Pawn(color);
        };
    }
}

