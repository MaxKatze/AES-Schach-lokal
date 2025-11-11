package org.chess.console;

import org.chess.console.cli.ConsoleRunner;
import org.chess.console.cli.io.ConsoleInputReader;
import org.chess.console.cli.io.ConsoleOutputWriter;
import org.chess.console.cli.render.AsciiBoardRenderer;
import org.chess.console.cli.command.CommandParser;
import org.chess.console.application.GameService;
import org.chess.console.application.HintService;
import org.chess.console.application.MoveCoordinator;
import org.chess.console.application.TurnManager;
import org.chess.console.domain.game.GameFactory;
import org.chess.console.domain.rules.RuleBook;
import org.chess.console.infrastructure.repository.InMemoryGameRepository;

/**
 * Application bootstrapper that wires together the console UI, application services, and domain layer.
 */
public final class App {

    private App() {
    }

    public static void main(String[] args) {
        var output = new ConsoleOutputWriter();
        var input = new ConsoleInputReader();
        var renderer = new AsciiBoardRenderer(output);
        var repository = new InMemoryGameRepository();
        var ruleBook = RuleBook.defaultRuleBook();
        var moveCoordinator = new MoveCoordinator(ruleBook);
        var hintService = new HintService(ruleBook);
        var turnManager = new TurnManager();
        var gameFactory = new GameFactory();
        var service = new GameService(repository, moveCoordinator, turnManager, gameFactory);
        var parser = new CommandParser();
        var runner = new ConsoleRunner(input, output, renderer, service, parser, hintService);
        runner.run();
    }
}
