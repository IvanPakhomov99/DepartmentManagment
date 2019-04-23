package aimprosoft.task.usermanagment.exception;

import java.sql.SQLException;

public class DataBaseException extends Exception{
    public DataBaseException(String s) {
        super(s);
    }

    public DataBaseException(SQLException e) {
        super(e);
    }
}
