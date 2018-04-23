package com.cv.challenge.rps.services;

import com.cv.challenge.rps.domain.Rule;
import com.cv.challenge.rps.exceptions.ValidationException;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RuleValidatorTest {

    RuleValidator testRuleValidator = new RuleValidator();
    List<Rule> rules;
    List<String> elements;

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

        elements = Arrays.asList("rock", "paper", "scissors");
    }

    @Test
    @DisplayName("Sane config")
    void test1() {
        testRuleValidator.validateRules(rules,elements);
    }

    @Test
    @DisplayName("Duplicate rules")
    void test2() {
        Rule r = new Rule();
        r.setElement("rock");
        r.setWinsAgainst(Arrays.asList("paper"));
        rules.add(r);

        Assertions.assertThrows(ValidationException.class, () -> testRuleValidator.validateRules(rules,elements));
    }

    @Test
    @DisplayName("Missing rule")
    void test3() {
        rules.remove(0);
        Assertions.assertThrows(ValidationException.class, () -> testRuleValidator.validateRules(rules,elements));
    }

    @Test
    @DisplayName("Conflicting rules")
    void test4() {
        Rule r = rules.get(2);
        r.setWinsAgainst(Arrays.asList("paper", "rock"));
        rules.set(2, r);
        Assertions.assertThrows(ValidationException.class, () -> testRuleValidator.validateRules(rules,elements));
    }

    @Test
    @DisplayName("Conflicting hotkeys")
    void test5() {
        Rule r = rules.get(2);
        r.setHotkey("r");
        rules.set(2, r);
        Assertions.assertThrows(ValidationException.class, () -> testRuleValidator.validateRules(rules,elements));
    }

    @Test
    @DisplayName("Element that doesn't win against anything")
    void test6() {
        Rule r = rules.get(2);
        r.setWinsAgainst(new ArrayList<>());
        rules.set(2, r);
        testRuleValidator.validateRules(rules, elements);
    }
}
