package boardManager.ui;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import boardManager.persistence.config.ConnectionConfig;
import boardManager.persistence.dao.BoardColumnDao;
import boardManager.persistence.entity.BoardColumnEntity;
import boardManager.persistence.entity.BoardColumnKindEnum;
import boardManager.persistence.entity.BoardEntity;

public class BoardMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final BoardEntity board;

    public BoardMenu(BoardEntity board) {
        this.board = board;
    }

    public void execute() {
        int option;
        while (true) {
            System.out.printf("Board: %s%n", board.getName());
            System.out.println("1 - List Columns");
            System.out.println("2 - Add Column");
            System.out.println("3 - Delete Column");
            System.out.println("4 - Go Back");

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> listColumns();
                case 2 -> addColumn();
                case 3 -> deleteColumn();
                case 4 -> {
                    System.out.println("Returning to main menu...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void listColumns() {
        try (var connection = ConnectionConfig.getConnection()) {
            var columnDao = new BoardColumnDao(connection);
            List<BoardColumnEntity> columns = columnDao.findByBoardId(board.getId());

            System.out.println("Columns:");
            for (var col : columns) {
                System.out.printf(" - ID %d: %s (%s) Order: %d%n", col.getId(), col.getName(), col.getKind(),
                        col.getOrder());
            }
        } catch (SQLException e) {
            System.out.println("Error listing columns: " + e.getMessage());
        }
    }

    private void addColumn() {
        try (var connection = ConnectionConfig.getConnection()) {
            var columnDao = new BoardColumnDao(connection);

            System.out.println("Enter column name:");
            String name = scanner.nextLine();

            System.out.println("Enter column kind (INITIAL, PENDING, DONE, etc):");
            String kindInput = scanner.nextLine().toUpperCase();

            BoardColumnKindEnum kind;
            try {
                kind = BoardColumnKindEnum.valueOf(kindInput);
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid kind. Defaulting to PENDING.");
                kind = BoardColumnKindEnum.PENDING;
            }

            System.out.println("Enter column order (number):");
            int order = scanner.nextInt();
            scanner.nextLine(); // consume newline

            var column = new BoardColumnEntity();
            column.setName(name);
            column.setKind(kind);
            column.setOrder(order);
            column.setBoard(board);

            columnDao.insert(column);
            connection.commit();

            System.out.println("Column added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding column: " + e.getMessage());
        }
    }

    private void deleteColumn() {
        System.out.println("Enter ID of column to delete:");
        int columnId = scanner.nextInt();
        scanner.nextLine();

        try (var connection = ConnectionConfig.getConnection()) {
            var columnDao = new BoardColumnDao(connection);
            boolean deleted = columnDao.delete((long) columnId);
            if (deleted) {
                connection.commit();
                System.out.println("Column deleted successfully!");
            } else {
                System.out.println("Column not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting column: " + e.getMessage());
        }
    }
}
