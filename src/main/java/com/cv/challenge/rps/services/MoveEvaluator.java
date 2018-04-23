package com.cv.challenge.rps.services;

import com.cv.challenge.rps.domain.Move;
import com.cv.challenge.rps.domain.Player;
import com.cv.challenge.rps.domain.Rule;
import com.cv.challenge.rps.util.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MoveEvaluator {

    /**
     * Given a list of moves made by players and the list of rules defined, returns the player with maximum number of wins
     * If there are just 2 players and there's a tie, returns null
     * @param moves
     * @param rules
     * @return
     */
    public Player getWinner(List<Move> moves, List<Rule> rules) {

        int maxWins = 0;
        Player winner = null;
        Map<String, Integer> winCounts = new HashMap<>();

        // Consider all combinations, return the player with maximum wins
        for (int i = 0; i < moves.size(); i++) {
            for (int j = 0; j < moves.size(); j++) {

                // Don't consider player vs him/herself
                if (!moves.get(i).getPlayer().equals(moves.get(j).getPlayer())) {

                    Player p1 = moves.get(i).getPlayer();
                    Player p2 = moves.get(j).getPlayer();

                    String p1Choice = moves.get(i).getChoice();
                    String p2Choice = moves.get(j).getChoice();

                    Rule p1Rule = Util.returnRuleForElement(rules, p1Choice);
                    Rule p2Rule = Util.returnRuleForElement(rules, p2Choice);

                    if (p1Rule.getWinsAgainst().contains(p2Choice)) {
                        // If p1's move wins against p2's move
                        if (winCounts.containsKey(p1.getName())) {
                            winCounts.put(p1.getName(), winCounts.get(p1.getName()) + 1);
                        } else {
                            winCounts.put(p1.getName(), 1);
                        }

                        // Update maxWins if required
                        if (winCounts.get(p1.getName()) > maxWins) {
                            maxWins = winCounts.get(p1.getName());
                            winner = p1;
                        }

                    } else if (p2Rule.getWinsAgainst().contains(p1Choice)) {
                        // If p2's move wins against p1's move
                        if (winCounts.containsKey(p2.getName())) {
                            winCounts.put(p2.getName(), winCounts.get(p2.getName()) + 1);
                        } else {
                            winCounts.put(p2.getName(), 1);
                        }

                        if (winCounts.get(p2.getName()) > maxWins) {
                            maxWins = winCounts.get(p2.getName());
                            winner = p2;
                        }
                    }

                }
            }
        }
        return winner;
    }
}
