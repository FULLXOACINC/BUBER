package by.zhuk.buber.specification.update;

import by.zhuk.buber.exeption.SpecificationException;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.specification.Specification;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateDriverSpecification implements Specification {
    private static final String UPDATE_DRIVER_BY_LOGIN = "UPDATE buber_db.driver SET driver_car_number=? , driver_document_id=?, driver_car_mark=? WHERE driver_login=?";
    private String carNumber;
    private String documentId;
    private String login;
    private int carMark;

    public UpdateDriverSpecification(Driver driver) {
        carNumber = driver.getCarNumber();
        documentId = driver.getDocumentId();
        carMark = driver.getCarMark().getIndex();
        login = driver.getLogin();
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
            statement.setString(4, login);

        } catch (SQLException e) {
            throw new SpecificationException(e);
        }

    }
}