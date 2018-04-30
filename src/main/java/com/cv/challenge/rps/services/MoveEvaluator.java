package com.cv.challenge.rps.services;

import com.cv.challenge.rps.domain.Move;
import com.cv.challenge.rps.domain.Player;
import com.cv.challenge.rps.domain.Rule;
import com.cv.challenge.rps.util.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoveEvaluator {

    /**
     * Given a list of moves made by players and the list of rules defined, returns the player with maximum number of wins
     * If there are just 2 players and there's a tie, returns null
     *
     * @param moves
     * @param rules
     * @return
     */
    public Player getWinner(List<Move> moves, List<Rule> rules) {

        int maxWins = 0;
        Player winner = null;
        Map<Player, Integer> winCounts = new HashMap<>();

        // Consider all combinations, return the player with maximum wins
        for (int i = 0; i < moves.size(); i++) {
            for (int j = 0; j < moves.size(); j++) {

                Player winningPlayer = decideWinner(moves.get(i), moves.get(j), rules);

                if (winningPlayer == null) {
                    // Don't do anything if the result is a tie
                    continue;
                }

                // Record 1 win for player if not already done, else add 1 to existing number of wins
                winCounts.compute(winningPlayer, (player, wins) -> wins == null ? 1 : wins + 1);

                // Update maxWins if required
                if (winCounts.get(winningPlayer) > maxWins) {
                    maxWins = winCounts.get(winningPlayer);
                    winner = winningPlayer;
                }
            }
        }
        return winner;
    }

    /**
     * Given 2 Moves and a list of rules, returns the player who played the winning move. Returns null if there's a tie
     *
     * @param m1    Move by player 1
     * @param m2    Move by player 2
     * @param rules List of rules the moves should be evaluated against
     * @return Player who made the winning move, null otherwise
     */
    private Player decideWinner(Move m1, Move m2, List<Rule> rules) {

        Player p1 = m1.getPlayer();
        Player p2 = m2.getPlayer();

        String p1Choice = m1.getChoice();
        String p2Choice = m2.getChoice();

        Rule p1Rule = Util.returnRuleForElement(rules, p1Choice);
        Rule p2Rule = Util.returnRuleForElement(rules, p2Choice);

        if (p1Rule.getWinsAgainst().contains(p2Choice)) {
            // p1 wins against p2
            return p1;
        } else if (p2Rule.getWinsAgainst().contains(p1Choice)) {
            // p2 wins against p1
            return p2;
        } else {
            // Its a tie, nobody wins
            return null;
        }
    }
}
