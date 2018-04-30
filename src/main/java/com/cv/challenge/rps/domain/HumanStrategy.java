package com.cv.challenge.rps.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class HumanStrategy implements IPlayerStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(HumanStrategy.class);

    @Override
    /**
     * Makes a move, given a list of rules
     */
    public String makeMove(List<Rule> ruleList) {

        // Print valid elements and corresponding hotkeys
        System.out.print("Valid choices : ");
        ruleList.forEach(rule -> System.out.print(rule.getElement() + " (" + rule.getHotkey() + "), "));
        System.out.println();

        // Record the list of valid elements and corresponding hotkeys for checking against user input in O(1)
        Set<String> elements = ruleList.stream().map(rule -> rule.getElement().toLowerCase()).collect(Collectors.toSet());
        Set<String> hotkeys = ruleList.stream().map(Rule::getHotkey).collect(Collectors.toSet());

        // Take input from user
        String choice = getUserInput(elements, hotkeys);

        // If a hotkey was entered, return the full element name
        if (hotkeys.contains(choice)) {
            return ruleList.stream()
                    .filter(rule -> rule.getHotkey().equals(choice))
                    .findFirst()
                    .get()
                    .getElement();
        }
        return choice;
    }

    /**
     * A method that takes an input from the user that's among the elements or hotkeys
     *
     * @param elements Valid element names
     * @param hotkeys  Valid hotkeys
     * @return
     */
    private String getUserInput(Set<String> elements, Set<String> hotkeys) {
        String choice = null;
        boolean validChoice = false;
        Scanner in = new Scanner(System.in);
        while (!validChoice) {
            System.out.println("Please enter your choice (full name of the element or hotkey) : ");

            // Try to read input as a password. Works only when run from the console
            if (System.console() != null) {
                choice = new String(System.console().readPassword());
            } else {
                // When reading as a password doesn't work (usually in IDEs)
                LOG.warn("No console instance found, will take input in plain text...");
                choice = in.nextLine();
            }

            // A valid input should be one of elements or hotkeys
            if (elements.contains(choice) || hotkeys.contains(choice)) {
                validChoice = true;
            } else {
                System.out.println("Invalid choice! Please try again...");
            }
        }
        return choice;
    }

}
