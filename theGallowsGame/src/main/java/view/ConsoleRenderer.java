package view;

import java.util.List;

public class ConsoleRenderer {
    private static final int MAX_ATTEMPTS = 6;

    private static final List<String> FRAMES = List.of(
            " +---+\n" +
                    " |   |\n" +
                    " |    \n" +
                    " |    \n" +
                    " |    \n" +
                    " |    \n" +
                    "=========",
            " +---+\n" +
                    " |   |\n" +
                    " |   O\n" +
                    " |    \n" +
                    " |    \n" +
                    " |    \n" +
                    "=========",
            " +---+\n" +
                    " |   |\n" +
                    " |   O\n" +
                    " |   |\n" +
                    " |    \n" +
                    " |    \n" +
                    "=========",
            " +---+\n" +
                    " |   |\n" +
                    " |   O\n" +
                    " |  /|\n" +
                    " |    \n" +
                    " |    \n" +
                    "=========",
            " +---+\n" +
                    " |   |\n" +
                    " |   O\n" +
                    " |  /|\\\n" +
                    " |    \n" +
                    " |    \n" +
                    "=========",
            " +---+\n" +
                    " |   |\n" +
                    " |   O\n" +
                    " |  /|\\\n" +
                    " |  /  \n" +
                    " |    \n" +
                    "=========",
            " +---+\n" +
                    " |   |\n" +
                    " |   O\n" +
                    " |  /|\\\n" +
                    " |  / \\\n" +
                    " |    \n" +
                    "========="
    );

    public static String render(int remainingAttempts) {
        int mistakes = MAX_ATTEMPTS - Math.max(0, Math.min(MAX_ATTEMPTS, remainingAttempts));
        return FRAMES.get(mistakes);
    }
}