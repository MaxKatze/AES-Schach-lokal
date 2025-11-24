package org.chess.console.domain.piece;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PawnTest {

    @Test
    void createsPawnWithColor() {
        Pawn whitePawn = new Pawn(PieceColor.WHITE);
        Pawn blackPawn = new Pawn(PieceColor.BLACK);

        assertEquals(PieceColor.WHITE, whitePawn.color());
        assertEquals(PieceColor.BLACK, blackPawn.color());
        assertEquals(PieceType.PAWN, whitePawn.type());
        assertFalse(whitePawn.hasMoved());
    }

    @Test
    void movedReturnsNewPawnWithMovedFlag() {
        Pawn pawn = new Pawn(PieceColor.WHITE);
        Piece moved = pawn.moved();

        assertNotSame(pawn, moved);
        assertTrue(moved.hasMoved());
        assertEquals(pawn.color(), moved.color());
        assertEquals(pawn.type(), moved.type());
    }

    @Test
    void copyReturnsNewPawnWithSameState() {
        Pawn original = new Pawn(PieceColor.WHITE);
        Piece copy = original.copy();

        assertNotSame(original, copy);
        assertEquals(original.color(), copy.color());
        assertEquals(original.type(), copy.type());
        assertEquals(original.hasMoved(), copy.hasMoved());
    }

    @Test
    void copyPreservesMovedState() {
        Pawn original = new Pawn(PieceColor.WHITE);
        Piece moved = original.moved();
        Piece copy = moved.copy();

        assertTrue(copy.hasMoved());
    }
}

