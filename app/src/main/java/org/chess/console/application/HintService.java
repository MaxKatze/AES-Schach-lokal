package org.chess.console.application;

import org.chess.console.domain.game.Game;
import org.chess.console.domain.move.Move;
import org.chess.console.domain.rules.CheckInspector;
import org.chess.console.domain.rules.LegalMoveExplorer;
import org.chess.console.domain.rules.RuleBook;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Provides simple move suggestions to the UI.
 */
public class HintService {

    private final LegalMoveExplorer explorer;
    private final Random random = new Random();

    public HintService(RuleBook ruleBook) {
        this.explorer = new LegalMoveExplorer(ruleBook, new CheckInspector(ruleBook));
    }

    public Optional<Move> suggestMove(Game game) {
        List<Move> moves = explorer.legalMoves(game, game.activeColor());
        if (moves.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(moves.get(random.nextInt(moves.size())));
    }
}

