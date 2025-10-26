package org.chess.console.domain.rules;

import org.chess.console.domain.board.Board;
import org.chess.console.domain.move.Move;
import org.chess.console.domain.piece.Piece;
import org.chess.console.domain.piece.PieceColor;

/**
 * Provides the data required by a rule to validate a move.
 */
public final class RuleContext {
    private final Move move;
    private final Piece movingPiece;
    private final Piece targetPiece;
    private final Board board;

    public RuleContext(Board board, Move move, Piece movingPiece, Piece targetPiece) {
        this.move = move;
        this.movingPiece = movingPiece;
        this.targetPiece = targetPiece;
        this.board = board;
    }

    public Move move() {
        return move;
    }

    public Piece movingPiece() {
        return movingPiece;
    }

    public Piece targetPiece() {
        return targetPiece;
    }

    public Board board() {
        return board;
    }

    public PieceColor activeColor() {
        return movingPiece.color();
    }
}

