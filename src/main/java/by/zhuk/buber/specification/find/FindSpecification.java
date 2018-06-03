package by.zhuk.buber.specification.find;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.specification.Specification;

import java.sql.ResultSet;
import java.util.List;
/**
 * Class extends Specification, and add method to create entities form ResultSet
 * @see Specification
 */
public interface FindSpecification<T> extends Specification {
    List<T> createEntities(ResultSet resultSet) throws SpecificationException;
}