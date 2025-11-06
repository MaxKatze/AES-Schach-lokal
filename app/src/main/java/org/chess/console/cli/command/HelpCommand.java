package org.chess.console.cli.command;

public final class HelpCommand implements CliCommand {
    @Override
    public CommandType type() {
        return CommandType.HELP;
    }
}

