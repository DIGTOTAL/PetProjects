package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {
    private static final Logger LOGGER = LogManager.getLogger(Game.class);

    public enum GuessOutcome {INVALID, ALREADY_GUESSED, HIT, MISS}

    private final String wordToGuess;
    private final List<Character> revealedChars;
    private final Set<Character> guessedLetters = new HashSet<>();
    private static final int MAX_ATTEMPTS = 6;
    private int remainingAttempts;

    public Game(String word) {
        String w = (word == null) ? "" : word.toLowerCase();
        this.wordToGuess = w;
        this.revealedChars = new ArrayList<>(w.length());
        initRevealed(w);
        this.remainingAttempts = MAX_ATTEMPTS;
        LOGGER.debug("Game created for word length {}", w.length());
    }

    public Game() {
        String picked = WordTaker.getRandomWordFromResource("words.txt");
        if (picked == null) {
            LOGGER.error("No word available to start the game. Aborting.");
            throw new IllegalStateException("No words available in resource words.txt");
        }

        String w = picked.toLowerCase();
        this.wordToGuess = w;
        this.revealedChars = new ArrayList<>(w.length());
        initRevealed(w);
        this.remainingAttempts = MAX_ATTEMPTS;
        LOGGER.debug("Game created with picked word (length={})", w.length());
    }

    private void initRevealed(String w) {
        for (int i = 0; i < w.length(); i++) {
            char ch = w.charAt(i);
            if (Character.isLetter(ch)) revealedChars.add('_');
            else revealedChars.add(ch);
        }
    }

    public GuessOutcome guess(char ch) {
        ch = Character.toLowerCase(ch);
        if (!isValidGuessChar(ch)) {
            LOGGER.debug("Invalid guess character: {}", ch);
            return GuessOutcome.INVALID;
        }
        if (isFinished()) {
            LOGGER.debug("Guess after finished game: {}", ch);
            return GuessOutcome.INVALID;
        }
        if (guessedLetters.contains(ch)) {
            LOGGER.debug("Already guessed character: {}", ch);
            return GuessOutcome.ALREADY_GUESSED;
        }

        guessedLetters.add(ch);
        boolean hit = openLetter(ch);
        if (!hit) {
            remainingAttempts--;
            LOGGER.info("Miss: {} remainingAttempts={}", ch, remainingAttempts);
        } else {
            LOGGER.info("Hit: {}", ch);
        }
        return hit ? GuessOutcome.HIT : GuessOutcome.MISS;
    }

    private boolean openLetter(char ch) {
        boolean hit = false;
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == ch) {
                revealedChars.set(i, ch);
                hit = true;
            }
        }
        return hit;
    }

    private boolean isValidGuessChar(char ch) {
        String rus = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        return rus.indexOf(ch) >= 0;
    }

    public String getRevealed() {
        if (revealedChars.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (char c : revealedChars) {
            sb.append(c).append(' ');
        }
        return sb.toString().trim();
    }

    private String getRevealedString() {
        StringBuilder sb = new StringBuilder(revealedChars.size());
        for (char c : revealedChars) sb.append(c);
        return sb.toString();
    }

    public boolean isWon() {
        return wordToGuess.equals(getRevealedString());
    }

    public boolean isLost() {
        return remainingAttempts <= 0 && !isWon();
    }

    public boolean isFinished() {
        return isWon() || isLost();
    }

    public String getWordToGuess() {
        return wordToGuess;
    }

    public int getRemainingAttempts() {
        return remainingAttempts;
    }

    public Set<Character> getGuessedLetters() {
        return Set.copyOf(guessedLetters);
    }
}