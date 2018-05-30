package by.zhuk.buber.specification;

import by.zhuk.buber.exception.SpecificationException;

import java.sql.PreparedStatement;

public interface Specification {

    String takePrepareQuery();

    void setupPreparedStatement(PreparedStatement statement) throws SpecificationException;

}