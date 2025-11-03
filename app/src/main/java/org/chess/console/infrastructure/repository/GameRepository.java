package org.chess.console.infrastructure.repository;

import org.chess.console.domain.game.Game;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository abstraction to comply with DDD requirements.
 */
public interface GameRepository {
    void save(Game game);

    Optional<Game> findById(UUID id);
}

