package org.chess.console.domain.board;

import java.util.Objects;

/**
 * Immutable value object describing a coordinate on the chess board.
 */
public final class Position {

    private final int file; // 0 - 7 representing 'a' - 'h'
    private final int rank; // 0 - 7 representing 1 - 8

    private Position(int file, int rank) {
        this.file = file;
        this.rank = rank;
    }

    public static Position of(int file, int rank) {
        if (file < 0 || file > 7 || rank < 0 || rank > 7) {
            throw new IllegalArgumentException("Position out of bounds: file=" + file + ", rank=" + rank);
        }
        return new Position(file, rank);
    }

    public static Position fromNotation(String algebraic) {
        if (algebraic == null || algebraic.length() != 2) {
            throw new IllegalArgumentException("Invalid coordinate: " + algebraic);
        }
        int file = algebraic.charAt(0) - 'a';
        int rank = Character.getNumericValue(algebraic.charAt(1)) - 1;
        return of(file, rank);
    }

    public String notation() {
        return (char) ('a' + file) + Integer.toString(rank + 1);
    }

    public int file() {
        return file;
    }

    public int rank() {
        return rank;
    }

    public int deltaFile(Position other) {
        return other.file - this.file;
    }

    public int deltaRank(Position other) {
        return other.rank - this.rank;
    }

    public int manhattanDistance(Position other) {
        return Math.abs(deltaFile(other)) + Math.abs(deltaRank(other));
    }

    public boolean isAdjacent(Position other) {
        return Math.abs(deltaFile(other)) <= 1 && Math.abs(deltaRank(other)) <= 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return file == position.file && rank == position.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, rank);
    }

    @Override
    public String toString() {
        return notation();
    }
}

