package by.zhuk.buber.repository;

import by.zhuk.buber.specification.Specification;

import java.util.List;

public interface Repository<T> {

    void add(T entity);

    void update(T entity);

    void delete(T entity);

    List<T> find(Specification specification);
}