package com.cv.challenge.rps.domain;

import com.cv.challenge.rps.configs.GameConfig;
import com.cv.challenge.rps.services.MoveEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class Game {
    private static final Logger LOG = LoggerFactory.getLogger(Game.class);

    private GameConfig gameConfig;
    private MoveEvaluator moveEvaluator;

    private Map<String, Integer> winStats;

    public void initialise(Properties gameProperties) throws Exception {
        winStats = new HashMap<>();
        gameConfig = new GameConfig(gameProperties);
        moveEvaluator = new MoveEvaluator();
    }

    public void run(int numTimes) throws InterruptedException {
        int numberOfTies = 0;

        for (int i = 0; i < numTimes; i++) {

            if (i == 0) {
                // Reset all wins to 0 in the first round
                gameConfig.getPlayers().forEach(player -> winStats.put(player.getName(), 0));
            }

            System.out.println("-------------------");
            System.out.println("     Round " + (i + 1));
            System.out.println("-------------------");

            // All players make their moves
            List<Move> moves = gameConfig.getPlayers()
                    .stream()
                    .map(player -> player.makeMove(gameConfig.getRules()))
                    .collect(Collectors.toList());

            // Winner is chosen
            Player winner = moveEvaluator.getWinner(moves, gameConfig.getRules());
            if (winner == null) {
                System.out.println("Its a tie!");
                numberOfTies++;
            } else {

                String winnerName = winner.getName();

                // Results are printed
                System.out.println("Congratulations " + winnerName + "! You've won the game!");
                System.out.println("Here's what everyone chose: ");
                moves.forEach(move -> System.out.println(move.getPlayer().getName() + " -> " + move.getChoice()));

                // Winning stats are recorded
                winStats.put(winnerName, winStats.getOrDefault(winnerName, 0) + 1);
                Thread.sleep(3000);
            }
        }

        // Final tally is printed
        System.out.println("Here's the tally after " + numTimes + " rounds: ");
        final int ties = numberOfTies;
        winStats.forEach((winner, numWins) -> {
            int numberOfWins = numWins;
            int numberOfLosses = numTimes - numberOfWins - ties;
            System.out.println(winner + " : " + numberOfWins + " wins, " + numberOfLosses + " losses");
        });
        System.out.println("Number of ties: " + ties);
    }

}
