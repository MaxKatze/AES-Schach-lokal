package org.chess.console.domain.move;

import org.chess.console.domain.board.Position;
import org.chess.console.domain.piece.PieceColor;
import org.chess.console.domain.piece.PieceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class MoveHistoryTest {

    private MoveHistory history;

    @BeforeEach
    void setUp() {
        history = new MoveHistory();
    }

    @Test
    void initializesEmpty() {
        assertEquals(0, history.size());
        assertTrue(history.records().isEmpty());
    }

    @Test
    void appendsMoveRecord() {
        MoveRecord record = new MoveRecord(
                1, PieceType.PAWN, PieceColor.WHITE,
                new Move(Position.fromNotation("e2"), Position.fromNotation("e4")),
                false, false, false, Instant.now()
        );

        history.append(record);

        assertEquals(1, history.size());
        assertEquals(record, history.records().get(0));
    }

    @Test
    void appendsMultipleRecords() {
        MoveRecord record1 = new MoveRecord(
                1, PieceType.PAWN, PieceColor.WHITE,
                new Move(Position.fromNotation("e2"), Position.fromNotation("e4")),
                false, false, false, Instant.now()
        );
        MoveRecord record2 = new MoveRecord(
                2, PieceType.PAWN, PieceColor.BLACK,
                new Move(Position.fromNotation("e7"), Position.fromNotation("e5")),
                false, false, false, Instant.now()
        );

        history.append(record1);
        history.append(record2);

        assertEquals(2, history.size());
        assertEquals(record1, history.records().get(0));
        assertEquals(record2, history.records().get(1));
    }

    @Test
    void recordsAreUnmodifiable() {
        MoveRecord record = new MoveRecord(
                1, PieceType.PAWN, PieceColor.WHITE,
                new Move(Position.fromNotation("e2"), Position.fromNotation("e4")),
                false, false, false, Instant.now()
        );

        history.append(record);

        assertThrows(UnsupportedOperationException.class, () -> 
                history.records().clear());
    }
}

