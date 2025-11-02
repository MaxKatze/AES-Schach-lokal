package org.chess.console.application;

import org.chess.console.domain.game.Game;
import org.chess.console.domain.game.GameFactory;
import org.chess.console.domain.move.Move;
import org.chess.console.domain.piece.PieceColor;
import org.chess.console.infrastructure.repository.GameRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Application service boundary for UI adapters.
 */
public class GameService {

    private final GameRepository repository;
    private final MoveCoordinator moveCoordinator;
    private final TurnManager turnManager;
    private final GameFactory gameFactory;
    private UUID activeGameId;

    public GameService(GameRepository repository,
                       MoveCoordinator moveCoordinator,
                       TurnManager turnManager,
                       GameFactory gameFactory) {
        this.repository = repository;
        this.moveCoordinator = moveCoordinator;
        this.turnManager = turnManager;
        this.gameFactory = gameFactory;
    }

    public Game startNewGame(String whiteName, String blackName) {
        Game game = gameFactory.create(whiteName, blackName);
        repository.save(game);
        activeGameId = game.id();
        return game;
    }

    public Optional<Game> activeGame() {
        return activeGameId == null ? Optional.empty() : repository.findById(activeGameId);
    }

    public MoveEvaluation playMove(Move move) {
        Game game = activeGame().orElseThrow(() -> new IllegalStateException("Es läuft noch keine Partie"));
        MoveEvaluation evaluation = moveCoordinator.execute(game, move);
        if (evaluation.success()) {
            if (!evaluation.outcome().checkmate()) {
                turnManager.advance(game);
            }
            repository.save(game);
        }
        return evaluation;
    }

    public void resign(PieceColor color) {
        Game game = activeGame().orElseThrow(() -> new IllegalStateException("Keine aktive Partie"));
        turnManager.resign(game, color);
        repository.save(game);
    }
}

