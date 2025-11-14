package org.chess.console.application;

import org.chess.console.domain.board.Board;
import org.chess.console.domain.board.Position;
import org.chess.console.domain.game.Game;
import org.chess.console.domain.game.GameFactory;
import org.chess.console.domain.move.Move;
import org.chess.console.domain.piece.King;
import org.chess.console.domain.piece.PieceColor;
import org.chess.console.domain.piece.Queen;
import org.chess.console.domain.piece.Rook;
import org.chess.console.domain.player.Player;
import org.chess.console.domain.rules.RuleBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MoveCoordinatorTest {

    private MoveCoordinator coordinator;
    private GameFactory factory;

    @BeforeEach
    void setUp() {
        coordinator = new MoveCoordinator(RuleBook.defaultRuleBook());
        factory = new GameFactory();
    }

    @Test
    void executesLegalPawnMove() {
        Game game = factory.create("White", "Black");
        var result = coordinator.execute(game, new Move(
                Position.fromNotation("e2"),
                Position.fromNotation("e4")
        ));

        assertTrue(result.success());
        assertTrue(game.board().getPiece(Position.fromNotation("e4")).isPresent());
        assertTrue(game.board().getPiece(Position.fromNotation("e2")).isEmpty());
    }

    @Test
    void rejectsMoveThatLeavesKingInCheck() {
        Game game = pinnedPieceScenario();
        var result = coordinator.execute(game, new Move(
                Position.fromNotation("e2"),
                Position.fromNotation("f2")
        ));

        assertFalse(result.success());
        assertEquals("Zug lässt eigenen König im Schach", result.message());
        assertTrue(game.board().getPiece(Position.fromNotation("e2")).isPresent());
    }

    private Game pinnedPieceScenario() {
        Board board = new Board();
        board.placePiece(Position.fromNotation("e1"), new King(PieceColor.WHITE));
        board.placePiece(Position.fromNotation("e2"), new Queen(PieceColor.WHITE));
        board.placePiece(Position.fromNotation("h8"), new King(PieceColor.BLACK));
        board.placePiece(Position.fromNotation("e8"), new Rook(PieceColor.BLACK));

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

