package org.chess.console.cli.render;

import org.chess.console.cli.io.ConsoleOutputWriter;
import org.chess.console.domain.board.Position;
import org.chess.console.domain.game.Game;
import org.chess.console.domain.game.GameStatus;
import org.chess.console.domain.piece.Piece;

/**
 * Renders the board using ASCII characters.
 */
public class AsciiBoardRenderer implements BoardRenderer {

    private final ConsoleOutputWriter output;

    public AsciiBoardRenderer(ConsoleOutputWriter output) {
        this.output = output;
    }

    @Override
    public void render(Game game) {
        output.println("    a   b   c   d   e   f   g   h");
        output.println("  +---+---+---+---+---+---+---+---+");
        for (int rank = 7; rank >= 0; rank--) {
            StringBuilder row = new StringBuilder();
            row.append(rank + 1).append(" |");
            for (int file = 0; file < 8; file++) {
                Piece piece = game.board().getPiece(Position.of(file, rank)).orElse(null);
                row.append(pieceSymbol(piece)).append("|");
            }
            row.append(" ").append(rank + 1);
            output.println(row.toString());
            output.println("  +---+---+---+---+---+---+---+---+");
        }
        output.println("    a   b   c   d   e   f   g   h");
        if (game.isOver()) {
            output.println("Status: " + formatGameStatus(game.status()));
        } else {
            output.println("Am Zug: " + game.activePlayer());
        }
        output.newline();
    }

    private String pieceSymbol(Piece piece) {
        if (piece == null) {
            return "   ";
        }
        String symbol = piece.type().getSymbol(piece.color());
        return " " + symbol + " ";
    }

    private String formatGameStatus(GameStatus status) {
        return switch (status) {
            case CHECKMATE -> "Schachmatt - Spiel beendet";
            case RESIGNED -> "Aufgegeben - Spiel beendet";
            case STALEMATE -> "Patt - Spiel beendet";
            case DRAWN -> "Remis - Spiel beendet";
            case CHECK -> "Schach";
            case IN_PROGRESS -> "In Bearbeitung";
            case READY -> "Bereit";
        };
    }
}

