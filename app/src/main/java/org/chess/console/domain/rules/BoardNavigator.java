package org.chess.console.domain.rules;

import org.chess.console.domain.board.Board;
import org.chess.console.domain.board.Position;
import org.chess.console.domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Utility methods for traversing the board when validating moves.
 */
public final class BoardNavigator {
    private BoardNavigator() {
    }

    public static boolean isPathClear(Board board, Position from, Position to, int stepFile, int stepRank) {
        int file = from.file() + stepFile;
        int rank = from.rank() + stepRank;
        while (file != to.file() || rank != to.rank()) {
            Position pos = Position.of(file, rank);
            if (board.getPiece(pos).isPresent()) {
                return false;
            }
            file += stepFile;
            rank += stepRank;
        }
        return true;
    }

    public static List<Position> collectBetween(Position from, Position to, int stepFile, int stepRank) {
        List<Position> positions = new ArrayList<>();
        int file = from.file() + stepFile;
        int rank = from.rank() + stepRank;
        while (file != to.file() || rank != to.rank()) {
            positions.add(Position.of(file, rank));
            file += stepFile;
            rank += stepRank;
        }
        return positions;
    }

    public static Optional<Position> locateKing(Board board, Piece king) {
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                Position position = Position.of(file, rank);
                Optional<Piece> piece = board.getPiece(position);
                if (piece.isPresent() && piece.get().type() == king.type() && piece.get().color() == king.color()) {
                    return Optional.of(position);
                }
            }
        }
        return Optional.empty();
    }
}

