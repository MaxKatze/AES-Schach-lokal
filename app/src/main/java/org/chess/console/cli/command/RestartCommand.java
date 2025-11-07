package org.chess.console.cli.command;

public final class RestartCommand implements CliCommand {
    @Override
    public CommandType type() {
        return CommandType.RESTART;
    }
}

