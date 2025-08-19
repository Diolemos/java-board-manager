package boardManager.persistence.dao;

import java.sql.*;
import java.util.Optional;
import boardManager.persistence.entity.BoardEntity;

public class BoardDao {

    private final Connection connection;

    public BoardDao(Connection connection) {
        this.connection = connection;
    }

    public BoardEntity insert(BoardEntity entity) throws SQLException {
        String sql = "INSERT INTO BOARDS (name) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getName());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    entity.setId(keys.getLong(1));
                }
            }
        }
        return entity;
    }

    public boolean delete(Long id) throws SQLException {
        String sql = "DELETE FROM BOARDS WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public Optional<BoardEntity> findById(Long id) throws SQLException {
        String sql = "SELECT id, name FROM BOARDS WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    BoardEntity entity = new BoardEntity();
                    entity.setId(rs.getLong("id"));
                    entity.setName(rs.getString("name"));
                    return Optional.of(entity);
                }
            }
        }
        return Optional.empty();
    }
}
