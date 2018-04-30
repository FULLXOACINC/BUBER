package by.zhuk.buber.repository;

import by.zhuk.buber.connectionpool.ConnectionPool;
import by.zhuk.buber.exeption.RepositoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class RepositoryTransaction {
    private static Logger logger = LogManager.getLogger(RepositoryTransaction.class);

    private Connection connection;

    public RepositoryTransaction() {
        connection = ConnectionPool.getInstance().takeConnection();
    }

    public void startTransaction(AbstractRepository repository, AbstractRepository... repositories) throws RepositoryException {

        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        repository.setConnection(connection);
        for (AbstractRepository transactionRepositor : repositories) {
            transactionRepositor.setConnection(connection);
        }

    }

    public void endTransaction() throws RepositoryException {
        try {
            connection.setAutoCommit(true);
            connection.close();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    public void commit() throws RepositoryException {
        try {
            connection.commit();

        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
    public void rollBack() {
        try {
            connection.rollback();
            connection.close();
        } catch (SQLException e) {
            logger.catching(e);
        }
    }
}