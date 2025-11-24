package org.chess.console.domain.piece;

/**
 * Recognized chess pieces and their symbolic representation.
 */
public enum PieceType {
    KING("K"),
    QUEEN("Q"),
    ROOK("R"),
    BISHOP("B"),
    KNIGHT("N"),
    PAWN("P");

    private final String symbol;

    PieceType(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the Unicode chess piece symbol for the given color.
     * @param color the color of the piece
     * @return Unicode chess piece symbol
     */
    public String getSymbol(PieceColor color) {
        return switch (this) {
            case KING -> color == PieceColor.WHITE ? "\u2654" : "\u265A";
            case QUEEN -> color == PieceColor.WHITE ? "♕" : "♛";
            case ROOK -> color == PieceColor.WHITE ? "♖" : "♜";
            case BISHOP -> color == PieceColor.WHITE ? "♗" : "♝";
            case KNIGHT -> color == PieceColor.WHITE ? "♘" : "♞";
            case PAWN -> color == PieceColor.WHITE ? "♙" : "♟";
        };
    }

    /**
     * Returns the letter symbol for notation purposes
     * @return letter symbol (K, Q, R, B, N, P)
     */
    public String getSymbol() {
        return symbol;
    }
}

