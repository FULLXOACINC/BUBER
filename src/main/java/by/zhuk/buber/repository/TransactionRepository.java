package by.zhuk.buber.repository;

import java.sql.Connection;

public interface TransactionRepository<T> extends Repository<T> {
    void setConnection(Connection connection);
}