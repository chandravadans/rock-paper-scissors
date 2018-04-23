package com.cv.challenge.rps.services;

import com.cv.challenge.rps.domain.Rule;
import com.cv.challenge.rps.domain.enums.ValidationErrorEnum;
import com.cv.challenge.rps.exceptions.ValidationException;
import com.cv.challenge.rps.util.Util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RuleValidator {

    /**
     * Given a set of Rules defined over a set of elements, validates them and returns
     * if the rules are logically valid. Throws an Exception otherwise
     *
     * @param rules
     * @param elements
     * @throws ValidationException
     */
    public void validateRules(List<Rule> rules, List<String> elements) {

        // Each element should have exactly one rule
        elements.forEach(element -> {
            if (rules.stream()
                    .filter(r -> r.getElement().equals(element))
                    .count() != 1) {
                throw new ValidationException("Need exactly 1 rule for " + element + ".", ValidationErrorEnum.RULE_VALIDATION);
            }
        });

        // Number of elements should be equal to number of rules
        if (elements.size() != rules.size()) {
            throw new ValidationException("Some elements missing from declaration.", ValidationErrorEnum.RULE_VALIDATION);
        }

        // Each element should have a unique hotkey
        Set<String> hotkeys = new HashSet<>();
        rules.stream().forEach(rule -> {
            if (hotkeys.contains(rule.getHotkey())) {
                throw new ValidationException("Duplicate hotkeys detected. Multiple binds for " + rule.getHotkey()+".", ValidationErrorEnum.RULE_VALIDATION);
            }
            hotkeys.add(rule.getHotkey());
        });

        elements.forEach(element -> {

            Rule rule = Util.returnRuleForElement(rules, element);

            // Check rules
            rule.getWinsAgainst()
                    .forEach(loser -> {

                        // An element can't win against itself
                        if (element.equals(loser)) {
                            throw new ValidationException(element + " can't win against itself!",
                                    ValidationErrorEnum.RULE_VALIDATION);
                        }

                        // If an element wins against another element, the other element can't win against this element
                        Rule losingElementRule = Util.returnRuleForElement(rules, loser);
                        if (losingElementRule.getWinsAgainst().contains(element)) {
                            throw new ValidationException(
                                    element + " wins against " + loser + ", but " + loser
                                            + " wins against " + element + ". This is invalid!",
                                    ValidationErrorEnum.RULE_VALIDATION);
                        }
                    });
        });
    }
}
