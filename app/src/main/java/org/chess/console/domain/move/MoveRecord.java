package org.chess.console.domain.move;

import org.chess.console.domain.piece.PieceColor;
import org.chess.console.domain.piece.PieceType;

import java.time.Instant;

/**
 * Immutable historic representation of a move for auditing purposes.
 */
public record MoveRecord(
        int turn,
        PieceType piece,
        PieceColor color,
        Move move,
        boolean capture,
        boolean check,
        boolean checkmate,
        Instant timestamp
) {
    public String notation() {
        var captureMarker = capture ? "x" : "-";
        return "%d. %s%s%s%s".formatted(
                turn,
                piece.getSymbol(color),
                captureMarker,
                move.to().notation(),
                checkmate ? "#" : (check ? "+" : "")
        );
    }
}

