package model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class WordTaker {
    public static Optional<String> getRandomWord(Path filePath) {
        try (Stream<String> lines = Files.lines(filePath, StandardCharsets.UTF_8)) {
            List<String> words = lines
                    .flatMap(line -> Arrays.stream(line.split(",")))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();

            if (words.isEmpty()) return Optional.empty();
            return Optional.of(words.get(ThreadLocalRandom.current().nextInt(words.size())));
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
