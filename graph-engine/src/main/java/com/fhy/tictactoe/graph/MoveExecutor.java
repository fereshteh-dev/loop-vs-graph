package com.fhy.tictactoe.graph;

import com.fhy.tictactoe.core.*;
import com.fhy.tictactoe.core.console.ConsoleView;
import lombok.extern.slf4j.Slf4j;

import static com.fhy.tictactoe.core.console.Ansi.*;

@Slf4j
final class MoveExecutor {
    private final GameRules rules;
    private final ConsoleView view;

    MoveExecutor(GameRules rules, ConsoleView view) {
        this.rules = rules;
        this.view = view;
    }

    Cell execute(GraphState state) {
        var selected = state.decision().cell();
        if (rules.isValidMove(state.board(), selected)) {
            view.node("Executor", GREEN, "Validated and committed %s".formatted(selected.display()));
            return selected;
        }
        var safe = state.board().availableCells().getFirst();
        log.warn("Decision agent proposed illegal move {}; substituting {}", selected, safe);
        view.node("Guardrail", RED, "Decision invalid; safely substituted %s".formatted(safe.display()));
        return safe;
    }
}
