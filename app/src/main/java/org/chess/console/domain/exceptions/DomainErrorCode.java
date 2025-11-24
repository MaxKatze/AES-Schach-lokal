package org.chess.console.domain.exceptions;

/**
 * Domain error codes representing business rule violations.
 * These are pure domain concepts without user-facing messages.
 * The domain layer should only use these codes, not user messages.
 */
public enum DomainErrorCode {
    // Game state errors
    GAME_ALREADY_OVER,
    
    // Move validation errors
    NO_PIECE_ON_START_SQUARE,
    WRONG_PLAYER_TURN,
    SAME_START_AND_TARGET,
    CANNOT_CAPTURE_OWN_PIECE,
    MOVE_LEAVES_KING_IN_CHECK,
    
    // Piece movement rule errors
    PAWN_MUST_MOVE_FORWARD,
    PAWN_FORWARD_MOVE_BLOCKED,
    PAWN_DOUBLE_MOVE_BLOCKED,
    PAWN_CAPTURE_REQUIRES_OPPONENT,
    PAWN_CANNOT_CAPTURE_OWN_PIECE,
    PAWN_CANNOT_CAPTURE_WITHOUT_DIAGONAL,
    KING_MOVES_ONE_SQUARE_ONLY,
    KNIGHT_MOVES_L_SHAPE_ONLY,
    INVALID_DIRECTION_FOR_PIECE,
    PATH_BLOCKED,
    NO_RULES_FOR_PIECE_TYPE
}

