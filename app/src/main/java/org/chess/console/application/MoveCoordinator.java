package org.chess.console.application;

import org.chess.console.domain.board.Board;
import org.chess.console.domain.game.Game;
import org.chess.console.domain.move.Move;
import org.chess.console.domain.move.MoveRecord;
import org.chess.console.domain.piece.Piece;
import org.chess.console.domain.piece.PieceColor;
import org.chess.console.domain.piece.PieceFactory;
import org.chess.console.domain.piece.PieceType;
import org.chess.console.domain.rules.CheckInspector;
import org.chess.console.domain.rules.LegalMoveExplorer;
import org.chess.console.domain.rules.RuleBook;

import java.time.Instant;

/**
 * Coordinates validation and execution of moves, acting as the main application service boundary.
 */
public class MoveCoordinator {

    private final RuleBook ruleBook;
    private final CheckInspector checkInspector;
    private final PieceFactory pieceFactory = new PieceFactory();
    private final LegalMoveExplorer moveExplorer;

    public MoveCoordinator(RuleBook ruleBook) {
        this.ruleBook = ruleBook;
        this.checkInspector = new CheckInspector(ruleBook);
        this.moveExplorer = new LegalMoveExplorer(ruleBook, checkInspector);
    }

    public MoveEvaluation execute(Game game, Move move) {
        // Guard clause: prevent moves after game has ended
        if (game.isOver()) {
            return MoveEvaluation.failure("Das Spiel ist beendet. Verwenden Sie 'restart' für eine neue Partie.");
        }

        var board = game.board();
        var movingPiece = board.getPiece(move.from()).orElse(null);
        if (movingPiece == null) {
            return MoveEvaluation.failure("Auf dem Startfeld steht keine Figur");
        }
        if (movingPiece.color() != game.activeColor()) {
            return MoveEvaluation.failure("Spieler " + game.activePlayer().name() + " ist am Zug");
        }
        if (move.from().equals(move.to())) {
            return MoveEvaluation.failure("Start- und Zielfeld müssen verschieden sein");
        }

        Piece targetPiece = board.getPiece(move.to()).orElse(null);
        if (targetPiece != null && targetPiece.color() == movingPiece.color()) {
            return MoveEvaluation.failure("Eigene Figuren können nicht überschrieben werden");
        }

        var ruleResult = ruleBook.validate(board, move, movingPiece, targetPiece);
        if (!ruleResult.legal()) {
            return MoveEvaluation.failure(ruleResult.message());
        }

        Piece replacement = determineReplacement(movingPiece, move);
        Board sandbox = board.copy();
        sandbox.move(move.from(), move.to(), replacement);
        if (checkInspector.isKingInCheck(sandbox, movingPiece.color())) {
            return MoveEvaluation.failure("Zug lässt eigenen König im Schach");
        }

        board.move(move.from(), move.to(), replacement);
        boolean capture = targetPiece != null;
        boolean check = checkInspector.isKingInCheck(board, movingPiece.color().opposite());
        boolean checkmate = check && !moveExplorer.hasLegalMoves(game, movingPiece.color().opposite());

        MoveOutcome outcome = new MoveOutcome(movingPiece, capture, check, checkmate, move.toString());
        recordHistory(game, move, movingPiece, capture, check, checkmate);

        if (checkmate) {
            game.flagStatus(org.chess.console.domain.game.GameStatus.CHECKMATE);
        } else if (check) {
            game.flagStatus(org.chess.console.domain.game.GameStatus.CHECK);
        } else {
            game.flagStatus(org.chess.console.domain.game.GameStatus.IN_PROGRESS);
        }

        return MoveEvaluation.success(outcome);
    }

    private Piece determineReplacement(Piece movingPiece, Move move) {
        if (movingPiece.type() != PieceType.PAWN) {
            return null;
        }
        int targetRank = move.to().rank();
        if (movingPiece.color() == PieceColor.WHITE && targetRank == 7
                || movingPiece.color() == PieceColor.BLACK && targetRank == 0) {
            PieceType promotionType = move.promotion().orElse(PieceType.QUEEN);
            return pieceFactory.create(promotionType, movingPiece.color());
        }
        return null;
    }

    private void recordHistory(Game game, Move move, Piece piece, boolean capture, boolean check, boolean checkmate) {
        MoveRecord record = new MoveRecord(
                game.turnNumber(),
                piece.type(),
                piece.color(),
                move,
                capture,
                check,
                checkmate,
                Instant.now()
        );
        game.recordMove(record);
    }
}

