package by.zhuk.buber.repository;

import by.zhuk.buber.exeption.RepositoryException;
import by.zhuk.buber.model.CarMark;
import by.zhuk.buber.specification.Specification;

import java.sql.Connection;
import java.util.List;

public class CarMarkRepository implements TransactionRepository<CarMark> {
    private Connection connection;

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(CarMark entity) throws RepositoryException {

    }

    @Override
    public void update(CarMark entity) throws RepositoryException {

    }

    @Override
    public void delete(CarMark entity) throws RepositoryException {

    }

    @Override
    public List<CarMark> find(Specification specification) throws RepositoryException {
        return null;
    }
}