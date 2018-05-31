package by.zhuk.buber.exception;

import java.sql.SQLException;

public class SpecificationException extends Exception {
    public SpecificationException(SQLException e) {
        super(e);
    }
}