package org.chess.console.domain.board;

import org.chess.console.domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Chess board aggregate component responsible for managing squares and movement.
 */
public class Board {

    private final Square[][] squares;

    public Board() {
        this.squares = new Square[8][8];
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                squares[file][rank] = new Square(Position.of(file, rank));
            }
        }
    }

    public Square getSquare(Position position) {
        return squares[position.file()][position.rank()];
    }

    public Optional<Piece> getPiece(Position position) {
        return getSquare(position).getPiece();
    }

    public void placePiece(Position position, Piece piece) {
        getSquare(position).place(piece);
    }

    public void move(Position from, Position to) {
        move(from, to, null);
    }

    public void move(Position from, Position to, Piece replacement) {
        Square source = getSquare(from);
        Square target = getSquare(to);
        Piece moving = source.removePiece();
        if (moving == null) {
            throw new IllegalStateException("No piece on " + from);
        }
        Piece pieceToPlace = replacement != null ? replacement : moving.moved();
        target.place(pieceToPlace);
    }

    public List<Piece> pieces() {
        List<Piece> all = new ArrayList<>();
        for (Square[] column : squares) {
            for (Square square : column) {
                square.getPiece().ifPresent(all::add);
            }
        }
        return all;
    }

    public Board copy() {
        Board board = new Board();
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                Position pos = Position.of(file, rank);
                getPiece(pos).ifPresent(piece -> board.placePiece(pos, piece.copy()));
            }
        }
        return board;
    }
}

