package org.chess.console.domain.rules;

import org.chess.console.domain.board.Position;
import org.chess.console.domain.piece.PieceColor;
import org.chess.console.domain.piece.PieceType;

public final class PawnMoveRule implements MoveRule {
    @Override
    public PieceType supports() {
        return PieceType.PAWN;
    }

    @Override
    public MoveValidationResult validate(RuleContext context) {
        var move = context.move();
        var piece = context.movingPiece();

        int deltaFile = move.from().deltaFile(move.to());
        int deltaRank = move.from().deltaRank(move.to());
        int direction = piece.color().forwardDirection();

        boolean forwardMove = deltaFile == 0 && deltaRank == direction;
        boolean doubleMove = deltaFile == 0 && deltaRank == 2 * direction && isStartingRank(piece.color(), move.from());
        boolean captureMove = Math.abs(deltaFile) == 1 && deltaRank == direction;

        if (!(forwardMove || doubleMove || captureMove)) {
            return MoveValidationResult.illegal("Der Bauer darf nur vorwärts ziehen");
        }

        if (forwardMove && context.targetPiece() != null) {
            return MoveValidationResult.illegal("Vorwärtszüge sind nur auf freie Felder erlaubt");
        }

        if (doubleMove) {
            Position mid = Position.of(move.from().file(), move.from().rank() + direction);
            if (context.board().getPiece(mid).isPresent() || context.targetPiece() != null) {
                return MoveValidationResult.illegal("Doppelzug blockiert");
            }
        }

        if (captureMove) {
            if (context.targetPiece() == null) {
                return MoveValidationResult.illegal("Schlagzüge benötigen eine gegnerische Figur");
            }
            if (context.targetPiece().color() == piece.color()) {
                return MoveValidationResult.illegal("Eigene Figuren können nicht geschlagen werden");
            }
        } else if (context.targetPiece() != null) {
            return MoveValidationResult.illegal("Baurn dürfen nicht ohne Diagonale schlagen");
        }

        return MoveValidationResult.SUCCESS;
    }

    private boolean isStartingRank(PieceColor color, Position position) {
        return (color == PieceColor.WHITE && position.rank() == 1)
                || (color == PieceColor.BLACK && position.rank() == 6);
    }
}

