package com.cv.challenge.rps.services;

import com.cv.challenge.rps.configs.GameConfig;
import com.cv.challenge.rps.domain.Player;
import com.cv.challenge.rps.domain.enums.PlayerTypeEnum;
import com.cv.challenge.rps.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigValidatorTest {

    ConfigValidator testConfigValidator;
    GameConfig gameConfig;

    @BeforeEach
    void resetSetup() {
        testConfigValidator = new ConfigValidator();
        gameConfig = new GameConfig();
        gameConfig.setElements(Arrays.asList("rock", "paper", "scissors"));

        Player player1 = new Player("p1", PlayerTypeEnum.human);

        Player player2 = new Player("p2", PlayerTypeEnum.machine);

        List<Player> players = new ArrayList<>(Arrays.asList(player1, player2));

        gameConfig.setPlayers(players);

        gameConfig.setRules(new ArrayList<>());

        RuleValidator mockRuleValidator = Mockito.mock(RuleValidator.class);
        testConfigValidator.setRuleValidator(mockRuleValidator);
    }

    @Test
    @DisplayName("Sane Config")
    void test0() {
        testConfigValidator.validateGameConfig(gameConfig);
    }

    @Test
    @DisplayName("Duplicate elements")
    void test1() {
        List<String> elements = new ArrayList<>(gameConfig.getElements());
        elements.add("rock");
        gameConfig.setElements(elements);
        Assertions.assertThrows(ValidationException.class, () -> testConfigValidator.validateGameConfig(gameConfig));
    }

    @Test
    @DisplayName("Invalid player type")
    void test2() {
        List<Player> players = new ArrayList<>(gameConfig.getPlayers());
        players.add(new Player("p3", null));
        gameConfig.setPlayers(players);
        Assertions.assertThrows(ValidationException.class, () -> testConfigValidator.validateGameConfig(gameConfig));
    }

    @Test
    @DisplayName("Too few players")
    void test3() {
        List<Player> players = new ArrayList<>(gameConfig.getPlayers());
        players.remove(0);
        gameConfig.setPlayers(players);
        Assertions.assertThrows(ValidationException.class, () -> testConfigValidator.validateGameConfig(gameConfig));
    }

    @Test
    @DisplayName("No elements defined")
    void test4() {
        gameConfig.setElements(new ArrayList<>());
        Assertions.assertThrows(ValidationException.class, () -> testConfigValidator.validateGameConfig(gameConfig));
    }

    @Test
    @DisplayName("Too few elements")
    void test5() {
        gameConfig.setElements(Arrays.asList("rock"));
        Assertions.assertThrows(ValidationException.class, () -> testConfigValidator.validateGameConfig(gameConfig));
    }
}
