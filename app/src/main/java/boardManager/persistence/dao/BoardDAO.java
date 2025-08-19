package boardManager.persistence.dao;

import java.sql.*;
import java.util.Optional;
import boardManager.persistence.entity.BoardEntity;
import com.mysql.cj.jdbc.StatementImpl;

public class BoardDAO {
    private final Connection connection;

    public BoardDAO(Connection connection) { this.connection = connection; }

    public BoardEntity insert(BoardEntity entity) throws SQLException {
        String sql = "INSERT INTO BOARDS (name) VALUES (?)";
        try (var stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getName());
            stmt.executeUpdate();
            if (stmt instanceof StatementImpl impl) {
                entity.setId(impl.getLastInsertID());
            }
        }
        return entity;
    }

    public boolean delete(Long id) throws SQLException {
        String sql = "DELETE FROM BOARDS WHERE id = ?";
        try (var stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public Optional<BoardEntity> findById(Long id) throws SQLException {
        String sql = "SELECT id, name FROM BOARDS WHERE id = ?";
        try (var stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeQuery();
            var rs = stmt.getResultSet();
            if (rs.next()) {
                BoardEntity board = new BoardEntity();
                board.setId(rs.getLong("id"));
                board.setName(rs.getString("name"));
                return Optional.of(board);
            }
        }
        return Optional.empty();
    }
}