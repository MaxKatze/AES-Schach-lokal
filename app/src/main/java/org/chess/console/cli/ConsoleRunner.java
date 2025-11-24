package org.chess.console.cli;

import org.chess.console.application.GameService;
import org.chess.console.application.HintService;
import org.chess.console.application.MoveEvaluation;
import org.chess.console.cli.command.*;
import org.chess.console.cli.io.ConsoleInputReader;
import org.chess.console.cli.io.ConsoleOutputWriter;
import org.chess.console.cli.render.BoardRenderer;
import org.chess.console.domain.board.Position;
import org.chess.console.domain.game.Game;
import org.chess.console.domain.move.Move;
import org.chess.console.domain.piece.PieceType;

import java.util.Locale;

/**
 * Coordinates the console UI lifecycle.
 */
public class ConsoleRunner {

    private final ConsoleInputReader input;
    private final ConsoleOutputWriter output;
    private final BoardRenderer renderer;
    private final GameService service;
    private final CommandParser parser;
    private final HintService hintService;
    private Game currentGame;

    public ConsoleRunner(ConsoleInputReader input,
                         ConsoleOutputWriter output,
                         BoardRenderer renderer,
                         GameService service,
                         CommandParser parser,
                         HintService hintService) {
        this.input = input;
        this.output = output;
        this.renderer = renderer;
        this.service = service;
        this.parser = parser;
        this.hintService = hintService;
    }

    public void run() {
        output.println("Willkommen zu ConsoleChess!");
        bootstrapGame();
        renderer.render(currentGame);
        boolean running = true;
        while (running) {
            String line = input.readLine("> ");
            CliCommand command = parser.parse(line);
            switch (command.type()) {
                case MOVE -> handleMove((MoveCommand) command);
                case HELP -> printHelp();
                case HISTORY -> printHistory();
                case RESIGN -> handleResign((ResignCommand) command);
                case HINT -> handleHint();
                case RESTART -> {
                    bootstrapGame();
                    renderer.render(currentGame);
                }
                case EXIT -> running = false;
                case INVALID -> output.println(((InvalidCommand) command).reason());
                default -> output.println("Befehl nicht implementiert");
            }
        }
        output.println("Auf Wiedersehen!");
    }

    private void bootstrapGame() {
        String white = input.readLine("Name Weiß: ");
        String black = input.readLine("Name Schwarz: ");
        currentGame = service.startNewGame(defaultName(white, "Weiß"), defaultName(black, "Schwarz"));
    }

    private String defaultName(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value;
    }

    private void handleMove(MoveCommand command) {
        try {
            Move move = new Move(
                    Position.fromNotation(command.from()),
                    Position.fromNotation(command.to()),
                    command.promotion().map(this::mapPromotion)
            );
            MoveEvaluation evaluation = service.playMove(move);
            if (!evaluation.success()) {
                output.println("Fehler: " + evaluation.message());
                return;
            }
            // Refresh currentGame to get updated state
            currentGame = service.activeGame().orElseThrow();
            output.println("Zug erfolgreich: " + evaluation.outcome().notation());
            renderer.render(currentGame);
            if (evaluation.outcome().checkmate()) {
                output.println("Schachmatt! " + evaluation.outcome().piece().color() + " gewinnt.");
                output.println("Das Spiel ist beendet. Verwenden Sie 'restart' für eine neue Partie.");
            } else if (evaluation.outcome().check()) {
                output.println("Schach!");
            }
        } catch (IllegalArgumentException ex) {
            output.println("Eingabe ungültig: " + ex.getMessage());
        }
    }

    private PieceType mapPromotion(String token) {
        return switch (token.toUpperCase(Locale.ROOT)) {
            case "Q" -> PieceType.QUEEN;
            case "R" -> PieceType.ROOK;
            case "B" -> PieceType.BISHOP;
            case "N" -> PieceType.KNIGHT;
            default -> throw new IllegalArgumentException("Unbekannte Umwandlung: " + token);
        };
    }

    private void handleResign(ResignCommand command) {
        try {
            service.resign(command.color());
            // Refresh currentGame to get updated state
            currentGame = service.activeGame().orElseThrow();
            output.println("Spieler " + command.color() + " hat aufgegeben.");
            renderer.render(currentGame);
        } catch (IllegalStateException ex) {
            output.println("Fehler: " + ex.getMessage());
        }
    }

    private void printHelp() {
        output.printLines(java.util.List.of(
                "Befehle:",
                "  move e2 e4        Führt einen Zug aus",
                "  e2 e4             Alternative Schreibweise",
                "  history           Zeigt die Zughistorie",
                "  hint              Zeigt einen zufälligen legalen Zug",
                "  resign <farbe>    Gibt auf",
                "  restart           Startet neue Partie",
                "  exit              Beendet das Programm"
        ));
    }

    private void handleHint() {
        if (currentGame.isOver()) {
            output.println("Das Spiel ist beendet. Keine Züge mehr möglich.");
            return;
        }
        hintService.suggestMove(currentGame)
                .ifPresentOrElse(
                        move -> output.println("Vorschlag: " + move),
                        () -> output.println("Keine legalen Züge verfügbar.")
                );
    }

    private void printHistory() {
        output.println("Historie:");
        currentGame.history().records().forEach(record -> output.println(record.notation()));
    }
}

