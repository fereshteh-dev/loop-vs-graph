package com.fhy.tictactoe.core;

import java.util.List;

public final class GameRules {
    private static final List<List<Cell>> WINNING_LINES = List.of(
            line(0, 0, 0, 1, 0, 2), line(1, 0, 1, 1, 1, 2), line(2, 0, 2, 1, 2, 2),
            line(0, 0, 1, 0, 2, 0), line(0, 1, 1, 1, 2, 1), line(0, 2, 1, 2, 2, 2),
            line(0, 0, 1, 1, 2, 2), line(0, 2, 1, 1, 2, 0)
    );

    public GameStatus status(Board board) {
        for (var line : WINNING_LINES) {
            var first = board.playerAt(line.getFirst());
            if (first.isPresent() && line.stream().allMatch(cell -> board.playerAt(cell).equals(first))) {
                return new GameStatus.Won(first.orElseThrow());
            }
        }
        return board.availableCells().isEmpty() ? new GameStatus.Draw() : new GameStatus.InProgress();
    }

    public boolean isValidMove(Board board, Cell cell) {
        return status(board) instanceof GameStatus.InProgress && board.isEmpty(cell);
    }

    public List<List<Cell>> winningLines() {
        return WINNING_LINES;
    }

    private static List<Cell> line(int... coordinates) {
        return List.of(new Cell(coordinates[0], coordinates[1]),
                new Cell(coordinates[2], coordinates[3]),
                new Cell(coordinates[4], coordinates[5]));
    }
}
