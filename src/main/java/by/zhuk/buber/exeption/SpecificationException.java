package by.zhuk.buber.exeption;

import java.sql.SQLException;

public class SpecificationException extends Exception {
    public SpecificationException(SQLException e) {
    }
}