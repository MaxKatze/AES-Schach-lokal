package org.chess.console.domain.piece;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceColorTest {

    @Test
    void oppositeReturnsCorrectColor() {
        assertEquals(PieceColor.BLACK, PieceColor.WHITE.opposite());
        assertEquals(PieceColor.WHITE, PieceColor.BLACK.opposite());
    }

    @Test
    void forwardDirectionIsCorrect() {
        assertEquals(1, PieceColor.WHITE.forwardDirection());
        assertEquals(-1, PieceColor.BLACK.forwardDirection());
    }

    @Test
    void fromTurnReturnsCorrectColor() {
        assertEquals(PieceColor.WHITE, PieceColor.fromTurn(0));
        assertEquals(PieceColor.BLACK, PieceColor.fromTurn(1));
        assertEquals(PieceColor.WHITE, PieceColor.fromTurn(2));
        assertEquals(PieceColor.BLACK, PieceColor.fromTurn(3));
        assertEquals(PieceColor.WHITE, PieceColor.fromTurn(10));
        assertEquals(PieceColor.BLACK, PieceColor.fromTurn(11));
    }

    @Test
    void oppositeIsIdempotent() {
        PieceColor white = PieceColor.WHITE;
        PieceColor black = PieceColor.BLACK;
        
        assertEquals(white, white.opposite().opposite());
        assertEquals(black, black.opposite().opposite());
    }
}

