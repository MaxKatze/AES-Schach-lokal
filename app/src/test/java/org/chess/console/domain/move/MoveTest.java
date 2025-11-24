package org.chess.console.domain.move;

import org.chess.console.domain.board.Position;
import org.chess.console.domain.piece.PieceType;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    @Test
    void createsMoveWithoutPromotion() {
        Position from = Position.fromNotation("e2");
        Position to = Position.fromNotation("e4");
        Move move = new Move(from, to);
        
        assertEquals(from, move.from());
        assertEquals(to, move.to());
        assertTrue(move.promotion().isEmpty());
    }

    @Test
    void createsMoveWithPromotion() {
        Position from = Position.fromNotation("e7");
        Position to = Position.fromNotation("e8");
        Move move = new Move(from, to, Optional.of(PieceType.QUEEN));
        
        assertEquals(from, move.from());
        assertEquals(to, move.to());
        assertTrue(move.promotion().isPresent());
        assertEquals(PieceType.QUEEN, move.promotion().get());
    }

    @Test
    void rejectsNullFrom() {
        Position to = Position.fromNotation("e4");
        assertThrows(NullPointerException.class, () -> new Move(null, to));
    }

    @Test
    void rejectsNullTo() {
        Position from = Position.fromNotation("e2");
        assertThrows(NullPointerException.class, () -> new Move(from, null));
    }

    @Test
    void rejectsNullPromotion() {
        Position from = Position.fromNotation("e2");
        Position to = Position.fromNotation("e4");
        assertThrows(NullPointerException.class, () -> new Move(from, to, null));
    }

    @Test
    void toStringIncludesPromotion() {
        Position from = Position.fromNotation("e7");
        Position to = Position.fromNotation("e8");
        Move moveWithPromotion = new Move(from, to, Optional.of(PieceType.QUEEN));
        Move moveWithoutPromotion = new Move(from, to);
        
        assertTrue(moveWithPromotion.toString().contains("Q"));
        assertFalse(moveWithoutPromotion.toString().contains("="));
    }
}

