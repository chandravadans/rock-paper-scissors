package com.cv.challenge.rps.domain;

import java.util.List;

public interface IPlayerStrategy {
    String makeMove(List<Rule> ruleList);
}
