package boardManager.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.mysql.cj.jdbc.StatementImpl;
import boardManager.persistence.entity.BoardColumnEntity;

public class BoardColumnDao {
    private final Connection connection;

    public BoardColumnDao(Connection connection) { this.connection = connection; }

    public BoardColumnEntity insert(BoardColumnEntity entity) throws SQLException {
        String sql = "INSERT INTO BOARDS_COLUMNS (name, `order`, kind, board_id) VALUES (?,?,?,?)";
        try (var stmt = connection.prepareStatement(sql)) {
            int i = 1;
            stmt.setString(i++, entity.getName());
            stmt.setInt(i++, entity.getOrder());
            stmt.setString(i++, entity.getKind().name());
            stmt.setLong(i, entity.getBoard().getId());
            stmt.executeUpdate();
            if (stmt instanceof StatementImpl impl) {
                entity.setId(impl.getLastInsertID());
            }
        }
        return entity;
    }
}