package by.zhuk.buber.specification;

import by.zhuk.buber.exception.SpecificationException;

import java.sql.PreparedStatement;
/**
 * Interface Specification include all helpful information to execute and,delete and update query
 * @author Alex Zhuk
 */
public interface Specification {

    /**
     * take prepare query to PreparedStatement
     */
    String takePrepareQuery();
    /**
     * setup PreparedStatement
     */
    void setupPreparedStatement(PreparedStatement statement) throws SpecificationException;

}