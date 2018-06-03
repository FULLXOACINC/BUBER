package by.zhuk.buber.exception;

import java.sql.SQLException;
/**
 * Class Exception created on specification
 */
public class SpecificationException extends Exception {
    public SpecificationException(SQLException e) {
        super(e);
    }
}