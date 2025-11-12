package org.chess.console;

import org.chess.console.domain.board.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {

    @Test
    void algebraicNotationIsParsedCorrectly() {
        Position position = Position.fromNotation("e4");
        assertEquals("e4", position.notation());
        assertEquals(4, position.file());
        assertEquals(3, position.rank());
    }
}
