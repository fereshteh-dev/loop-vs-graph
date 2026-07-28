package com.fhy.tictactoe.loop;

import com.fhy.tictactoe.core.Board;
import com.fhy.tictactoe.core.Cell;
import com.fhy.tictactoe.core.Player;

import java.util.Set;

interface MoveReasoner {
    MoveCandidate propose(Board board, Player player, Set<Cell> rejected);
}
