package boardManager.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import boardManager.persistence.entity.TaskEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TaskDAO {
    private final Connection connection;

    public TaskEntity insert(TaskEntity task) throws SQLException {
        String sql = "INSERT INTO TASKS (title, description, created_at, due_date, column_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setTimestamp(3, Timestamp.valueOf(task.getCreatedAt()));
            stmt.setTimestamp(4, task.getDueDate() != null ? Timestamp.valueOf(task.getDueDate()) : null);
            stmt.setLong(5, task.getColumn().getId());

            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    task.setId(keys.getLong(1));
                }
            }
        }
        return task;
    }

    public List<TaskEntity> findByColumnId(Long columnId) throws SQLException {
        String sql = "SELECT id, title, description, created_at, due_date FROM TASKS WHERE column_id = ? ORDER BY created_at";
        List<TaskEntity> tasks = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, columnId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TaskEntity task = new TaskEntity();
                    task.setId(rs.getLong("id"));
                    task.setTitle(rs.getString("title"));
                    task.setDescription(rs.getString("description"));
                    task.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    Timestamp due = rs.getTimestamp("due_date");
                    task.setDueDate(due != null ? due.toLocalDateTime() : null);
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }

    public boolean delete(Long id) throws SQLException {
        String sql = "DELETE FROM TASKS WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean update(TaskEntity task) throws SQLException {
        String sql = "UPDATE TASKS SET title = ?, description = ?, due_date = ?, column_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setTimestamp(3, task.getDueDate() != null ? Timestamp.valueOf(task.getDueDate()) : null);
            stmt.setLong(4, task.getColumn().getId());
            stmt.setLong(5, task.getId());

            return stmt.executeUpdate() > 0;
        }
    }
}
