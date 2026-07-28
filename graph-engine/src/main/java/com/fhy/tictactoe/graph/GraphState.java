package com.fhy.tictactoe.graph;

import com.fhy.tictactoe.core.Board;
import com.fhy.tictactoe.core.Cell;
import com.fhy.tictactoe.core.Player;

record GraphState(
        Board board,
        Player player,
        Strategy strategy,
        Proposal attack,
        Proposal defense,
        Evaluation evaluation,
        Decision decision) {

    static GraphState start(Board board, Player player) {
        return new GraphState(board, player, null, null, null, null, null);
    }

    GraphState withStrategy(Strategy value) {
        return new GraphState(board, player, value, attack, defense, evaluation, decision);
    }
    GraphState withAttack(Proposal value) {
        return new GraphState(board, player, strategy, value, defense, evaluation, decision);
    }
    GraphState withDefense(Proposal value) {
        return new GraphState(board, player, strategy, attack, value, evaluation, decision);
    }
    GraphState withEvaluation(Evaluation value) {
        return new GraphState(board, player, strategy, attack, defense, value, decision);
    }
    GraphState withDecision(Decision value) {
        return new GraphState(board, player, strategy, attack, defense, evaluation, value);
    }
}
