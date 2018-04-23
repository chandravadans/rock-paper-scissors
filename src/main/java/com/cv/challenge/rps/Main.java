package com.cv.challenge.rps;

import com.cv.challenge.rps.domain.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {

        // Read properties from resources
        InputStream resourcesFile = new FileInputStream("src/main/resources/game.properties");
        Properties config = new Properties();
        config.load(resourcesFile);

        Game game = new Game();
        try {

            // Initialise game from properties
            game.initialise(config);

            // Run Game n times
            Scanner in = new Scanner(System.in);
            System.out.println("How many games would you like to play? ");
            int numGames = in.nextInt();
            game.run(numGames);

        } catch (Exception e) {
            System.out.println("Error. " + e.getMessage() + " Please check your config and the log file for more details");
            LOG.error("Exception occurred!", e);
        }
    }
}
