package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class WordTaker {
    private static final Logger LOGGER = LogManager.getLogger(WordTaker.class);

    public static String getRandomWordFromResource(String resourceName) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try (InputStream is = cl.getResourceAsStream(resourceName)) {
            if (is == null) {
                LOGGER.error("Resource not found: {}", resourceName);
                return null;
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                List<String> words = br.lines()
                        .flatMap(line -> Arrays.stream(line.split(",")))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(String::toLowerCase)
                        .toList();
                if (words.isEmpty()) {
                    LOGGER.error("No words loaded from resource: {}", resourceName);
                    return null;
                }
                String picked = words.get(ThreadLocalRandom.current().nextInt(words.size()));
                LOGGER.debug("Picked word from {} (length={})", resourceName, picked.length());
                return picked;
            }
        } catch (Exception e) {
            LOGGER.error("Failed to load words from resource: {}", resourceName, e);
            return null;
        }
    }
}