package com.fhy.tictactoe.graph;

import com.fhy.tictactoe.core.Cell;

enum Strategy { OFFENSIVE, BALANCED, DEFENSIVE }
enum Intent { ATTACK, DEFENSE }

record Proposal(int row, int column, double confidence, String finding) {
    Cell cell() { return new Cell(row - 1, column - 1); }
    static Proposal of(Cell cell, double confidence, String finding) {
        return new Proposal(cell.row() + 1, cell.column() + 1, confidence, finding);
    }
}

record Evaluation(double attackScore, double defenseScore, String assessment) {}
record Decision(Intent intent, int row, int column, String reason) {
    Cell cell() { return new Cell(row - 1, column - 1); }
    static Decision from(Intent intent, Proposal proposal, String reason) {
        return new Decision(intent, proposal.row(), proposal.column(), reason);
    }
}

record StrategyResponse(Strategy strategy, String rationale) {}
