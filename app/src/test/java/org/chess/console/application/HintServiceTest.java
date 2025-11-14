package org.chess.console.application;

import org.chess.console.domain.board.Board;
import org.chess.console.domain.board.Position;
import org.chess.console.domain.game.Game;
import org.chess.console.domain.game.GameFactory;
import org.chess.console.domain.piece.King;
import org.chess.console.domain.piece.PieceColor;
import org.chess.console.domain.piece.Queen;
import org.chess.console.domain.player.Player;
import org.chess.console.domain.rules.RuleBook;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class HintServiceTest {

    @Test
    void suggestsMoveWhenAvailable() {
        HintService service = new HintService(RuleBook.defaultRuleBook());
        Game game = new GameFactory().create("Alice", "Bob");

        assertTrue(service.suggestMove(game).isPresent());
    }

    @Test
    void returnsEmptyWhenNoLegalMovesExist() {
        HintService service = new HintService(RuleBook.defaultRuleBook());
        Game game = checkmateScenario();

        assertTrue(service.suggestMove(game).isEmpty());
    }

    private Game checkmateScenario() {
        Board board = new Board();
        board.placePiece(Position.fromNotation("h1"), new King(PieceColor.WHITE));
        board.placePiece(Position.fromNotation("g2"), new Queen(PieceColor.BLACK));
        board.placePiece(Position.fromNotation("f2"), new King(PieceColor.BLACK));

        Game game = new Game(
                UUID.randomUUID(),
                new Player("White", PieceColor.WHITE),
                new Player("Black", PieceColor.BLACK),
                board
        );
        game.start();
        return game;
    }
}

