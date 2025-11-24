package org.chess.console.domain.rules;

import org.chess.console.domain.board.Board;
import org.chess.console.domain.board.Position;
import org.chess.console.domain.move.Move;
import org.chess.console.domain.piece.PieceColor;
import org.chess.console.domain.piece.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RookMoveRuleTest {

    private RookMoveRule rule;
    private Board board;
    private Rook whiteRook;

    @BeforeEach
    void setUp() {
        rule = new RookMoveRule();
        board = new Board();
        whiteRook = new Rook(PieceColor.WHITE);
    }

    @Test
    void supportsRookType() {
        assertEquals(org.chess.console.domain.piece.PieceType.ROOK, rule.supports());
    }

    @Test
    void allowsHorizontalMove() {
        board.placePiece(Position.fromNotation("e4"), whiteRook);
        Move move = new Move(Position.fromNotation("e4"), Position.fromNotation("h4"));
        
        var result = rule.validate(new RuleContext(board, move, whiteRook, null));
        
        assertTrue(result.legal());
    }

    @Test
    void allowsVerticalMove() {
        board.placePiece(Position.fromNotation("e4"), whiteRook);
        Move move = new Move(Position.fromNotation("e4"), Position.fromNotation("e8"));
        
        var result = rule.validate(new RuleContext(board, move, whiteRook, null));
        
        assertTrue(result.legal());
    }

    @Test
    void rejectsDiagonalMove() {
        board.placePiece(Position.fromNotation("e4"), whiteRook);
        Move move = new Move(Position.fromNotation("e4"), Position.fromNotation("g6"));
        
        var result = rule.validate(new RuleContext(board, move, whiteRook, null));
        
        assertFalse(result.legal());
    }

    @Test
    void rejectsLShapeMove() {
        board.placePiece(Position.fromNotation("e4"), whiteRook);
        Move move = new Move(Position.fromNotation("e4"), Position.fromNotation("f6"));
        
        var result = rule.validate(new RuleContext(board, move, whiteRook, null));
        
        assertFalse(result.legal());
    }

    @Test
    void rejectsBlockedPath() {
        board.placePiece(Position.fromNotation("e4"), whiteRook);
        board.placePiece(Position.fromNotation("e6"), new Rook(PieceColor.BLACK));
        Move move = new Move(Position.fromNotation("e4"), Position.fromNotation("e8"));
        
        var result = rule.validate(new RuleContext(board, move, whiteRook, null));
        
        assertFalse(result.legal());
    }
}

