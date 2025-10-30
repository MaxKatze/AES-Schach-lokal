package org.chess.console.domain.rules;

import org.chess.console.domain.board.Board;
import org.chess.console.domain.move.Move;
import org.chess.console.domain.piece.Piece;
import org.chess.console.domain.piece.PieceType;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Registry of movement strategies. Implements the Strategy pattern.
 */
public final class RuleBook {
    private final Map<PieceType, MoveRule> rules = new EnumMap<>(PieceType.class);

    public RuleBook(List<MoveRule> moveRules) {
        moveRules.forEach(rule -> rules.put(rule.supports(), rule));
    }

    public MoveValidationResult validate(Board board, Move move, Piece movingPiece, Piece targetPiece) {
        MoveRule rule = rules.get(movingPiece.type());
        if (rule == null) {
            return MoveValidationResult.illegal("Keine Regeln für " + movingPiece.type());
        }
        return rule.validate(new RuleContext(board, move, movingPiece, targetPiece));
    }

    public static RuleBook defaultRuleBook() {
        return new RuleBook(List.of(
                new KingMoveRule(),
                new QueenMoveRule(),
                new RookMoveRule(),
                new BishopMoveRule(),
                new KnightMoveRule(),
                new PawnMoveRule()
        ));
    }
}

