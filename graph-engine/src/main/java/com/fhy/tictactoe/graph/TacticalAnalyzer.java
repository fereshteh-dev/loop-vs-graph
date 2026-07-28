package com.fhy.tictactoe.graph;

import com.fhy.tictactoe.core.*;

import java.util.Comparator;

final class TacticalAnalyzer {
    private final GameRules rules;

    TacticalAnalyzer(GameRules rules) {
        this.rules = rules;
    }

    Proposal attack(Board board, Player player) {
        var win = immediate(board, player);
        if (win != null) return Proposal.of(win, 10.0, "Immediate winning line discovered");
        var fork = board.availableCells().stream()
                .max(Comparator.comparingInt(cell -> futureWins(board.place(cell, player), player)))
                .orElseThrow();
        var threats = futureWins(board.place(fork, player), player);
        return Proposal.of(fork, threats > 1 ? 9.0 : positional(fork),
                threats > 1 ? "Fork creates multiple winning paths" : "Strongest positional attack");
    }

    Proposal defend(Board board, Player player) {
        var threat = immediate(board, player.opponent());
        if (threat != null) return Proposal.of(threat, 10.0, "Immediate opponent threat must be blocked");
        var candidate = board.availableCells().stream()
                .max(Comparator.comparingDouble(this::positional)).orElseThrow();
        return Proposal.of(candidate, positional(candidate), "No immediate threat; preserves defensive coverage");
    }

    private Cell immediate(Board board, Player player) {
        return board.availableCells().stream()
                .filter(cell -> rules.status(board.place(cell, player)) instanceof GameStatus.Won)
                .findFirst().orElse(null);
    }

    private int futureWins(Board board, Player player) {
        return (int) board.availableCells().stream()
                .filter(cell -> rules.status(board.place(cell, player)) instanceof GameStatus.Won).count();
    }

    private double positional(Cell cell) {
        if (cell.equals(Cell.at(1, 1))) return 7.5;
        return cell.row() != 1 && cell.column() != 1 ? 6.5 : 5.0;
    }
}
