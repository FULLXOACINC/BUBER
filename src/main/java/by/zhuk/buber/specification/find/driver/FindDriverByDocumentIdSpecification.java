package by.zhuk.buber.specification.find.driver;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.specification.find.FindSpecification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * FindSpecification<Driver> find driver_document_id by driver_document_id
 * Check is driver document id exist
 */
public class FindDriverByDocumentIdSpecification implements FindSpecification<Driver> {
    private static final String SELECT_BY_DRIVER_DOCUMENT_ID = "SELECT driver_document_id FROM buber_db.driver WHERE driver_document_id=?";
    private String documentId;

    public FindDriverByDocumentIdSpecification(String documentId) {
        this.documentId = documentId;
    }

    @Override
    public String takePrepareQuery() {
        return SELECT_BY_DRIVER_DOCUMENT_ID;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {
        try {
            statement.setString(1, documentId);
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
    }

    @Override
    public List<Driver> createEntities(ResultSet resultSet) throws SpecificationException {
        List<Driver> drivers = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Driver driver = new Driver();
                driver.setDocumentId(resultSet.getString(1));
                drivers.add(driver);
            }
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
        return drivers;
    }
}