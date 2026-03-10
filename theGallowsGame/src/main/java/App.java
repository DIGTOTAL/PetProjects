import model.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class App {
    private static final Logger LOG = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        LOG.info("Application started");
        try (Scanner scanner = new Scanner(System.in)) {
            new GameRunner(scanner, Game::new).run();
        } catch (Exception e) {
            LOG.error("Unhandled exception in main", e);
            System.out.println("Приложение завершилось с ошибкой. Проверьте логи.");
        }
        LOG.info("Application finished");
    }
}