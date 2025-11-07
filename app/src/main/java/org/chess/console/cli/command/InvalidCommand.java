package org.chess.console.cli.command;

public record InvalidCommand(String reason) implements CliCommand {
    @Override
    public CommandType type() {
        return CommandType.INVALID;
    }
}

