package boardManager.persistence.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionConfig {

    private static final String URL = "jdbc:mysql://localhost:3306/board";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private ConnectionConfig() {} // Prevent instantiation

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        conn.setAutoCommit(false);
        return conn;
    }
}
