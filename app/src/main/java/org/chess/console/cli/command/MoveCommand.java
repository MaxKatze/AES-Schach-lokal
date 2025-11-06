package org.chess.console.cli.command;

import java.util.Optional;

public record MoveCommand(String from, String to, Optional<String> promotion) implements CliCommand {
    @Override
    public CommandType type() {
        return CommandType.MOVE;
    }
}

