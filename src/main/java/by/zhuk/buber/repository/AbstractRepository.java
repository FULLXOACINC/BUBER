package by.zhuk.buber.repository;

import by.zhuk.buber.exeption.RepositoryException;
import by.zhuk.buber.exeption.SpecificationException;
import by.zhuk.buber.specification.Specification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractRepository<T> {
    private Connection connection;

    public abstract void add(T entity) throws RepositoryException;

    public abstract void update(T entity) throws RepositoryException;

    public abstract void delete(T entity) throws RepositoryException;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    Connection getConnection() {
        return connection;
    }

    public List<T> find(Specification<T> specification) throws RepositoryException {
        List<T> entities;
        try (PreparedStatement statement = connection.prepareStatement(specification.takePrepareQuery())) {
            specification.setupPreparedStatement(statement);
            ResultSet resultSet = statement.executeQuery();
            entities = specification.createEntities(resultSet);
        } catch (SQLException | SpecificationException e) {
            throw new RepositoryException(e);
        }
        return entities;
    }
}
