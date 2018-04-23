package com.cv.challenge.rps.configs;

import com.cv.challenge.rps.domain.Player;
import com.cv.challenge.rps.domain.Rule;
import com.cv.challenge.rps.services.ConfigValidator;
import com.cv.challenge.rps.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class GameConfig {

    private static final Logger LOG = LoggerFactory.getLogger(GameConfig.class);

    private List<String> elements;
    private List<Rule> rules;
    private List<Player> players;
    private ConfigValidator configEvaluator;

    public GameConfig() {

    }

    public GameConfig(Properties properties) throws Exception {
        try {

            Gson gson = new Gson();

            // Read elements
            String elementString = properties.getProperty("elements");
            this.elements = new ArrayList<>();
            Collections.addAll(elements, elementString.split(","));

            // Read players
            String playersFileName = properties.getProperty("players");
            Type playersType =  new TypeToken<List<Player>>() {}.getType();
            players = (List<Player>)Util.readJsonArrayFromFile(playersFileName, playersType);


            // Read rules
            String rulesFileName = properties.getProperty("rules");
            Type rulesType = new TypeToken<List<Rule>>() {}.getType();
            rules = (List<Rule>) Util.readJsonArrayFromFile(rulesFileName, rulesType);

            configEvaluator = new ConfigValidator();
            configEvaluator.validateGameConfig(this);

        } catch (Exception e) {
            LOG.error("Error while building config!", e);
            throw e;
        }

    }

    public List<String> getElements() {
        return elements;
    }

    public void setElements(List<String> elements) {
        this.elements = elements;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
