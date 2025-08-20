package boardManager.persistence.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE) // Prevent instantiation
public final class ConnectionConfig {

    private static final String URL = "jdbc:mysql://localhost:3306/board";
    private static final String USER = "root";
    private static final String PASSWORD = "root"; // Use env variables in real apps

    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASSWORD);
        Connection conn = DriverManager.getConnection(URL, props);
       conn.setAutoCommit(false);
       return conn;

    }
}
