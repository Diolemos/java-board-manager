package boardManager.service;

import java.sql.Connection;
import java.sql.SQLException;

import boardManager.persistence.dao.BoardDAO;
import boardManager.persistence.entity.BoardEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardService {

    private final Connection connection;

    public BoardEntity insert(BoardEntity board) throws SQLException {
        var dao = new BoardDAO(connection);
        var saved = dao.insert(board);
        connection.commit();
        return saved;
    }

    public boolean delete(Long id) throws SQLException {
        var dao = new BoardDAO(connection);
        boolean deleted = dao.delete(id);
        connection.commit();
        return deleted;
    }
}
