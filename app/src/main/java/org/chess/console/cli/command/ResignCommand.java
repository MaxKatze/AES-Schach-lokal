package org.chess.console.cli.command;

import org.chess.console.domain.piece.PieceColor;

public record ResignCommand(PieceColor color) implements CliCommand {
    @Override
    public CommandType type() {
        return CommandType.RESIGN;
    }
}

