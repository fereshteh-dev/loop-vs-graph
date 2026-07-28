package com.fhy.tictactoe.loop;

import com.fhy.tictactoe.core.*;
import com.fhy.tictactoe.core.console.ConsoleView;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashSet;

import static com.fhy.tictactoe.core.console.Ansi.*;

@Slf4j
final class LoopOrchestrator {
    private static final int MAX_ATTEMPTS = 3;
    private final MoveReasoner reasoner;
    private final GameRules rules;
    private final ConsoleView view;

    LoopOrchestrator(MoveReasoner reasoner, GameRules rules, ConsoleView view) {
        this.reasoner = reasoner;
        this.rules = rules;
        this.view = view;
    }

    Cell choose(Board board, Player player) {
        var rejected = new LinkedHashSet<Cell>();
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            view.node("LOOP", CYAN, "Reasoning iteration %d/%d".formatted(attempt, MAX_ATTEMPTS));
            view.node("Planner", BLUE, "Thinking across %d open cells...".formatted(board.availableCells().size()));
            try {
                var candidate = reasoner.propose(board, player, rejected);
                var cell = candidate.cell();
                view.node("Candidate", MAGENTA, "%s  %s".formatted(cell.display(), candidate.rationale()));
                if (!rules.isValidMove(board, cell)) {
                    rejected.add(cell);
                    view.node("Validator", RED, "Rejected — occupied or out of play");
                    continue;
                }
                view.node("Validator", GREEN, "Passed");
                view.node("Evaluator", YELLOW, "Position score %.1f / 10".formatted(candidate.score()));
                if (candidate.score() < 3.5 && attempt < MAX_ATTEMPTS) {
                    rejected.add(cell);
                    view.node("LOOP", RED, "Below quality threshold — reasoning again");
                    continue;
                }
                view.node("Executor", GREEN, "Playing move %s".formatted(cell.display()));
                return cell;
            } catch (RuntimeException exception) {
                log.warn("Reasoning iteration {} produced an unusable response: {}",
                        attempt, exception.getMessage());
                view.node("Recovery", RED, "Malformed proposal — requesting a fresh candidate");
            }
        }
        var safe = board.availableCells().getFirst();
        view.node("Guardrail", YELLOW, "Attempt budget exhausted; selecting legal move %s".formatted(safe.display()));
        return safe;
    }
}
