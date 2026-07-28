package com.fhy.tictactoe.loop;

import com.fhy.tictactoe.core.Board;
import com.fhy.tictactoe.core.Cell;
import com.fhy.tictactoe.core.Player;

import java.util.Set;

final class LocalLoopReasoner implements MoveReasoner {
    private final StrategicMoveScorer scorer;

    LocalLoopReasoner(StrategicMoveScorer scorer) {
        this.scorer = scorer;
    }

    @Override
    public MoveCandidate propose(Board board, Player player, Set<Cell> rejected) {
        var ranked = scorer.rank(board, player);
        var selected = ranked.stream().filter(move -> !rejected.contains(move.cell())).findFirst().orElseThrow();
        var normalized = Math.clamp(5.0 + selected.score() / 20.0, 0.0, 10.0);
        return new MoveCandidate(selected.cell().row() + 1, selected.cell().column() + 1,
                normalized, "Minimax search across %d legal continuations".formatted(ranked.size()));
    }
}
