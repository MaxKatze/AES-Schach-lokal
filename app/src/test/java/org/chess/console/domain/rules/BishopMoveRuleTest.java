package org.chess.console.domain.rules;

import org.chess.console.domain.board.Board;
import org.chess.console.domain.board.Position;
import org.chess.console.domain.move.Move;
import org.chess.console.domain.piece.Bishop;
import org.chess.console.domain.piece.PieceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BishopMoveRuleTest {

    private BishopMoveRule rule;
    private Board board;
    private Bishop whiteBishop;

    @BeforeEach
    void setUp() {
        rule = new BishopMoveRule();
        board = new Board();
        whiteBishop = new Bishop(PieceColor.WHITE);
    }

    @Test
    void supportsBishopType() {
        assertEquals(org.chess.console.domain.piece.PieceType.BISHOP, rule.supports());
    }

    @Test
    void allowsDiagonalMove() {
        board.placePiece(Position.fromNotation("e4"), whiteBishop);
        Move move = new Move(Position.fromNotation("e4"), Position.fromNotation("g6"));
        
        var result = rule.validate(new RuleContext(board, move, whiteBishop, null));
        
        assertTrue(result.legal());
    }

    @Test
    void rejectsHorizontalMove() {
        board.placePiece(Position.fromNotation("e4"), whiteBishop);
        Move move = new Move(Position.fromNotation("e4"), Position.fromNotation("h4"));
        
        var result = rule.validate(new RuleContext(board, move, whiteBishop, null));
        
        assertFalse(result.legal());
    }

    @Test
    void rejectsVerticalMove() {
        board.placePiece(Position.fromNotation("e4"), whiteBishop);
        Move move = new Move(Position.fromNotation("e4"), Position.fromNotation("e8"));
        
        var result = rule.validate(new RuleContext(board, move, whiteBishop, null));
        
        assertFalse(result.legal());
    }

    @Test
    void rejectsBlockedDiagonalPath() {
        board.placePiece(Position.fromNotation("e4"), whiteBishop);
        board.placePiece(Position.fromNotation("f5"), new Bishop(PieceColor.BLACK));
        Move move = new Move(Position.fromNotation("e4"), Position.fromNotation("g6"));
        
        var result = rule.validate(new RuleContext(board, move, whiteBishop, null));
        
        assertFalse(result.legal());
    }
}

