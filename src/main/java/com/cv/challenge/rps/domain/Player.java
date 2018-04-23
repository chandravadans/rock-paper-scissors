package com.cv.challenge.rps.domain;

import com.cv.challenge.rps.domain.enums.PlayerTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class Player {
    private static final Logger LOG = LoggerFactory.getLogger(Player.class);

    private String name;
    private PlayerTypeEnum type;
    private Random random;

    public Player() {
    }

    public Move makeMove(List<Rule> rules) {
        random = new Random();
        Scanner in = new Scanner(System.in);

        if (this.type == PlayerTypeEnum.human) {

            System.out.print("Valid choices : ");
            rules.forEach(rule -> System.out.print(rule.getElement() + " ( " + rule.getHotkey() + " ), "));
            System.out.println();
            Set<String> elements = rules.stream().map(rule -> rule.getElement().toLowerCase()).collect(Collectors.toSet());
            Set<String> hotkeys = rules.stream().map(Rule::getHotkey).collect(Collectors.toSet());
            String choice;
            boolean firstAttempt = true;
            do{
                if (firstAttempt) {
                    System.out.println(this.name + ", please enter your choice (full name of the element or hotkey) : ");
                } else {
                    System.out.println("Invalid choice! Please enter a valid element name/hotkey ");
                }
                if (System.console() != null) {
                    choice = new String(System.console().readPassword());
                } else {
                    LOG.warn("No console instance found, will take input in plain text...");
                    choice = in.nextLine();
                }
                firstAttempt = false;
            } while (!(elements.contains(choice) || hotkeys.contains(choice)));


            // If a hotkey was entered, return the full element name
            if (hotkeys.contains(choice)) {
                for(Rule rule : rules) {
                    if (rule.getHotkey().equals(choice)) {
                        choice = rule.getElement();
                        break;
                    }
                }
            }

            return new Move(this, choice);

        } else {
            // AI is fair, makes a random choice!
            return new Move(this, rules.get(random.nextInt(rules.size())).getElement());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerTypeEnum getType() {
        return type;
    }

    public void setType(PlayerTypeEnum type) {
        this.type = type;
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
