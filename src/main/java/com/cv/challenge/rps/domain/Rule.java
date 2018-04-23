package com.cv.challenge.rps.domain;

import java.util.List;


public class Rule {

    private String element;
    private List<String> winsAgainst;
    private String hotkey;

    public Rule() {

    }

    public String getHotkey() {
        return hotkey;
    }

    public void setHotkey(String hotkey) {
        this.hotkey = hotkey;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public List<String> getWinsAgainst() {
        return winsAgainst;
    }

    public void setWinsAgainst(List<String> winsAgainst) {
        this.winsAgainst = winsAgainst;
    }

}
