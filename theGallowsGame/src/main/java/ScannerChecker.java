import java.util.Scanner;

public class ScannerChecker {
    static Scanner scanner = new Scanner(System.in);

    public static boolean isValidCommand(String userInput) {
        String[] partsOfUserInput = splitInput(userInput);

        if (partsOfUserInput.length == 0 || partsOfUserInput[0].isEmpty()) {
            printCommandsPrompt();
            return false;
        }
        String userCommand = partsOfUserInput[0].toLowerCase();
        switch (userCommand) {
            case "start":
                return true;
            case "exit":
                System.out.println("Выход из игры...");
                System.exit(0);
            default:
                System.out.println("Неверная команда. Пожалуйста, попробуйте снова.");
                printCommandsPrompt();
                return false;
        }
    }

    private static void printCommandsPrompt() {
        System.out.println("Введите \"start\" для старта новой игры");
        System.out.println("Введите \"exit\" выходa из игры");
    }

    private static String readUserInput() {
        printCommandsPrompt();
        return scanner.nextLine();
    }

    private static String[] splitInput(String input) {
        return input.trim().split("[\\s,;]+");
    }
}

