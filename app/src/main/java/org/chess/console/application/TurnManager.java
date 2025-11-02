package org.chess.console.application;

import org.chess.console.domain.game.Game;
import org.chess.console.domain.game.GameStatus;
import org.chess.console.domain.piece.PieceColor;

/**
 * Keeps track of which player is allowed to move.
 */
public class TurnManager {

    public PieceColor current(Game game) {
        return game.activeColor();
    }

    public void advance(Game game) {
        game.toggleTurn();
    }

    public void resign(Game game, PieceColor color) {
        game.resign(color);
        game.flagStatus(GameStatus.RESIGNED);
    }
}

