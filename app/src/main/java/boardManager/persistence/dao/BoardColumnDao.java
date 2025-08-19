package boardManager.persistence.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import boardManager.persistence.entity.BoardColumnEntity;
import boardManager.persistence.entity.BoardColumnKindEnum;

public class BoardColumnDao {

    private final Connection connection;

    public BoardColumnDao(Connection connection) {
        this.connection = connection;
    }

    public BoardColumnEntity insert(BoardColumnEntity entity) throws SQLException {
        String sql = "INSERT INTO BOARDS_COLUMNS (name, `order`, kind, board_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getName());
            stmt.setInt(2, entity.getOrder());
            stmt.setString(3, entity.getKind().name());
            stmt.setLong(4, entity.getBoard().getId());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    entity.setId(keys.getLong(1));
                }
            }
        }
        return entity;
    }

    public List<BoardColumnEntity> findByBoardId(Long boardId) throws SQLException {
        String sql = "SELECT id, name, `order`, kind FROM BOARDS_COLUMNS WHERE board_id = ? ORDER BY `order`";
        List<BoardColumnEntity> list = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, boardId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    BoardColumnEntity column = new BoardColumnEntity();
                    column.setId(rs.getLong("id"));
                    column.setName(rs.getString("name"));
                    column.setOrder(rs.getInt("order"));
                    column.setKind(BoardColumnKindEnum.findByName(rs.getString("kind")));
                    list.add(column);
                }
            }
        }
        return list;
    }

    public boolean delete(Long id) throws SQLException {
        String sql = "DELETE FROM BOARDS_COLUMNS WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
