package org.chess.console.domain.game;

import org.chess.console.domain.board.Board;
import org.chess.console.domain.board.Position;
import org.chess.console.domain.piece.*;
import org.chess.console.domain.player.Player;

import java.util.UUID;

/**
 * Factory responsible for instantiating new games with the standard chess layout.
 */
public class GameFactory {

    public Game create(String whitePlayer, String blackPlayer) {
        Board board = new Board();
        setupPieces(board);
        Player white = new Player(whitePlayer, PieceColor.WHITE);
        Player black = new Player(blackPlayer, PieceColor.BLACK);
        Game game = new Game(UUID.randomUUID(), white, black, board);
        game.start();
        return game;
    }

    private void setupPieces(Board board) {
        placeBackRank(board, 0, PieceColor.WHITE);
        placePawns(board, 1, PieceColor.WHITE);
        placePawns(board, 6, PieceColor.BLACK);
        placeBackRank(board, 7, PieceColor.BLACK);
    }

    private void placeBackRank(Board board, int rank, PieceColor color) {
        board.placePiece(Position.of(0, rank), new Rook(color));
        board.placePiece(Position.of(1, rank), new Knight(color));
        board.placePiece(Position.of(2, rank), new Bishop(color));
        board.placePiece(Position.of(3, rank), color == PieceColor.WHITE ? new Queen(color) : new Queen(color));
        board.placePiece(Position.of(4, rank), new King(color));
        board.placePiece(Position.of(5, rank), new Bishop(color));
        board.placePiece(Position.of(6, rank), new Knight(color));
        board.placePiece(Position.of(7, rank), new Rook(color));
    }

    private void placePawns(Board board, int rank, PieceColor color) {
        for (int file = 0; file < 8; file++) {
            board.placePiece(Position.of(file, rank), new Pawn(color));
        }
    }
}

