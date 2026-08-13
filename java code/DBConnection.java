import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides database connections without storing credentials in source code.
 *
 * Configure the connection with environment variables:
 * DB_URL, DB_USER, DB_PASSWORD
 */
public class DBConnection {
    private static final String URL = getEnvOrDefault(
            "DB_URL", "jdbc:mysql://127.0.0.1:3306/dbproject");
    private static final String USER = getEnvOrDefault("DB_USER", "root");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    public DBConnection() {}

    private static String getEnvOrDefault(String name, String defaultValue) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? defaultValue : value;
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }

        if (PASSWORD == null || PASSWORD.isBlank()) {
            throw new SQLException(
                    "Database password is not configured. Set the DB_PASSWORD environment variable before starting the application.");
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
