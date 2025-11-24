package org.chess.console.application;

import org.chess.console.domain.board.Position;
import org.chess.console.domain.game.Game;
import org.chess.console.domain.game.GameFactory;
import org.chess.console.domain.game.GameStatus;
import org.chess.console.domain.move.Move;
import org.chess.console.domain.piece.Pawn;
import org.chess.console.domain.piece.PieceColor;
import org.chess.console.domain.rules.RuleBook;
import org.chess.console.infrastructure.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private FakeGameRepository repository;
    private StubMoveCoordinator moveCoordinator;
    private TurnManager turnManager;
    private GameFactory factory;
    private GameService service;

    @BeforeEach
    void setUp() {
        repository = new FakeGameRepository();
        moveCoordinator = new StubMoveCoordinator();
        turnManager = new TurnManager();
        factory = new GameFactory();
        service = new GameService(repository, moveCoordinator, turnManager, factory);
    }

    @Test
    void startNewGamePersistsAndActivates() {
        Game game = service.startNewGame("Anna", "Bert");

        assertNotNull(game.id());
        assertEquals(1, repository.saveCount());
        assertTrue(service.activeGame().isPresent());
    }

    @Test
    void playMoveSuccessAdvancesTurnAndPersists() {
        service.startNewGame("Anna", "Bert");
        moveCoordinator.willReturnSuccess();

        Move move = new Move(Position.fromNotation("e2"), Position.fromNotation("e4"));
        var evaluation = service.playMove(move);

        assertTrue(evaluation.success());
        assertEquals(PieceColor.BLACK, service.activeGame().orElseThrow().activeColor());
        assertEquals(2, repository.saveCount());
    }

    @Test
    void playMoveFailureDoesNotAdvanceTurnOrPersist() {
        service.startNewGame("Anna", "Bert");
        moveCoordinator.willReturnFailure("Fehler");

        Move move = new Move(Position.fromNotation("e2"), Position.fromNotation("e5"));
        var evaluation = service.playMove(move);

        assertFalse(evaluation.success());
        assertEquals(PieceColor.WHITE, service.activeGame().orElseThrow().activeColor());
        assertEquals(1, repository.saveCount());
    }

    private static final class FakeGameRepository implements GameRepository {
        private final Map<UUID, Game> games = new HashMap<>();
        private int saveCount;

        @Override
        public void save(Game game) {
            saveCount++;
            games.put(game.id(), game);
        }

        @Override
        public Optional<Game> findById(UUID id) {
            return Optional.ofNullable(games.get(id));
        }

        int saveCount() {
            return saveCount;
        }
    }

    private static final class StubMoveCoordinator extends MoveCoordinator {
        private MoveEvaluation nextResult;

        StubMoveCoordinator() {
            super(RuleBook.defaultRuleBook());
        }

        void willReturnSuccess() {
            nextResult = MoveEvaluation.success(new MoveOutcome(new Pawn(PieceColor.WHITE), false, false, false, "test"));
        }

        void willReturnFailure(String message) {
            nextResult = MoveEvaluation.failure(message);
        }

        @Override
        public MoveEvaluation execute(Game game, Move move) {
            if (nextResult == null) {
                throw new IllegalStateException("No result configured");
            }
            return nextResult;
        }
    }

    @Test
    void resignThrowsExceptionWhenGameIsAlreadyOver() {
        service.startNewGame("Anna", "Bert");
        Game game = service.activeGame().orElseThrow();
        game.flagStatus(GameStatus.CHECKMATE);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            service.resign(PieceColor.WHITE);
        });

        assertTrue(exception.getMessage().contains("beendet"));
    }

    @Test
    void resignSucceedsWhenGameIsInProgress() {
        service.startNewGame("Anna", "Bert");

        assertDoesNotThrow(() -> {
            service.resign(PieceColor.WHITE);
        });

        Game game = service.activeGame().orElseThrow();
        assertEquals(GameStatus.RESIGNED, game.status());
    }
}

