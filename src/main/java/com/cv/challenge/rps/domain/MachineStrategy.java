package com.cv.challenge.rps.domain;

import java.util.List;
import java.util.Random;

public class MachineStrategy implements IPlayerStrategy {
    @Override
    public String makeMove(List<Rule> ruleList) {
        return ruleList.get(new Random().nextInt(ruleList.size())).getElement();
    }
}
