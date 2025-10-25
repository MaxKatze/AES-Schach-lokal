package org.chess.console.domain.move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Aggregate component storing the list of moves that occurred in a game.
 */
public class MoveHistory {
    private final List<MoveRecord> records = new ArrayList<>();

    public void append(MoveRecord record) {
        records.add(record);
    }

    public List<MoveRecord> records() {
        return Collections.unmodifiableList(records);
    }

    public int size() {
        return records.size();
    }
}

