package org.chess.console.cli.render;

import org.chess.console.cli.io.ConsoleOutputWriter;
import org.chess.console.domain.board.Position;
import org.chess.console.domain.game.Game;
import org.chess.console.domain.piece.Piece;
import org.chess.console.domain.piece.PieceColor;

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
        output.println("Am Zug: " + game.activePlayer());
        output.newline();
    }

    private String pieceSymbol(Piece piece) {
        if (piece == null) {
            return "   ";
        }
        String symbol = piece.type().getSymbol();
        return piece.color() == PieceColor.WHITE ? " " + symbol + " " : "*" + symbol + "*";
    }
}

