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
        MoveCommand move = (MoveCommand) command;
        assertEquals("b1", move.from());
        assertEquals("c3", move.to());
    }

    @Test
    void parsesMoveCommandWithPromotion() {
        CliCommand command = parser.parse("move e7 e8 Q");

        assertEquals(CommandType.MOVE, command.type());
        MoveCommand move = (MoveCommand) command;
        assertEquals("e7", move.from());
        assertEquals("e8", move.to());
        assertTrue(move.promotion().isPresent());
        assertEquals("Q", move.promotion().get());
    }

    @Test
    void parsesMoveCommandWithPromotionWithoutKeyword() {
        CliCommand command = parser.parse("e7 e8 Q");

        assertEquals(CommandType.MOVE, command.type());
        MoveCommand move = (MoveCommand) command;
        assertTrue(move.promotion().isPresent());
    }

    @Test
    void parsesUtilityCommands() {
        assertEquals(CommandType.HINT, parser.parse("hint").type());
        assertEquals(CommandType.HISTORY, parser.parse("history").type());
        assertEquals(CommandType.HELP, parser.parse("help").type());
        assertEquals(CommandType.HELP, parser.parse("?").type());
        assertEquals(CommandType.RESTART, parser.parse("restart").type());
        assertEquals(CommandType.EXIT, parser.parse("exit").type());
        assertEquals(CommandType.EXIT, parser.parse("quit").type());
    }

    @Test
    void parsesResignCommand() {
        CliCommand command = parser.parse("resign");
        assertEquals(CommandType.RESIGN, command.type());
        ResignCommand resign = (ResignCommand) command;
        assertEquals(org.chess.console.domain.piece.PieceColor.WHITE, resign.color());
    }

    @Test
    void parsesResignCommandWithColor() {
        CliCommand commandWhite = parser.parse("resign white");
        assertEquals(CommandType.RESIGN, commandWhite.type());
        assertEquals(org.chess.console.domain.piece.PieceColor.WHITE, 
                ((ResignCommand) commandWhite).color());

        CliCommand commandBlack = parser.parse("resign black");
        assertEquals(org.chess.console.domain.piece.PieceColor.BLACK, 
                ((ResignCommand) commandBlack).color());

        CliCommand commandW = parser.parse("resign w");
        assertEquals(org.chess.console.domain.piece.PieceColor.WHITE, 
                ((ResignCommand) commandW).color());

        CliCommand commandB = parser.parse("resign b");
        assertEquals(org.chess.console.domain.piece.PieceColor.BLACK, 
                ((ResignCommand) commandB).color());
    }

    @Test
    void reportsInvalidCommand() {
        CliCommand command = parser.parse("foobar");
        assertEquals(CommandType.INVALID, command.type());
        assertTrue(((InvalidCommand) command).reason().contains("Unbekannter"));
    }

    @Test
    void reportsInvalidForNullInput() {
        CliCommand command = parser.parse(null);
        assertEquals(CommandType.INVALID, command.type());
        assertTrue(((InvalidCommand) command).reason().contains("Eingabe"));
    }

    @Test
    void reportsInvalidForBlankInput() {
        CliCommand command = parser.parse("   ");
        assertEquals(CommandType.INVALID, command.type());
    }

    @Test
    void reportsInvalidForIncompleteMove() {
        CliCommand command = parser.parse("move e2");
        assertEquals(CommandType.INVALID, command.type());
        assertTrue(((InvalidCommand) command).reason().contains("Start- und Zielfeld"));
    }

    @Test
    void reportsInvalidForInvalidCoordinates() {
        CliCommand command1 = parser.parse("move i1 e4");
        assertEquals(CommandType.INVALID, command1.type());

        CliCommand command2 = parser.parse("move e2 e9");
        assertEquals(CommandType.INVALID, command2.type());

        CliCommand command3 = parser.parse("move e2 invalid");
        assertEquals(CommandType.INVALID, command3.type());
    }

    @Test
    void reportsInvalidForUnknownResignColor() {
        CliCommand command = parser.parse("resign red");
        assertEquals(CommandType.INVALID, command.type());
        assertTrue(((InvalidCommand) command).reason().contains("Farbe"));
    }

    @Test
    void handlesCaseInsensitiveCommands() {
        assertEquals(CommandType.HELP, parser.parse("HELP").type());
        assertEquals(CommandType.HINT, parser.parse("Hint").type());
        assertEquals(CommandType.MOVE, parser.parse("MOVE e2 e4").type());
        assertEquals(CommandType.RESIGN, parser.parse("RESIGN WHITE").type());
    }

    @Test
    void handlesWhitespaceInCommands() {
        assertEquals(CommandType.MOVE, parser.parse("  move  e2  e4  ").type());
        assertEquals(CommandType.MOVE, parser.parse("e2    e4").type());
    }
}

