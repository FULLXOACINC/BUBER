package by.zhuk.buber.repository;

import by.zhuk.buber.connectionpool.ConnectionPool;
import by.zhuk.buber.exeption.RepositoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class RepositoryController {
    private static Logger logger = LogManager.getLogger(RepositoryController.class);

    private Connection connection;

    public RepositoryController(Repository repository, Repository... repositories) {
        connection = ConnectionPool.getInstance().takeConnection();
        repository.setConnection(connection);
        for (Repository transactionRepository : repositories) {
            transactionRepository.setConnection(connection);
        }
    }

    public void startTransaction() throws RepositoryException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RepositoryException(e);
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

    public void end() throws RepositoryException {
        try {
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