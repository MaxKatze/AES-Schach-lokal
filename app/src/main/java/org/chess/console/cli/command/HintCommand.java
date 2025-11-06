package org.chess.console.cli.command;

public final class HintCommand implements CliCommand {
    @Override
    public CommandType type() {
        return CommandType.HINT;
    }
}

