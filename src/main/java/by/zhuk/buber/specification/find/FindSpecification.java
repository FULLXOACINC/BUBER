package by.zhuk.buber.specification.find;

import by.zhuk.buber.exeption.SpecificationException;
import by.zhuk.buber.specification.Specification;

import java.sql.ResultSet;
import java.util.List;

public interface FindSpecification<T> extends Specification {
    List<T> createEntities(ResultSet resultSet) throws SpecificationException;
}