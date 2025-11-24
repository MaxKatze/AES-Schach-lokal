package org.chess.console.domain.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void createsPositionFromCoordinates() {
        Position pos = Position.of(0, 0);
        assertEquals(0, pos.file());
        assertEquals(0, pos.rank());
    }

    @Test
    void createsPositionFromAlgebraicNotation() {
        Position pos = Position.fromNotation("e4");
        assertEquals(4, pos.file());
        assertEquals(3, pos.rank());
        assertEquals("e4", pos.notation());
    }

    @Test
    void convertsAllAlgebraicNotations() {
        assertEquals("a1", Position.fromNotation("a1").notation());
        assertEquals("h8", Position.fromNotation("h8").notation());
        assertEquals("d5", Position.fromNotation("d5").notation());
    }

    @Test
    void rejectsInvalidFile() {
        assertThrows(IllegalArgumentException.class, () -> Position.of(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> Position.of(8, 0));
    }

    @Test
    void rejectsInvalidRank() {
        assertThrows(IllegalArgumentException.class, () -> Position.of(0, -1));
        assertThrows(IllegalArgumentException.class, () -> Position.of(0, 8));
    }

    @Test
    void rejectsInvalidAlgebraicNotation() {
        assertThrows(IllegalArgumentException.class, () -> Position.fromNotation(null));
        assertThrows(IllegalArgumentException.class, () -> Position.fromNotation(""));
        assertThrows(IllegalArgumentException.class, () -> Position.fromNotation("a"));
        assertThrows(IllegalArgumentException.class, () -> Position.fromNotation("a12"));
        assertThrows(IllegalArgumentException.class, () -> Position.fromNotation("i1"));
        assertThrows(IllegalArgumentException.class, () -> Position.fromNotation("a0"));
        assertThrows(IllegalArgumentException.class, () -> Position.fromNotation("a9"));
    }

    @Test
    void calculatesDeltaFile() {
        Position from = Position.fromNotation("e4");
        Position to = Position.fromNotation("g4");
        assertEquals(2, from.deltaFile(to));
        assertEquals(-2, to.deltaFile(from));
    }

    @Test
    void calculatesDeltaRank() {
        Position from = Position.fromNotation("e4");
        Position to = Position.fromNotation("e6");
        assertEquals(2, from.deltaRank(to));
        assertEquals(-2, to.deltaRank(from));
    }

    @Test
    void calculatesManhattanDistance() {
        Position a1 = Position.fromNotation("a1");
        Position h8 = Position.fromNotation("h8");
        assertEquals(14, a1.manhattanDistance(h8));
        
        Position e4 = Position.fromNotation("e4");
        Position e5 = Position.fromNotation("e5");
        assertEquals(1, e4.manhattanDistance(e5));
    }

    @Test
    void detectsAdjacentPositions() {
        Position center = Position.fromNotation("e4");
        
        assertTrue(center.isAdjacent(Position.fromNotation("e5")));
        assertTrue(center.isAdjacent(Position.fromNotation("e3")));
        assertTrue(center.isAdjacent(Position.fromNotation("d4")));
        assertTrue(center.isAdjacent(Position.fromNotation("f4")));
        assertTrue(center.isAdjacent(Position.fromNotation("d5")));
        assertTrue(center.isAdjacent(Position.fromNotation("f5")));
        assertTrue(center.isAdjacent(Position.fromNotation("d3")));
        assertTrue(center.isAdjacent(Position.fromNotation("f3")));
        
        assertFalse(center.isAdjacent(Position.fromNotation("e6")));
        assertFalse(center.isAdjacent(Position.fromNotation("c4")));
        assertFalse(center.isAdjacent(Position.fromNotation("g6")));
    }

    @Test
    void equalsAndHashCode() {
        Position pos1 = Position.fromNotation("e4");
        Position pos2 = Position.fromNotation("e4");
        Position pos3 = Position.fromNotation("d4");
        
        assertEquals(pos1, pos2);
        assertNotEquals(pos1, pos3);
        assertEquals(pos1.hashCode(), pos2.hashCode());
    }

    @Test
    void toStringReturnsNotation() {
        Position pos = Position.fromNotation("e4");
        assertEquals("e4", pos.toString());
    }
}

