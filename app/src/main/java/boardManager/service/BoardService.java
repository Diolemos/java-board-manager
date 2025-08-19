package boardManager.service;

import java.sql.Connection;
import java.sql.SQLException;

import boardManager.persistence.dao.BoardColumnDao;
import boardManager.persistence.dao.BoardDAO;
import boardManager.persistence.entity.BoardColumnEntity;
import boardManager.persistence.entity.BoardEntity;

public class BoardService {
    private final Connection connection;

    public BoardService(Connection connection) { this.connection = connection; }

    public BoardEntity insert(BoardEntity entity) throws SQLException {
        var boardDao = new BoardDao(connection);
        var columnDao = new BoardColumnDao(connection);
        try {
            boardDao.insert(entity);
            for (BoardColumnEntity col : entity.getBoardColumns()) {
                col.setBoard(entity);
                columnDao.insert(col);
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
        return entity;
    }

    public boolean delete(Long id) throws SQLException {
        var boardDao = new BoardDao(connection);
        try {
            if (!boardDao.findById(id).isPresent()) return false;
            boolean deleted = boardDao.delete(id);
            connection.commit();
            return deleted;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }
}