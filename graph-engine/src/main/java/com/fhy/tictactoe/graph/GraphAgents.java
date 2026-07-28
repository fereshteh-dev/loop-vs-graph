package com.fhy.tictactoe.graph;

import com.fhy.tictactoe.core.console.ConsoleView;

import static com.fhy.tictactoe.core.console.Ansi.*;

interface GraphNode {
    GraphState process(GraphState state);
}

final class PlannerAgent implements GraphNode {
    private final AgentIntelligence ai; private final ConsoleView view;
    PlannerAgent(AgentIntelligence ai, ConsoleView view) { this.ai = ai; this.view = view; }
    public GraphState process(GraphState state) {
        var plan = ai.plan(state.board(), state.player());
        view.node("Planner", BLUE, "%s — %s".formatted(plan.strategy(), plan.rationale()));
        return state.withStrategy(plan.strategy());
    }
}

final class AttackAgent implements GraphNode {
    private final AgentIntelligence ai; private final ConsoleView view;
    AttackAgent(AgentIntelligence ai, ConsoleView view) { this.ai = ai; this.view = view; }
    public GraphState process(GraphState state) {
        var proposal = ai.attack(state.board(), state.player());
        view.node("Attack", MAGENTA, "%s → %s".formatted(proposal.finding(), proposal.cell().display()));
        return state.withAttack(proposal);
    }
}

final class DefenseAgent implements GraphNode {
    private final AgentIntelligence ai; private final ConsoleView view;
    DefenseAgent(AgentIntelligence ai, ConsoleView view) { this.ai = ai; this.view = view; }
    public GraphState process(GraphState state) {
        var proposal = ai.defend(state.board(), state.player());
        view.node("Defense", CYAN, "%s → %s".formatted(proposal.finding(), proposal.cell().display()));
        return state.withDefense(proposal);
    }
}

final class EvaluatorAgent implements GraphNode {
    private final AgentIntelligence ai; private final ConsoleView view;
    EvaluatorAgent(AgentIntelligence ai, ConsoleView view) { this.ai = ai; this.view = view; }
    public GraphState process(GraphState state) {
        var result = ai.evaluate(state.board(), state.player(), state.attack(), state.defense());
        view.node("Evaluator", YELLOW, "Attack %.1f  │  Defense %.1f  • %s".formatted(
                result.attackScore(), result.defenseScore(), result.assessment()));
        return state.withEvaluation(result);
    }
}

final class DecisionAgent implements GraphNode {
    private final AgentIntelligence ai; private final ConsoleView view;
    DecisionAgent(AgentIntelligence ai, ConsoleView view) { this.ai = ai; this.view = view; }
    public GraphState process(GraphState state) {
        var decision = ai.decide(state.strategy(), state.attack(), state.defense(), state.evaluation());
        view.node("Decision", GREEN, "%s → %s — %s".formatted(
                decision.intent(), decision.cell().display(), decision.reason()));
        return state.withDecision(decision);
    }
}
