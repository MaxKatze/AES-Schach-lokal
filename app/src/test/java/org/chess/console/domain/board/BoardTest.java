package org.chess.console.domain.board;

import org.chess.console.domain.piece.Pawn;
import org.chess.console.domain.piece.Piece;
import org.chess.console.domain.piece.PieceColor;
import org.chess.console.domain.piece.Queen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void initializesEmptyBoard() {
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                Position pos = Position.of(file, rank);
                assertTrue(board.getPiece(pos).isEmpty());
            }
        }
    }

    @Test
    void placesPieceOnBoard() {
        Position pos = Position.fromNotation("e4");
        Piece pawn = new Pawn(PieceColor.WHITE);
        
        board.placePiece(pos, pawn);
        
        assertTrue(board.getPiece(pos).isPresent());
        assertEquals(pawn, board.getPiece(pos).get());
    }

    @Test
    void movesPieceFromTo() {
        Position from = Position.fromNotation("e2");
        Position to = Position.fromNotation("e4");
        Piece pawn = new Pawn(PieceColor.WHITE);
        
        board.placePiece(from, pawn);
        board.move(from, to);
        
        assertTrue(board.getPiece(from).isEmpty());
        assertTrue(board.getPiece(to).isPresent());
        assertTrue(board.getPiece(to).get().hasMoved());
    }

    @Test
    void movesPieceWithReplacement() {
        Position from = Position.fromNotation("e7");
        Position to = Position.fromNotation("e8");
        Piece pawn = new Pawn(PieceColor.WHITE);
        Piece queen = new Queen(PieceColor.WHITE);
        
        board.placePiece(from, pawn);
        board.move(from, to, queen);
        
        assertTrue(board.getPiece(from).isEmpty());
        assertTrue(board.getPiece(to).isPresent());
        assertEquals(queen, board.getPiece(to).get());
    }

    @Test
    void moveThrowsExceptionWhenNoPieceOnSource() {
        Position from = Position.fromNotation("e2");
        Position to = Position.fromNotation("e4");
        
        assertThrows(IllegalStateException.class, () -> board.move(from, to));
    }

    @Test
    void capturesPieceWhenMoving() {
        Position from = Position.fromNotation("e2");
        Position to = Position.fromNotation("e4");
        Piece whitePawn = new Pawn(PieceColor.WHITE);
        Piece blackPawn = new Pawn(PieceColor.BLACK);
        
        board.placePiece(from, whitePawn);
        board.placePiece(to, blackPawn);
        board.move(from, to);
        
        assertTrue(board.getPiece(from).isEmpty());
        assertTrue(board.getPiece(to).isPresent());
        assertEquals(whitePawn.color(), board.getPiece(to).get().color());
    }

    @Test
    void copiesBoardCorrectly() {
        Position pos1 = Position.fromNotation("e2");
        Position pos2 = Position.fromNotation("e7");
        Piece whitePawn = new Pawn(PieceColor.WHITE);
        Piece blackPawn = new Pawn(PieceColor.BLACK);
        
        board.placePiece(pos1, whitePawn);
        board.placePiece(pos2, blackPawn);
        
        Board copy = board.copy();
        
        assertTrue(copy.getPiece(pos1).isPresent());
        assertTrue(copy.getPiece(pos2).isPresent());
        assertEquals(whitePawn.color(), copy.getPiece(pos1).get().color());
        assertEquals(blackPawn.color(), copy.getPiece(pos2).get().color());
        
        // Modifying copy should not affect original
        copy.move(pos1, Position.fromNotation("e4"));
        assertTrue(board.getPiece(pos1).isPresent());
        assertTrue(copy.getPiece(pos1).isEmpty());
    }

    @Test
    void piecesReturnsAllPiecesOnBoard() {
        board.placePiece(Position.fromNotation("e2"), new Pawn(PieceColor.WHITE));
        board.placePiece(Position.fromNotation("e7"), new Pawn(PieceColor.BLACK));
        board.placePiece(Position.fromNotation("d1"), new Queen(PieceColor.WHITE));
        
        var pieces = board.pieces();
        
        assertEquals(3, pieces.size());
    }

    @Test
    void piecesReturnsEmptyListForEmptyBoard() {
        var pieces = board.pieces();
        assertTrue(pieces.isEmpty());
    }
}

