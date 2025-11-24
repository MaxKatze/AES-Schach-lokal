package org.chess.console.domain.rules;

import org.chess.console.domain.exceptions.DomainErrorCode;

import java.util.Optional;

/**
 * Outcome of evaluating a rule.
 * Uses domain error codes instead of user-facing messages to maintain domain purity.
 */
public record MoveValidationResult(boolean legal, Optional<DomainErrorCode> errorCode, Object[] errorArgs) {
    public static final MoveValidationResult SUCCESS = new MoveValidationResult(true, Optional.empty(), new Object[0]);

    /**
     * Creates an illegal result with a domain error code.
     * This maintains domain purity - no user messages in domain layer.
     */
    public static MoveValidationResult illegal(DomainErrorCode errorCode, Object... args) {
        return new MoveValidationResult(false, Optional.of(errorCode), args);
    }

    /**
     * Legacy method for backward compatibility during migration.
     * @deprecated Use illegal(DomainErrorCode, Object...) instead
     */
    @Deprecated
    public static MoveValidationResult illegal(String message) {
        // Map to a generic error code - this is temporary during migration
        return illegal(DomainErrorCode.NO_RULES_FOR_PIECE_TYPE, message);
    }

    /**
     * Legacy method for backward compatibility.
     * @deprecated Use errorCode() instead
     */
    @Deprecated
    public String message() {
        return errorCode.map(code -> code.name()).orElse("");
    }
}

