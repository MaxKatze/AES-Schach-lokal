package org.chess.console.domain.move;

import org.chess.console.domain.board.Position;
import org.chess.console.domain.piece.PieceColor;
import org.chess.console.domain.piece.PieceType;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class MoveRecordTest {

    @Test
    void createsMoveRecord() {
        Move move = new Move(Position.fromNotation("e2"), Position.fromNotation("e4"));
        MoveRecord record = new MoveRecord(
                1, PieceType.PAWN, PieceColor.WHITE, move, false, false, false, Instant.now()
        );

        assertEquals(1, record.turn());
        assertEquals(PieceType.PAWN, record.piece());
        assertEquals(PieceColor.WHITE, record.color());
        assertEquals(move, record.move());
        assertFalse(record.capture());
        assertFalse(record.check());
        assertFalse(record.checkmate());
    }

    @Test
    void notationIncludesCaptureMarker() {
        Move move = new Move(Position.fromNotation("e2"), Position.fromNotation("e4"));
        MoveRecord capture = new MoveRecord(
                1, PieceType.PAWN, PieceColor.WHITE, move, true, false, false, Instant.now()
        );
        MoveRecord noCapture = new MoveRecord(
                1, PieceType.PAWN, PieceColor.WHITE, move, false, false, false, Instant.now()
        );

        assertTrue(capture.notation().contains("x"));
        assertTrue(noCapture.notation().contains("-"));
    }

    @Test
    void notationIncludesCheckMarker() {
        Move move = new Move(Position.fromNotation("e2"), Position.fromNotation("e4"));
        MoveRecord check = new MoveRecord(
                1, PieceType.PAWN, PieceColor.WHITE, move, false, true, false, Instant.now()
        );
        MoveRecord noCheck = new MoveRecord(
                1, PieceType.PAWN, PieceColor.WHITE, move, false, false, false, Instant.now()
        );

        assertTrue(check.notation().contains("+"));
        assertFalse(noCheck.notation().contains("+"));
    }

    @Test
    void notationIncludesCheckmateMarker() {
        Move move = new Move(Position.fromNotation("e2"), Position.fromNotation("e4"));
        MoveRecord checkmate = new MoveRecord(
                1, PieceType.PAWN, PieceColor.WHITE, move, false, true, true, Instant.now()
        );
        MoveRecord check = new MoveRecord(
                1, PieceType.PAWN, PieceColor.WHITE, move, false, true, false, Instant.now()
        );

        assertTrue(checkmate.notation().contains("#"));
        assertFalse(check.notation().contains("#"));
    }

    @Test
    void notationIncludesTurnAndPiece() {
        Move move = new Move(Position.fromNotation("e2"), Position.fromNotation("e4"));
        MoveRecord record = new MoveRecord(
                5, PieceType.QUEEN, PieceColor.BLACK, move, false, false, false, Instant.now()
        );

        String notation = record.notation();
        assertTrue(notation.contains("5"));
        assertTrue(notation.contains("e4"));
    }
}

