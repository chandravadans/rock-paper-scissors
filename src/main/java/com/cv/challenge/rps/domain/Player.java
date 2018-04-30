package com.cv.challenge.rps.domain;

import com.cv.challenge.rps.domain.enums.PlayerTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class Player {
    private static final Logger LOG = LoggerFactory.getLogger(Player.class);

    private String name;
    private PlayerTypeEnum type;
    private IPlayerStrategy playerStrategy;

    public Player(String name, PlayerTypeEnum playerType) {
        this.name = name;
        this.type = playerType;
        decidePlayerStrategy();
    }

    private void decidePlayerStrategy() {
        switch (type) {
            case human:
                playerStrategy = new HumanStrategy();
                break;
            case machine:
                playerStrategy = new MachineStrategy();
        }
    }

    public Move makeMove(List<Rule> rules) {
        if (playerStrategy == null) {
            decidePlayerStrategy();
        }
        System.out.println(this.name + "'s turn:");
        return new Move(this, playerStrategy.makeMove(rules));
    }

    public String getName() {
        return name;
    }

    public PlayerTypeEnum getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name) &&
                type == player.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}
