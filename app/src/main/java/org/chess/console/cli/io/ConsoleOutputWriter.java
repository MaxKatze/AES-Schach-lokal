package org.chess.console.cli.io;

import java.util.List;

/**
 * Wraps writing to the console to centralize formatting concerns.
 */
public class ConsoleOutputWriter {

    public void println(String message) {
        System.out.println(message);
    }

    public void printLines(List<String> lines) {
        lines.forEach(System.out::println);
    }

    public void newline() {
        System.out.println();
    }
}

