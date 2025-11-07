package org.chess.console.cli.command;

public final class ExitCommand implements CliCommand {
    @Override
    public CommandType type() {
        return CommandType.EXIT;
    }
}

