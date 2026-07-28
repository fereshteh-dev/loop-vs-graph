package com.fhy.tictactoe.graph;

import com.fhy.tictactoe.core.Board;
import com.fhy.tictactoe.core.Cell;
import com.fhy.tictactoe.core.Player;
import com.fhy.tictactoe.core.console.ConsoleView;

import java.util.List;


final class GameGraph {
    private final List<GraphNode> nodes;
    private final MoveExecutor executor;
    private final ConsoleView view;

    GameGraph(GraphNode planner, GraphNode attack, GraphNode defense,
              GraphNode evaluator, GraphNode decision, MoveExecutor executor, ConsoleView view) {
        this.nodes = List.of(planner, attack, defense, evaluator, decision);
        this.executor = executor;
        this.view = view;
    }

    Cell execute(Board board, Player player) {
        var state = GraphState.start(board, player);
        for (int index = 0; index < nodes.size(); index++) {
            state = nodes.get(index).process(state);
            if (index < nodes.size() - 1) {
                view.edge();
            }
        }
        view.edge();
        return executor.execute(state);
    }
}
