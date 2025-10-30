package org.chess.console.domain.rules;

import org.chess.console.domain.board.Board;
import org.chess.console.domain.board.Position;
import org.chess.console.domain.game.Game;
import org.chess.console.domain.move.Move;
import org.chess.console.domain.piece.Piece;
import org.chess.console.domain.piece.PieceColor;
import org.chess.console.domain.piece.PieceFactory;
import org.chess.console.domain.piece.PieceType;

import java.util.ArrayList;
import java.util.List;

/**
 * Explores the game tree to find legal moves for a player.
 */
public class LegalMoveExplorer {

    private final RuleBook ruleBook;
    private final CheckInspector checkInspector;
    private final PieceFactory pieceFactory = new PieceFactory();

    public LegalMoveExplorer(RuleBook ruleBook, CheckInspector checkInspector) {
        this.ruleBook = ruleBook;
        this.checkInspector = checkInspector;
    }

    public List<Move> legalMoves(Game game, PieceColor color) {
        List<Move> moves = new ArrayList<>();
        Board board = game.board();
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                Position from = Position.of(file, rank);
                var pieceOpt = board.getPiece(from);
                if (pieceOpt.isEmpty() || pieceOpt.get().color() != color) {
                    continue;
                }
                Piece piece = pieceOpt.get();
                moves.addAll(generateMovesFrom(game, piece, from));
            }
        }
        return moves;
    }

    public boolean hasLegalMoves(Game game, PieceColor color) {
        return !legalMoves(game, color).isEmpty();
    }

    private List<Move> generateMovesFrom(Game game, Piece piece, Position from) {
        List<Move> legal = new ArrayList<>();
        Board board = game.board();
        for (int targetFile = 0; targetFile < 8; targetFile++) {
            for (int targetRank = 0; targetRank < 8; targetRank++) {
                Position to = Position.of(targetFile, targetRank);
                if (from.equals(to)) {
                    continue;
                }
                var targetPiece = board.getPiece(to).orElse(null);
                if (targetPiece != null && targetPiece.color() == piece.color()) {
                    continue;
                }
                Move candidate = new Move(from, to);
                var validation = ruleBook.validate(board, candidate, piece, targetPiece);
                if (!validation.legal()) {
                    continue;
                }
                Board sandbox = board.copy();
                sandbox.move(from, to, determineReplacement(piece, candidate));
                if (!checkInspector.isKingInCheck(sandbox, piece.color())) {
                    legal.add(candidate);
                }
            }
        }
        return legal;
    }

    private Piece determineReplacement(Piece movingPiece, Move move) {
        if (movingPiece.type() != PieceType.PAWN) {
            return null;
        }
        int targetRank = move.to().rank();
        if ((movingPiece.color() == PieceColor.WHITE && targetRank == 7)
                || (movingPiece.color() == PieceColor.BLACK && targetRank == 0)) {
            return pieceFactory.create(PieceType.QUEEN, movingPiece.color());
        }
        return null;
    }
}

