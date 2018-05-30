package by.zhuk.buber.specification.update.driver;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.specification.Specification;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateDriverInfoSpecification implements Specification {
    private static final String UPDATE_DRIVER_BY_LOGIN = "UPDATE buber_db.driver SET driver_car_number=? , driver_document_id=?, driver_car_mark=?,driver_tariff=? WHERE driver_login=?";
    private String carNumber;
    private String documentId;
    private String login;
    private int carMark;
    private BigDecimal tariff;

    public UpdateDriverInfoSpecification(Driver driver) {
        carNumber = driver.getCarNumber();
        documentId = driver.getDocumentId();
        carMark = driver.getCarMark().getIndex();
        login = driver.getLogin();
        tariff = driver.getTariff();
    }

    @Override
    public String takePrepareQuery() {
        return UPDATE_DRIVER_BY_LOGIN;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {
        try {
            statement.setString(1, carNumber);
            statement.setString(2, documentId);
            statement.setInt(3, carMark);
            statement.setBigDecimal(4, tariff);
            statement.setString(5, login);

        } catch (SQLException e) {
            throw new SpecificationException(e);
        }

    }
}