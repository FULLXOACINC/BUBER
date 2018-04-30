package by.zhuk.buber.specification;

import by.zhuk.buber.exeption.SpecificationException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public interface Specification<T>{

    String takePrepareQuery();

    void setupPreparedStatement(PreparedStatement statement) throws SpecificationException;

    List<T> createEntities(ResultSet resultSet) throws SpecificationException;

}