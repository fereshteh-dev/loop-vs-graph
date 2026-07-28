package com.fhy.tictactoe.graph;

import com.fhy.tictactoe.core.Board;
import com.fhy.tictactoe.core.Player;

interface AgentIntelligence {
    StrategyResponse plan(Board board, Player player);
    Proposal attack(Board board, Player player);
    Proposal defend(Board board, Player player);
    Evaluation evaluate(Board board, Player player, Proposal attack, Proposal defense);
    Decision decide(Strategy strategy, Proposal attack, Proposal defense, Evaluation evaluation);
}
