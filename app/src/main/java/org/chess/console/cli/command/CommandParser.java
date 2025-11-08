package org.chess.console.cli.command;

import org.chess.console.domain.piece.PieceColor;

import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Parses textual input into command objects.
 */
public class CommandParser {

    private static final Pattern COORDINATE = Pattern.compile("^[a-h][1-8]$");

    public CliCommand parse(String line) {
        if (line == null || line.isBlank()) {
            return new InvalidCommand("Keine Eingabe");
        }
        String[] tokens = line.trim().split("\\s+");
        String keyword = tokens[0].toLowerCase(Locale.ROOT);
        return switch (keyword) {
            case "help", "?" -> new HelpCommand();
            case "history" -> new HistoryCommand();
            case "hint" -> new HintCommand();
            case "restart" -> new RestartCommand();
            case "exit", "quit" -> new ExitCommand();
            case "resign" -> parseResign(tokens);
            case "move" -> parseMoveTokens(tokens, true);
            default -> {
                if (tokens.length >= 2 && isCoordinate(keyword)) {
                    yield parseMoveTokens(tokens, false);
                }
                yield new InvalidCommand("Unbekannter Befehl: " + keyword);
            }
        };
    }

    private CliCommand parseResign(String[] tokens) {
        if (tokens.length == 1) {
            return new ResignCommand(PieceColor.WHITE); // default
        }
        return switch (tokens[1].toLowerCase(Locale.ROOT)) {
            case "white", "w" -> new ResignCommand(PieceColor.WHITE);
            case "black", "b" -> new ResignCommand(PieceColor.BLACK);
            default -> new InvalidCommand("Farbe für Aufgabe unbekannt");
        };
    }

    private CliCommand parseMoveTokens(String[] tokens, boolean hasKeyword) {
        int offset = hasKeyword ? 1 : 0;
        if (tokens.length - offset < 2) {
            return new InvalidCommand("Zug benötigt Start- und Zielfeld");
        }
        String from = tokens[offset];
        String to = tokens[offset + 1];
        if (!isCoordinate(from) || !isCoordinate(to)) {
            return new InvalidCommand("Koordinaten sind ungültig");
        }
        Optional<String> promotion = Optional.empty();
        if (tokens.length - offset >= 3) {
            promotion = Optional.of(tokens[offset + 2].toUpperCase(Locale.ROOT));
        }
        return new MoveCommand(from, to, promotion);
    }

    private boolean isCoordinate(String token) {
        return COORDINATE.matcher(token.toLowerCase(Locale.ROOT)).matches();
    }
}

