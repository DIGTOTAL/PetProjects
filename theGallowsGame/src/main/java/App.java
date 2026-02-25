import java.nio.file.Path;
import java.util.Optional;
import model.WordTaker;

public class App {
    public static void main(String[] args) {
        Path wordsFile = Path.of("words.txt");
        Optional<String> word = WordTaker.getRandomWord(wordsFile);
        word.ifPresentOrElse(
                w -> System.out.println("Chosen: " + w),
                () -> System.out.println("No word found")
        );
    }
}

