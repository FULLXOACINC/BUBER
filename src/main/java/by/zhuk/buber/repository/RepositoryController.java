package by.zhuk.buber.repository;

import by.zhuk.buber.connectionpool.ConnectionPool;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.exception.RepositoryException;
import by.zhuk.buber.specification.Specification;
import by.zhuk.buber.specification.update.driver.UpdateDriverProfileCoordinateSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
/**
 * Class that help Repository to take connection and transaction organization
 */
public class RepositoryController {
    private static Logger logger = LogManager.getLogger(RepositoryController.class);

    private Connection connection;

    /**
     * Extradite all repository single connection
     *
     * @see ConnectionPool
     */
    public RepositoryController(Repository repository, Repository... repositories) {
        connection = ConnectionPool.getInstance().takeConnection();
        repository.setConnection(connection);
        for (Repository transactionRepository : repositories) {
            transactionRepository.setConnection(connection);
        }
    }
    /**
     * Method start transaction
     * @throws RepositoryException throws when there are problems with the database
     * @see ConnectionPool
     */
    public void startTransaction() throws RepositoryException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }


    }
    /**
     * Method end transaction
     * @throws RepositoryException throws when there are problems with the database
     * @see ConnectionPool
     */
    public void endTransaction() throws RepositoryException {
        try {
            connection.setAutoCommit(true);
            connection.close();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
    /**
     * Method end working with Repository, most execute after working
     * Put connection to connectionPool
     * @throws RepositoryException throws when there are problems with the database
     * @see ConnectionPool
     */
    public void end() throws RepositoryException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
    /**
     * Method commit
     * @throws RepositoryException throws when there are problems with the database
     * @see ConnectionPool
     */
    public void commit() throws RepositoryException {
        try {
            connection.commit();

        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
    /**
     * Method rollBack
     * @see ConnectionPool
     */
    public void rollBack() {
        try {
            connection.rollback();
            connection.close();
        } catch (SQLException e) {
            logger.catching(e);
        }
    }
}