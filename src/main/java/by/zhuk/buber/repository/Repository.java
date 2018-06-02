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

/**
 * Class Repository implements DRUD method using Specification and FindSpecification<T>
 * Model Repository :class-level
 */
public class Repository<T> {
    private Connection connection;

    /**
     * Method add entity by specification
     *
     * @throws RepositoryException throws when there are problems with the database
     * @see Specification,RepositoryException
     */
    public void add(Specification specification) throws RepositoryException {
        updateQueryExecute(specification);
    }

    /**
     * Method update entity by specification
     *
     * @throws RepositoryException throws when there are problems with the database
     * @see Specification,RepositoryException
     */
    public void update(Specification specification) throws RepositoryException {
        updateQueryExecute(specification);
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method find entities by FindSpecification<T>
     *
     * @return list of entities
     * @throws RepositoryException throws when there are problems with the database
     * @see FindSpecification,RepositoryException
     */
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
    /**
     * Method execute update query by specification
     *
     * @throws RepositoryException throws when there are problems with the database
     * @see Specification,RepositoryException,PreparedStatement
     */
    private void updateQueryExecute(Specification specification) throws RepositoryException {
        try (PreparedStatement statement = connection.prepareStatement(specification.takePrepareQuery())) {
            specification.setupPreparedStatement(statement);
            statement.executeUpdate();
        } catch (SQLException | SpecificationException e) {
            throw new RepositoryException(e);
        }
    }
}
