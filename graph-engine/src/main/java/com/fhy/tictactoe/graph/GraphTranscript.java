package com.fhy.tictactoe.graph;

import com.fhy.tictactoe.core.*;
import com.fhy.tictactoe.core.console.ConsoleView;
import com.fhy.tictactoe.core.demo.DemoOpponent;

import java.io.PrintStream;

import static com.fhy.tictactoe.core.console.Ansi.*;

/**
 * Produces a complete, deterministic graph-engine trace for comparison and recording.
 */
public final class GraphTranscript {
    private GraphTranscript() {}

    public static void writeTo(PrintStream output, boolean color) {
        var rules = new GameRules();
        var view = new ConsoleView(output, color);
        var intelligence = new LocalAgentIntelligence(
                new TacticalAnalyzer(rules), new GraphDecisionPolicy());
        var graph = new GameGraph(
                new PlannerAgent(intelligence, view),
                new AttackAgent(intelligence, view),
                new DefenseAgent(intelligence, view),
                new EvaluatorAgent(intelligence, view),
                new DecisionAgent(intelligence, view),
                new MoveExecutor(rules, view), view);
        var opponent = new DemoOpponent();
        var game = new GameEngine(rules);

        view.banner("GRAPH ENGINEERING", "Specialists • explicit typed hand-offs", MAGENTA);
        view.board(game.board());
        while (game.status() instanceof GameStatus.InProgress) {
            view.turn(game.board().moveCount() + 1, game.currentPlayer());
            var move = game.currentPlayer() == Player.X
                    ? opponent.choose(game.board())
                    : graph.execute(game.board(), Player.O);
            if (game.currentPlayer() == Player.X) {
                view.node("Opponent", BLUE, "Fixed input → %s".formatted(move.display()));
            }
            game.play(move);
            view.board(game.board());
        }
        view.result(game.status());
    }
}
