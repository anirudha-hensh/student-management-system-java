// db/DBConnection.java
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = "jdbc:mysql://localhost:3306/attendance_db";
            String user = "root"; // update this
            String pass = "yourpassword"; // update this
            connection = DriverManager.getConnection(url, user, pass);
        }
        return connection;
    }
}

