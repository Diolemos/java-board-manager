package boardManager.ui;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import boardManager.persistence.config.ConnectionConfig;
import boardManager.persistence.dao.BoardColumnDao;
import boardManager.persistence.dao.TaskDAO;
import boardManager.persistence.entity.BoardColumnEntity;
import boardManager.persistence.entity.BoardEntity;
import boardManager.persistence.entity.TaskEntity;

public class BoardMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final BoardEntity board;

    public BoardMenu(BoardEntity board) {
        this.board = board;
    }

    public void execute() {
        int option;
        while (true) {
            System.out.printf("\nBoard: %s%n", board.getName());
            System.out.println("1 - List Columns & Tasks");
            System.out.println("2 - Add Column");
            System.out.println("3 - Delete Column");
            System.out.println("4 - Add Task");
            System.out.println("5 - Move Task");
            System.out.println("6 - Delete Task");
            System.out.println("7 - Go Back");

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> listColumnsAndTasks();
                case 2 -> addColumn();
                case 3 -> deleteColumn();
                case 4 -> addTask();
                case 5 -> moveTask();
                case 6 -> deleteTask();
                case 7 -> {
                    System.out.println("Returning to main menu...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    // -------------------- Column Operations --------------------
    private void listColumnsAndTasks() {
        try (var connection = ConnectionConfig.getConnection()) {
            var columnDao = new BoardColumnDao(connection);
            var taskDao = new TaskDAO(connection);

            List<BoardColumnEntity> columns = columnDao.findByBoardId(board.getId());
            for (var col : columns) {
                System.out.printf("\nColumn: %s (%s) Order: %d%n", col.getName(), col.getKind(), col.getOrder());
                List<TaskEntity> tasks = taskDao.findByColumnId(col.getId());
                for (var task : tasks) {
System.out.printf("  [%d] %s %s%n", 
    task.getId(), 
    task.getTitle(), 
    task.getDueDate() != null ? " - Due: " + task.getDueDate() : "  "
);;                }
            }
        } catch (SQLException e) {
            System.out.println("Error listing columns & tasks: " + e.getMessage());
        }
    }

    private void addColumn() { /* keep existing implementation */ }

    private void deleteColumn() { /* keep existing implementation */ }

    // -------------------- Task Operations --------------------
    private void addTask() {
        try (var connection = ConnectionConfig.getConnection()) {
            var columnDao = new BoardColumnDao(connection);
            var taskDao = new TaskDAO(connection);

            List<BoardColumnEntity> columns = columnDao.findByBoardId(board.getId());
            System.out.println("Select column for the new task:");
            for (int i = 0; i < columns.size(); i++) {
                System.out.printf("%d - %s%n", i + 1, columns.get(i).getName());
            }
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice < 1 || choice > columns.size()) {
                System.out.println("Invalid column selection.");
                return;
            }

            BoardColumnEntity selectedColumn = columns.get(choice - 1);

            TaskEntity task = new TaskEntity();
            task.setColumn(selectedColumn);

            System.out.println("Task title:");
            task.setTitle(scanner.nextLine());

            System.out.println("Task description:");
            task.setDescription(scanner.nextLine());

            task.setCreatedAt(LocalDateTime.now());

            taskDao.insert(task);
            connection.commit();
            System.out.println("Task added successfully.");

        } catch (SQLException e) {
            System.out.println("Error adding task: " + e.getMessage());
        }
    }

    private void moveTask() {
        System.out.println("Move Task - TODO");
    }

    private void deleteTask() {
        System.out.println("Delete Task - TODO");
    }
}
