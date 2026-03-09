import model.Game;
import model.Game.GuessOutcome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.ConsoleRenderer;

import java.util.Scanner;
import java.util.function.Supplier;

public class GameRunner {
    private static final Logger LOGGER = LogManager.getLogger(GameRunner.class);

    private final Scanner scanner;
    private final Supplier<Game> gameFactory;

    public GameRunner(Scanner scanner, Supplier<Game> gameFactory) {
        this.scanner = scanner;
        this.gameFactory = gameFactory;
    }

    public void run() {
        while (true) {
            System.out.println("Введите \"start\" для новой игры или \"exit\" для выхода:");
            String cmd = scanner.nextLine().trim().toLowerCase();
            if ("exit".equals(cmd)) {
                LOGGER.info("Application exit by user command");
                System.out.println("Выход...");
                break;
            }
            if (!"start".equals(cmd)) continue;

            final Game game;
            try {
                game = gameFactory.get();
            } catch (Exception e) {
                LOGGER.error("Failed to create game instance", e);
                System.out.println("Не удалось запустить игру. Проверьте логи.");
                continue;
            }

            System.out.println(ConsoleRenderer.render(game.getRemainingAttempts()));
            System.out.println("Слово: " + game.getRevealed());
            while (!game.isFinished()) {
                System.out.print("Введите одну букву (или \"exit\"): ");
                String input = scanner.nextLine().trim().toLowerCase();
                if (input.isEmpty()) continue;
                if ("exit".equals(input)) {
                    LOGGER.info("Application exit during game by user command");
                    System.out.println("Выход...");
                    return;
                }
                if (input.length() != 1) {
                    System.out.println("Пожалуйста, введите одну букву кириллицы.");
                    continue;
                }
                char ch = input.charAt(0);

                if (!Character.isLetter(ch) || Character.UnicodeScript.of(ch) != Character.UnicodeScript.CYRILLIC) {
                    System.out.println("Пожалуйста, введите букву кириллицы.");
                    continue;
                }

                GuessOutcome result = game.guess(ch);
                switch (result) {
                    case INVALID -> System.out.println("Недопустимый символ. Используйте маленькие буквы кириллицы.");
                    case ALREADY_GUESSED -> System.out.println("Эта буква уже использовалась.");
                    case HIT -> System.out.println("Есть такая буква!");
                    case MISS ->
                            System.out.println("Нет такой буквы. Осталось попыток: " + game.getRemainingAttempts());
                }

                System.out.println(ConsoleRenderer.render(game.getRemainingAttempts()));
                System.out.println("Слово: " + game.getRevealed());
                System.out.println("Использованные буквы: " + game.getGuessedLetters());
            }

            if (game.isWon()) {
                LOGGER.info("Game won. Word={}", game.getWordToGuess());
                System.out.println("Победа! Слово: " + game.getWordToGuess());
            } else {
                LOGGER.info("Game lost. Word={}", game.getWordToGuess());
                System.out.println("Проигрыш. Слово: " + game.getWordToGuess());
            }
        }
    }
}