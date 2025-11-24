package org.chess.console.domain.game;

import org.chess.console.domain.board.Board;
import org.chess.console.domain.board.Position;
import org.chess.console.domain.move.MoveRecord;
import org.chess.console.domain.piece.Pawn;
import org.chess.console.domain.piece.Piece;
import org.chess.console.domain.piece.PieceColor;
import org.chess.console.domain.piece.PieceType;
import org.chess.console.domain.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;
    private Player white;
    private Player black;
    private Board board;

    @BeforeEach
    void setUp() {
        white = new Player("White", PieceColor.WHITE);
        black = new Player("Black", PieceColor.BLACK);
        board = new Board();
        game = new Game(UUID.randomUUID(), white, black, board);
    }

    @Test
    void initializesWithReadyStatus() {
        assertEquals(GameStatus.READY, game.status());
        assertEquals(PieceColor.WHITE, game.activeColor());
    }

    @Test
    void startsGame() {
        game.start();
        
        assertEquals(GameStatus.IN_PROGRESS, game.status());
        assertEquals(PieceColor.WHITE, game.activeColor());
    }

    @Test
    void togglesTurn() {
        game.start();
        game.toggleTurn();
        
        assertEquals(PieceColor.BLACK, game.activeColor());
        
        game.toggleTurn();
        
        assertEquals(PieceColor.WHITE, game.activeColor());
    }

    @Test
    void flagsStatus() {
        game.flagStatus(GameStatus.CHECK);
        assertEquals(GameStatus.CHECK, game.status());
        
        game.flagStatus(GameStatus.CHECKMATE);
        assertEquals(GameStatus.CHECKMATE, game.status());
    }

    @Test
    void recordsMoves() {
        MoveRecord record = new MoveRecord(
                1,
                PieceType.PAWN,
                PieceColor.WHITE,
                new org.chess.console.domain.move.Move(
                        Position.fromNotation("e2"),
                        Position.fromNotation("e4")
                ),
                false,
                false,
                false,
                Instant.now()
        );
        
        game.recordMove(record);
        
        assertEquals(1, game.history().size());
        assertEquals(record, game.history().records().get(0));
    }

    @Test
    void calculatesTurnNumber() {
        assertEquals(1, game.turnNumber());
        
        game.recordMove(new MoveRecord(
                1, PieceType.PAWN, PieceColor.WHITE,
                new org.chess.console.domain.move.Move(
                        Position.fromNotation("e2"),
                        Position.fromNotation("e4")
                ),
                false, false, false, Instant.now()
        ));
        
        assertEquals(2, game.turnNumber());
    }

    @Test
    void returnsActivePlayer() {
        game.start();
        
        assertEquals(white, game.activePlayer());
        
        game.toggleTurn();
        
        assertEquals(black, game.activePlayer());
    }

    @Test
    void returnsPieceAtPosition() {
        Position pos = Position.fromNotation("e2");
        Piece pawn = new Pawn(PieceColor.WHITE);
        board.placePiece(pos, pawn);
        
        assertTrue(game.pieceAt(pos).isPresent());
        assertEquals(pawn, game.pieceAt(pos).get());
    }

    @Test
    void isOverReturnsFalseForInProgress() {
        game.start();
        assertFalse(game.isOver());
    }

    @Test
    void isOverReturnsTrueForCheckmate() {
        game.flagStatus(GameStatus.CHECKMATE);
        assertTrue(game.isOver());
    }

    @Test
    void isOverReturnsTrueForResigned() {
        game.flagStatus(GameStatus.RESIGNED);
        assertTrue(game.isOver());
    }

    @Test
    void isOverReturnsTrueForStalemate() {
        game.flagStatus(GameStatus.STALEMATE);
        assertTrue(game.isOver());
    }

    @Test
    void isOverReturnsTrueForDrawn() {
        game.flagStatus(GameStatus.DRAWN);
        assertTrue(game.isOver());
    }

    @Test
    void isOverReturnsFalseForCheck() {
        game.flagStatus(GameStatus.CHECK);
        assertFalse(game.isOver());
    }

    @Test
    void resignSetsStatusToResigned() {
        game.start();
        game.resign(PieceColor.WHITE);
        
        assertEquals(GameStatus.RESIGNED, game.status());
        assertEquals(PieceColor.BLACK, game.activeColor());
    }

    @Test
    void resignThrowsExceptionWhenGameAlreadyOver() {
        game.flagStatus(GameStatus.CHECKMATE);
        
        assertThrows(IllegalStateException.class, () -> game.resign(PieceColor.WHITE));
    }

    @Test
    void rejectsNullId() {
        assertThrows(NullPointerException.class, () -> 
                new Game(null, white, black, board));
    }

    @Test
    void rejectsNullWhite() {
        assertThrows(NullPointerException.class, () -> 
                new Game(UUID.randomUUID(), null, black, board));
    }

    @Test
    void rejectsNullBlack() {
        assertThrows(NullPointerException.class, () -> 
                new Game(UUID.randomUUID(), white, null, board));
    }

    @Test
    void rejectsNullBoard() {
        assertThrows(NullPointerException.class, () -> 
                new Game(UUID.randomUUID(), white, black, null));
    }
}

