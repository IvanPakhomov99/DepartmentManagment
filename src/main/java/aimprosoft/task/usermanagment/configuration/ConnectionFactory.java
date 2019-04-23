package aimprosoft.task.usermanagment.configuration;

import aimprosoft.task.usermanagment.exception.DataBaseException;

import java.sql.Connection;
import java.sql.SQLException;


public interface ConnectionFactory {
    Connection createConnection() throws DataBaseException, SQLException;

    void closeQuietly(Connection connection) throws SQLException;

    void rollbackQuietly(Connection connection) throws SQLException;


}
