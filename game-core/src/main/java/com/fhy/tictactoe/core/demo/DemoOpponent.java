package com.fhy.tictactoe.core.demo;

import com.fhy.tictactoe.core.Board;
import com.fhy.tictactoe.core.Cell;

import java.util.List;

/**
 * A fixed opponent script makes the two orchestration recordings directly comparable.
 */
public final class DemoOpponent {
    private static final List<Cell> PREFERRED = List.of(
            new Cell(0, 0), new Cell(2, 2), new Cell(0, 2), new Cell(2, 0), new Cell(1, 1));

    public Cell choose(Board board) {
        return PREFERRED.stream().filter(board::isEmpty).findFirst()
                .orElseGet(() -> board.availableCells().getFirst());
    }
}
