package org.chess.console.domain.rules;

import org.chess.console.domain.board.Board;
import org.chess.console.domain.board.Position;
import org.chess.console.domain.move.Move;
import org.chess.console.domain.piece.PieceColor;
import org.chess.console.domain.piece.Queen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueenMoveRuleTest {

    private QueenMoveRule rule;
    private Board board;
    private Queen whiteQueen;

    @BeforeEach
    void setUp() {
        rule = new QueenMoveRule();
        board = new Board();
        whiteQueen = new Queen(PieceColor.WHITE);
    }

    @Test
    void supportsQueenType() {
        assertEquals(org.chess.console.domain.piece.PieceType.QUEEN, rule.supports());
    }

    @Test
    void allowsHorizontalMove() {
        board.placePiece(Position.fromNotation("e4"), whiteQueen);
        Move move = new Move(Position.fromNotation("e4"), Position.fromNotation("h4"));
        
        var result = rule.validate(new RuleContext(board, move, whiteQueen, null));
        
        assertTrue(result.legal());
    }

    @Test
    void allowsVerticalMove() {
        board.placePiece(Position.fromNotation("e4"), whiteQueen);
        Move move = new Move(Position.fromNotation("e4"), Position.fromNotation("e8"));
        
        var result = rule.validate(new RuleContext(board, move, whiteQueen, null));
        
        assertTrue(result.legal());
    }

    @Test
    void allowsDiagonalMove() {
        board.placePiece(Position.fromNotation("e4"), whiteQueen);
        Move move = new Move(Position.fromNotation("e4"), Position.fromNotation("g6"));
        
        var result = rule.validate(new RuleContext(board, move, whiteQueen, null));
        
        assertTrue(result.legal());
    }

    @Test
    void rejectsLShapeMove() {
        board.placePiece(Position.fromNotation("e4"), whiteQueen);
        Move move = new Move(Position.fromNotation("e4"), Position.fromNotation("f6"));
        
        var result = rule.validate(new RuleContext(board, move, whiteQueen, null));
        
        assertFalse(result.legal());
    }

    @Test
    void rejectsBlockedPath() {
        board.placePiece(Position.fromNotation("e4"), whiteQueen);
        board.placePiece(Position.fromNotation("e6"), new Queen(PieceColor.BLACK));
        Move move = new Move(Position.fromNotation("e4"), Position.fromNotation("e8"));
        
        var result = rule.validate(new RuleContext(board, move, whiteQueen, null));
        
        assertFalse(result.legal());
    }
}

