package org.chess.console.domain.rules;

import org.chess.console.domain.board.Board;
import org.chess.console.domain.board.Position;
import org.chess.console.domain.move.Move;
import org.chess.console.domain.piece.King;
import org.chess.console.domain.piece.Piece;
import org.chess.console.domain.piece.PieceColor;

import java.util.Optional;

/**
 * Service that can determine check situations.
 */
public final class CheckInspector {

    private final RuleBook ruleBook;

    public CheckInspector(RuleBook ruleBook) {
        this.ruleBook = ruleBook;
    }

    public boolean isKingInCheck(Board board, PieceColor kingColor) {
        Position kingPosition = locateKing(board, kingColor)
                .orElseThrow(() -> new IllegalStateException("Kein König für " + kingColor + " gefunden"));

        Piece kingPiece = new King(kingColor);

        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                Position attackerPosition = Position.of(file, rank);
                Optional<Piece> occupant = board.getPiece(attackerPosition);
                if (occupant.isEmpty()) {
                    continue;
                }
                Piece attacker = occupant.get();
                if (attacker.color() != kingColor.opposite()) {
                    continue;
                }
                Move pseudoMove = new Move(attackerPosition, kingPosition);
                var result = ruleBook.validate(board, pseudoMove, attacker, kingPiece);
                if (result.legal()) {
                    if (pathClearOrKnight(attacker, board, attackerPosition, kingPosition)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean pathClearOrKnight(Piece attacker, Board board, Position from, Position to) {
        switch (attacker.type()) {
            case ROOK -> {
                int df = Integer.compare(to.file() - from.file(), 0);
                int dr = Integer.compare(to.rank() - from.rank(), 0);
                return BoardNavigator.isPathClear(board, from, to, df, dr);
            }
            case BISHOP -> {
                int df = Integer.compare(to.file() - from.file(), 0);
                int dr = Integer.compare(to.rank() - from.rank(), 0);
                return BoardNavigator.isPathClear(board, from, to, df, dr);
            }
            case QUEEN -> {
                int df = Integer.compare(to.file() - from.file(), 0);
                int dr = Integer.compare(to.rank() - from.rank(), 0);
                return BoardNavigator.isPathClear(board, from, to, df, dr);
            }
            case KNIGHT -> {
                return true;
            }
            case KING -> {
                return true;
            }
            case PAWN -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    private Optional<Position> locateKing(Board board, PieceColor color) {
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                Position position = Position.of(file, rank);
                Optional<Piece> piece = board.getPiece(position);
                if (piece.isPresent() && piece.get().type() == org.chess.console.domain.piece.PieceType.KING
                        && piece.get().color() == color) {
                    return Optional.of(position);
                }
            }
        }
        return Optional.empty();
    }
}

