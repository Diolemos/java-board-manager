package boardManager.ui;

import java.sql.SQLException;
import java.util.Scanner;

public class MainMenu {
    private final Scanner scanner = new Scanner(System.in);

    public void execute() throws SQLException {
        System.out.println("Welcome to Board Manager!");
        int option;
        while (true) {
            System.out.println("1 - Create Board");
            System.out.println("2 - Select Board");
            System.out.println("3 - Delete Board");
            System.out.println("4 - Exit");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> System.out.println("Create Board - TODO");
                case 2 -> System.out.println("Select Board - TODO");
                case 3 -> System.out.println("Delete Board - TODO");
                case 4 -> System.exit(0);
                default -> System.out.println("Invalid option.");
            }
        }
    }
}
