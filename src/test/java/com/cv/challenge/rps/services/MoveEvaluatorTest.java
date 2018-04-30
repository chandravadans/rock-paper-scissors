package com.cv.challenge.rps.services;

import com.cv.challenge.rps.domain.Move;
import com.cv.challenge.rps.domain.Player;
import com.cv.challenge.rps.domain.Rule;
import com.cv.challenge.rps.domain.enums.PlayerTypeEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoveEvaluatorTest {

    MoveEvaluator testMoveEvaluator = new MoveEvaluator();
    List<Rule> rules;
    List<Move> moves;

    @BeforeEach
    void setup() {
        rules = new ArrayList<>();

        Rule rule1 = new Rule();
        rule1.setElement("rock");
        rule1.setWinsAgainst(Arrays.asList("scissors"));
        rule1.setHotkey("r");

        Rule rule2 = new Rule();
        rule2.setElement("paper");
        rule2.setWinsAgainst(Arrays.asList("rock"));
        rule2.setHotkey("p");

        Rule rule3 = new Rule();
        rule3.setElement("scissors");
        rule3.setHotkey("s");
        rule3.setWinsAgainst(Arrays.asList("paper"));

        rules.addAll(Arrays.asList(rule1, rule2, rule3));

        Player player1 = new Player("p1", PlayerTypeEnum.machine);

        Player player2 = new Player("p2", PlayerTypeEnum.machine);

        Move move1 = new Move(player1, "rock");
        Move move2 = new Move(player2, "scissors");
        moves = new ArrayList<>(Arrays.asList(move1, move2));
    }

    @Test
    @DisplayName("Sane moves")
    void test1() {
        Player winner = testMoveEvaluator.getWinner(moves, rules);
        Assertions.assertEquals("p1", winner.getName());
    }

    @Test
    @DisplayName("Tie scenario")
    void test2() {
        Player p2 = new Player("p2", PlayerTypeEnum.machine);
        Move move2 = new Move(p2, "rock");
        moves.set(1, move2);
        Player winner = testMoveEvaluator.getWinner(moves, rules);
        Assertions.assertEquals(null, winner);
    }
}
