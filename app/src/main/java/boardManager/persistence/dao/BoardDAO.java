package boardManager.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import boardManager.persistence.entity.BoardEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardDAO {

    private final Connection connection;

    public BoardEntity insert(BoardEntity entity) throws SQLException {
        String sql = "INSERT INTO BOARDS (name) VALUES (?)";
        try (var stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getName());
            stmt.executeUpdate();

            var rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getLong(1));
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

    public List<BoardEntity> findAll() throws SQLException {
    List<BoardEntity> boards = new ArrayList<>();
    String sql = "SELECT id, name FROM BOARDS ORDER BY id";
    try (var stmt = connection.prepareStatement(sql);
         var rs = stmt.executeQuery()) {
        while (rs.next()) {
            var board = new BoardEntity();
            board.setId(rs.getLong("id"));
            board.setName(rs.getString("name"));
            boards.add(board);
        }
    }
    return boards;
}
}
