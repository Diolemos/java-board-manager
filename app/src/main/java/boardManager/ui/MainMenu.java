package boardManager.ui;

import java.sql.SQLException;
import java.util.Scanner;

import boardManager.persistence.config.ConnectionConfig;
import boardManager.persistence.dao.BoardColumnDao;
import boardManager.persistence.dao.BoardDAO;
import boardManager.persistence.entity.BoardColumnEntity;
import boardManager.persistence.entity.BoardColumnKindEnum;
import boardManager.persistence.entity.BoardEntity;

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

    private void createBoard() {
    try (var connection = ConnectionConfig.getConnection()) {
        var boardDao = new BoardDAO(connection);
        var columnDao = new BoardColumnDao(connection);

        // 1. Ask for board name
        System.out.println("What's the name of your Board?");
        var boardName = scanner.nextLine();
        var board = new BoardEntity();
        board.setName(boardName);

        // 2. Insert board
        boardDao.insert(board);

        // 3. Ask for initial column
        System.out.println("Name of the starting column?");
        var initialColumnName = scanner.nextLine();
        var initialColumn = new BoardColumnEntity();
        initialColumn.setName(initialColumnName);
        initialColumn.setKind(BoardColumnKindEnum.INITIAL);
        initialColumn.setOrder(0);
        initialColumn.setBoard(board);

        columnDao.insert(initialColumn);

        // 4. Optionally add extra columns
        System.out.println("How many extra 'PENDING' columns do you want?");
        int extraColumns = scanner.nextInt();
        scanner.nextLine(); // consume newline
        for (int i = 1; i <= extraColumns; i++) {
            System.out.printf("Name of pending column #%d: ", i);
            var pendingName = scanner.nextLine();
            var pendingColumn = new BoardColumnEntity();
            pendingColumn.setName(pendingName);
            pendingColumn.setKind(BoardColumnKindEnum.PENDING);
            pendingColumn.setOrder(i);
            pendingColumn.setBoard(board);

            columnDao.insert(pendingColumn);
        }

        // 5. Commit transaction
        connection.commit();
        System.out.printf("Board '%s' created successfully with %d columns.%n", boardName, extraColumns + 1);

    } catch (SQLException e) {
        System.out.println("Error creating board: " + e.getMessage());
        e.printStackTrace();
    }
}

private void deleteBoard() {
    System.out.println("Enter the ID of the Board you'd like to delete:");
    long boardId = scanner.nextLong();
    scanner.nextLine(); // consume newline
    try (var connection = ConnectionConfig.getConnection()) {
        var boardDao = new BoardDAO(connection);

        boolean deleted = boardDao.delete(boardId);
        if (deleted) {
            connection.commit();
            System.out.printf("Board with ID %d deleted successfully.%n", boardId);
        } else {
            System.out.printf("No board found with ID %d.%n", boardId);
        }

    } catch (SQLException e) {
        System.out.println("Error deleting board: " + e.getMessage());
        e.printStackTrace();
    }
}

}
