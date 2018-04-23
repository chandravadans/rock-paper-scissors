package com.cv.challenge.rps.services;

import com.cv.challenge.rps.configs.GameConfig;
import com.cv.challenge.rps.domain.enums.ValidationErrorEnum;
import com.cv.challenge.rps.exceptions.ValidationException;

import java.util.HashSet;
import java.util.Set;

public class ConfigValidator {

    private RuleValidator ruleValidator;

    public ConfigValidator() {
        ruleValidator = new RuleValidator();
    }

    public void validateGameConfig(GameConfig config) {

        // Check there are elements
        if (config.getElements() == null || (config.getElements() != null && config.getElements().size() < 2)) {
            throw new ValidationException("Please define more elements in the config!", ValidationErrorEnum.CONFIG_VALIDATION);
        }

        // Check elements are unique
        Set<String> found = new HashSet<>();
        config.getElements().forEach(element -> {
            if (found.contains(element)) {
                throw new ValidationException("Duplicate elements specified in config!", ValidationErrorEnum.CONFIG_VALIDATION);
            }
            found.add(element);
        });

        // Check players are sane
        config.getPlayers().forEach(player -> {
            if (player.getType() == null) {
                throw new ValidationException("Invalid player type for " + player.getName() + "!", ValidationErrorEnum.CONFIG_VALIDATION);
            }
        });

        // Check there are atleast 2 players
        if (config.getPlayers().size() < 2) {
            throw new ValidationException("Need atleast two to tango! :)", ValidationErrorEnum.CONFIG_VALIDATION);
        }

        // Check rules
        ruleValidator.validateRules(config.getRules(), config.getElements());
    }


    // To make it easy to run tests
    public void setRuleValidator(RuleValidator ruleValidator) {
        this.ruleValidator = ruleValidator;
    }
}
