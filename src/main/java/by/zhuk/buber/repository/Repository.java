package by.zhuk.buber.repository;

import by.zhuk.buber.exception.RepositoryException;
import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.specification.Specification;
import by.zhuk.buber.specification.find.FindSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Repository<T> {
    private Connection connection;

    public void add(Specification specification) throws RepositoryException {
        updateQueryExecute(specification);
    }

    public void update(Specification specification) throws RepositoryException {
        updateQueryExecute(specification);
    }

    public void delete(Specification specification) throws RepositoryException {
        updateQueryExecute(specification);
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public List<T> find(FindSpecification<T> specification) throws RepositoryException {
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

    private void updateQueryExecute(Specification specification) throws RepositoryException {
        try (PreparedStatement statement = connection.prepareStatement(specification.takePrepareQuery())) {
            specification.setupPreparedStatement(statement);
            statement.executeUpdate();
        } catch (SQLException | SpecificationException e) {
            throw new RepositoryException(e);
        }
    }
}
