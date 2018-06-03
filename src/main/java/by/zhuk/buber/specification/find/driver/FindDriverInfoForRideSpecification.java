package by.zhuk.buber.specification.find.driver;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.model.CarMark;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.specification.find.FindSpecification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * FindSpecification<Driver> find driver_car_number,car_mark_id,car_mark_name,user_first_name,user_last_name,user_phone_number by driver_login inner join car_mark,driver,user
 */
public class FindDriverInfoForRideSpecification implements FindSpecification<Driver> {
    private static final String SELECT_RIDE_INFO_DRIVER = "SELECT driver_car_number,car_mark_id,car_mark_name,user_first_name,user_last_name,user_phone_number FROM buber_db.driver INNER JOIN buber_db.car_mark ON buber_db.car_mark.car_mark_id=buber_db.driver.driver_car_mark INNER JOIN buber_db.user ON buber_db.driver.driver_login=buber_db.user.user_login WHERE driver_login=?";

    private String login;

    public FindDriverInfoForRideSpecification(String login) {
        this.login = login;
    }

    @Override
    public String takePrepareQuery() {
        return SELECT_RIDE_INFO_DRIVER;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {
        try {
            statement.setString(1, login);
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
                driver.setLogin(login);
                driver.setCarNumber(resultSet.getString(1));
                CarMark carMark = new CarMark();
                carMark.setIndex(resultSet.getInt(2));
                carMark.setMarkName(resultSet.getString(3));
                driver.setCarMark(carMark);

                driver.setFirstName(resultSet.getString(4));
                driver.setLastName(resultSet.getString(5));
                driver.setPhoneNumber(resultSet.getString(6));

                drivers.add(driver);
            }
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
        return drivers;
    }
}