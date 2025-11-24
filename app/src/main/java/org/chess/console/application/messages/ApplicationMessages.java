package org.chess.console.application.messages;

import org.chess.console.domain.exceptions.DomainErrorCode;

/**
 * Application layer message constants.
 * These provide user-facing messages for domain error codes.
 * This layer can be extended for internationalization.
 */
public final class ApplicationMessages {

    private ApplicationMessages() {
        // Utility class
    }

    // Game state messages
    public static final String GAME_ALREADY_OVER = "Das Spiel ist bereits beendet.";
    public static final String GAME_OVER_USE_RESTART = "Das Spiel ist beendet. Verwenden Sie 'restart' für eine neue Partie.";

    // Move execution messages
    public static final String NO_PIECE_ON_START_SQUARE = "Auf dem Startfeld steht keine Figur";
    public static final String WRONG_PLAYER_TURN = "Spieler %s ist am Zug";
    public static final String SAME_START_AND_TARGET = "Start- und Zielfeld müssen verschieden sein";
    public static final String CANNOT_CAPTURE_OWN_PIECE = "Eigene Figuren können nicht überschrieben werden";
    public static final String MOVE_LEAVES_KING_IN_CHECK = "Zug lässt eigenen König im Schach";

    // Domain rule violation messages (mapped from domain error codes)
    public static final String PAWN_MUST_MOVE_FORWARD = "Der Bauer darf nur vorwärts ziehen";
    public static final String PAWN_FORWARD_MOVE_BLOCKED = "Vorwärtszüge sind nur auf freie Felder erlaubt";
    public static final String PAWN_DOUBLE_MOVE_BLOCKED = "Doppelzug blockiert";
    public static final String PAWN_CAPTURE_REQUIRES_OPPONENT = "Schlagzüge benötigen eine gegnerische Figur";
    public static final String PAWN_CANNOT_CAPTURE_OWN_PIECE = "Eigene Figuren können nicht geschlagen werden";
    public static final String PAWN_CANNOT_CAPTURE_WITHOUT_DIAGONAL = "Bauern dürfen nicht ohne Diagonale schlagen";
    public static final String KING_MOVES_ONE_SQUARE_ONLY = "Der König bewegt sich nur ein Feld";
    public static final String KNIGHT_MOVES_L_SHAPE_ONLY = "Springer bewegt sich in L-Form";
    public static final String INVALID_DIRECTION_FOR_PIECE = "Ungültige Richtung für %s";
    public static final String PATH_BLOCKED = "Der Weg ist blockiert";
    public static final String NO_RULES_FOR_PIECE_TYPE = "Keine Regeln für %s";

    /**
     * Maps domain error codes to user-facing messages.
     * This allows the application layer to translate domain concepts to messages.
     */
    public static String getMessage(DomainErrorCode errorCode, Object... args) {
        return switch (errorCode) {
            case GAME_ALREADY_OVER -> GAME_ALREADY_OVER;
            case NO_PIECE_ON_START_SQUARE -> NO_PIECE_ON_START_SQUARE;
            case WRONG_PLAYER_TURN -> String.format(WRONG_PLAYER_TURN, args);
            case SAME_START_AND_TARGET -> SAME_START_AND_TARGET;
            case CANNOT_CAPTURE_OWN_PIECE -> CANNOT_CAPTURE_OWN_PIECE;
            case MOVE_LEAVES_KING_IN_CHECK -> MOVE_LEAVES_KING_IN_CHECK;
            case PAWN_MUST_MOVE_FORWARD -> PAWN_MUST_MOVE_FORWARD;
            case PAWN_FORWARD_MOVE_BLOCKED -> PAWN_FORWARD_MOVE_BLOCKED;
            case PAWN_DOUBLE_MOVE_BLOCKED -> PAWN_DOUBLE_MOVE_BLOCKED;
            case PAWN_CAPTURE_REQUIRES_OPPONENT -> PAWN_CAPTURE_REQUIRES_OPPONENT;
            case PAWN_CANNOT_CAPTURE_OWN_PIECE -> PAWN_CANNOT_CAPTURE_OWN_PIECE;
            case PAWN_CANNOT_CAPTURE_WITHOUT_DIAGONAL -> PAWN_CANNOT_CAPTURE_WITHOUT_DIAGONAL;
            case KING_MOVES_ONE_SQUARE_ONLY -> KING_MOVES_ONE_SQUARE_ONLY;
            case KNIGHT_MOVES_L_SHAPE_ONLY -> KNIGHT_MOVES_L_SHAPE_ONLY;
            case INVALID_DIRECTION_FOR_PIECE -> String.format(INVALID_DIRECTION_FOR_PIECE, args);
            case PATH_BLOCKED -> PATH_BLOCKED;
            case NO_RULES_FOR_PIECE_TYPE -> String.format(NO_RULES_FOR_PIECE_TYPE, args);
        };
    }
}

