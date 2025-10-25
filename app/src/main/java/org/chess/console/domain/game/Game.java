package org.chess.console.domain.game;

import org.chess.console.domain.board.Board;
import org.chess.console.domain.board.Position;
import org.chess.console.domain.move.MoveHistory;
import org.chess.console.domain.move.MoveRecord;
import org.chess.console.domain.piece.Piece;
import org.chess.console.domain.piece.PieceColor;
import org.chess.console.domain.player.Player;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Aggregate root for the chess domain model.
 */
public class Game {

    private final UUID id;
    private final Player white;
    private final Player black;
    private final MoveHistory history;
    private final Board board;
    private GameStatus status;
    private PieceColor activeColor;

    public Game(UUID id, Player white, Player black, Board board) {
        this.id = Objects.requireNonNull(id, "id");
        this.white = Objects.requireNonNull(white, "white");
        this.black = Objects.requireNonNull(black, "black");
        this.board = Objects.requireNonNull(board, "board");
        this.history = new MoveHistory();
        this.status = GameStatus.READY;
        this.activeColor = PieceColor.WHITE;
    }

    public UUID id() {
        return id;
    }

    public Player white() {
        return white;
    }

    public Player black() {
        return black;
    }

    public MoveHistory history() {
        return history;
    }

    public Board board() {
        return board;
    }

    public PieceColor activeColor() {
        return activeColor;
    }

    public GameStatus status() {
        return status;
    }

    public void start() {
        status = GameStatus.IN_PROGRESS;
        activeColor = PieceColor.WHITE;
    }

    public void toggleTurn() {
        activeColor = activeColor.opposite();
    }

    public void flagStatus(GameStatus newStatus) {
        this.status = newStatus;
    }

    public Optional<Piece> pieceAt(Position position) {
        return board.getPiece(position);
    }

    public void recordMove(MoveRecord record) {
        history.append(record);
    }

    public Player activePlayer() {
        return activeColor == PieceColor.WHITE ? white : black;
    }

    public int turnNumber() {
        return history.size() + 1;
    }

    public void resign(PieceColor color) {
        status = GameStatus.RESIGNED;
        activeColor = color.opposite();
    }
}

