package org.chess.console.cli.io;

import java.util.Scanner;

/**
 * Reads user input from standard input.
 */
public class ConsoleInputReader {
    private final Scanner scanner = new Scanner(System.in);

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}

