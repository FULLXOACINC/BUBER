package by.zhuk.buber.specification.add;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.specification.Specification;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Specification how to add to database driver
 */
public class AddDriverSpecification implements Specification {
    private static final String INSERT_DRIVER = "INSERT INTO buber_db.driver (driver_login, driver_car_number, driver_document_id, driver_car_mark, driver_current_lat_coordinate, driver_current_lng_coordinate, driver_is_working,driver_positive_mark, driver_negative_mark,driver_tariff) VALUES (?, ?, ? , ?, 53.963257, 27.539906, 0, 0, 0, ?);";
    private Driver driver;

    public AddDriverSpecification(Driver driver) {
        this.driver = driver;
    }

    @Override
    public String takePrepareQuery() {
        return INSERT_DRIVER;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {
        try {
            statement.setString(1, driver.getLogin());
            statement.setString(2, driver.getCarNumber());
            statement.setString(3, driver.getDocumentId());
            statement.setInt(4, driver.getCarMark().getIndex());
            statement.setBigDecimal(5, driver.getTariff());
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
    }
}