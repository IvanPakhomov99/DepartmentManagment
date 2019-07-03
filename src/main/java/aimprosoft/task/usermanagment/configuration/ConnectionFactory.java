package aimprosoft.task.usermanagment.configuration;

import java.sql.Connection;
import java.sql.SQLException;


public interface ConnectionFactory {
    Connection createConnection() throws SQLException;

    default void closeQuietly(Connection connection) throws SQLException {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    default void rollbackQuietly(Connection connection) throws SQLException {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
