package by.zhuk.buber.exeption;

import java.sql.SQLException;

public class RepositoryException extends Exception {
    public RepositoryException(SQLException e) {
    }
}