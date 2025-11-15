package org.chess.console.cli.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {

    private final CommandParser parser = new CommandParser();

    @Test
    void parsesMoveCommandWithKeyword() {
        CliCommand command = parser.parse("move e2 e4");

        assertEquals(CommandType.MOVE, command.type());
        MoveCommand move = (MoveCommand) command;
        assertEquals("e2", move.from());
        assertEquals("e4", move.to());
    }

    @Test
    void parsesMoveCommandWithoutKeyword() {
        CliCommand command = parser.parse("b1 c3");

        assertEquals(CommandType.MOVE, command.type());
    }

    @Test
    void parsesUtilityCommands() {
        assertEquals(CommandType.HINT, parser.parse("hint").type());
        assertEquals(CommandType.HISTORY, parser.parse("history").type());
    }

    @Test
    void reportsInvalidCommand() {
        CliCommand command = parser.parse("foobar");
        assertEquals(CommandType.INVALID, command.type());
        assertTrue(((InvalidCommand) command).reason().contains("Unbekannter"));
    }
}

