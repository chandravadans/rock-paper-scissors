package com.cv.challenge.rps.util;

import com.cv.challenge.rps.domain.Rule;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class Util {
    private static Gson gson = new Gson();

    public static Rule returnRuleForElement(List<Rule> ruleList, String element) {
        return ruleList.stream()
                .filter(rule -> rule.getElement().equals(element))
                .findFirst()
                .orElseGet(null);
    }

    // Reads a file in src/main/resources
    public static List readJsonArrayFromFile(String fileName, Type collectionType) throws FileNotFoundException {
        JsonReader reader = new JsonReader(new FileReader("src/main/resources/" + fileName));
        return gson.fromJson(reader, collectionType);
    }

}