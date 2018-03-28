package by.zhuk.buber.repository;

import by.zhuk.buber.exeption.RepositoryException;
import by.zhuk.buber.specification.Specification;

import java.util.List;

public interface Repository<T> {

    void add(T entity) throws RepositoryException;

    void update(T entity) throws RepositoryException;

    void delete(T entity) throws RepositoryException;

    List<T> find(Specification specification) throws RepositoryException;
}