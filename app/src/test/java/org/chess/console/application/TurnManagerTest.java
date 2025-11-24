package org.chess.console.application;

import org.chess.console.domain.board.Board;
import org.chess.console.domain.game.Game;
import org.chess.console.domain.game.GameStatus;
import org.chess.console.domain.piece.PieceColor;
import org.chess.console.domain.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TurnManagerTest {

    private TurnManager turnManager;
    private Game game;

    @BeforeEach
    void setUp() {
        turnManager = new TurnManager();
        game = new Game(
                UUID.randomUUID(),
                new Player("White", PieceColor.WHITE),
                new Player("Black", PieceColor.BLACK),
                new Board()
        );
        game.start();
    }

    @Test
    void currentReturnsActiveColor() {
        assertEquals(PieceColor.WHITE, turnManager.current(game));
        
        game.toggleTurn();
        
        assertEquals(PieceColor.BLACK, turnManager.current(game));
    }

    @Test
    void advanceTogglesTurn() {
        assertEquals(PieceColor.WHITE, game.activeColor());
        
        turnManager.advance(game);
        
        assertEquals(PieceColor.BLACK, game.activeColor());
        
        turnManager.advance(game);
        
        assertEquals(PieceColor.WHITE, game.activeColor());
    }

    @Test
    void resignSetsGameToResigned() {
        turnManager.resign(game, PieceColor.WHITE);
        
        assertEquals(GameStatus.RESIGNED, game.status());
        assertEquals(PieceColor.BLACK, game.activeColor());
    }

    @Test
    void resignThrowsExceptionWhenGameAlreadyOver() {
        game.flagStatus(GameStatus.CHECKMATE);
        
        assertThrows(IllegalStateException.class, () -> 
                turnManager.resign(game, PieceColor.WHITE));
    }
}

