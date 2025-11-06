package org.chess.console.cli.command;

public final class HistoryCommand implements CliCommand {
    @Override
    public CommandType type() {
        return CommandType.HISTORY;
    }
}

