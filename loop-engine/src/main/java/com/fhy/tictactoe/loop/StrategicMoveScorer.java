package com.fhy.tictactoe.loop;

import com.fhy.tictactoe.core.*;

import java.util.Comparator;
import java.util.List;


final class StrategicMoveScorer {
    private final GameRules rules;

    StrategicMoveScorer(GameRules rules) {
        this.rules = rules;
    }

    List<ScoredCell> rank(Board board, Player player) {
        return board.availableCells().stream()
                .map(cell -> new ScoredCell(cell, score(board.place(cell, player), player.opponent(), player, 0)))
                .sorted(Comparator.comparingInt(ScoredCell::score).reversed()
                        .thenComparingInt(value -> preference(value.cell())))
                .toList();
    }

    private int score(Board board, Player turn, Player maximizing, int depth) {
        return switch (rules.status(board)) {
            case GameStatus.Won won -> won.winner() == maximizing ? 100 - depth : depth - 100;
            case GameStatus.Draw ignored -> 0;
            case GameStatus.InProgress ignored -> {
                var scores = board.availableCells().stream()
                        .mapToInt(cell -> score(board.place(cell, turn), turn.opponent(), maximizing, depth + 1));
                yield turn == maximizing ? scores.max().orElse(0) : scores.min().orElse(0);
            }
        };
    }

    private int preference(Cell cell) {
        if (cell.equals(Cell.at(1, 1))) return 0;
        if (cell.row() != 1 && cell.column() != 1) return 1;
        return 2;
    }

    record ScoredCell(Cell cell, int score) {}
}
