package org.chess.console.domain.rules;

import org.chess.console.domain.board.Board;
import org.chess.console.domain.board.Position;
import org.chess.console.domain.piece.King;
import org.chess.console.domain.piece.PieceColor;
import org.chess.console.domain.piece.Queen;
import org.chess.console.domain.piece.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckInspectorTest {

    private CheckInspector inspector;
    private Board board;

    @BeforeEach
    void setUp() {
        inspector = new CheckInspector(RuleBook.defaultRuleBook());
        board = new Board();
    }

    @Test
    void detectsCheckFromRook() {
        board.placePiece(Position.fromNotation("e1"), new King(PieceColor.WHITE));
        board.placePiece(Position.fromNotation("e8"), new Rook(PieceColor.BLACK));
        
        assertTrue(inspector.isKingInCheck(board, PieceColor.WHITE));
    }

    @Test
    void detectsCheckFromQueen() {
        board.placePiece(Position.fromNotation("e1"), new King(PieceColor.WHITE));
        board.placePiece(Position.fromNotation("h4"), new Queen(PieceColor.BLACK));
        
        assertTrue(inspector.isKingInCheck(board, PieceColor.WHITE));
    }

    @Test
    void detectsNoCheckWhenKingIsSafe() {
        board.placePiece(Position.fromNotation("e1"), new King(PieceColor.WHITE));
        board.placePiece(Position.fromNotation("e8"), new King(PieceColor.BLACK));
        
        assertFalse(inspector.isKingInCheck(board, PieceColor.WHITE));
    }

    @Test
    void detectsNoCheckWhenPieceIsBlocked() {
        board.placePiece(Position.fromNotation("e1"), new King(PieceColor.WHITE));
        board.placePiece(Position.fromNotation("e3"), new Rook(PieceColor.WHITE));
        board.placePiece(Position.fromNotation("e8"), new Rook(PieceColor.BLACK));
        
        assertFalse(inspector.isKingInCheck(board, PieceColor.WHITE));
    }

    @Test
    void detectsCheckFromDiagonal() {
        board.placePiece(Position.fromNotation("e1"), new King(PieceColor.WHITE));
        board.placePiece(Position.fromNotation("h4"), new Queen(PieceColor.BLACK));
        
        assertTrue(inspector.isKingInCheck(board, PieceColor.WHITE));
    }

    @Test
    void detectsCheckForBlackKing() {
        board.placePiece(Position.fromNotation("e8"), new King(PieceColor.BLACK));
        board.placePiece(Position.fromNotation("e1"), new Rook(PieceColor.WHITE));
        
        assertTrue(inspector.isKingInCheck(board, PieceColor.BLACK));
    }

    @Test
    void throwsExceptionWhenKingNotFound() {
        assertThrows(IllegalStateException.class, () -> 
                inspector.isKingInCheck(board, PieceColor.WHITE));
    }
}

