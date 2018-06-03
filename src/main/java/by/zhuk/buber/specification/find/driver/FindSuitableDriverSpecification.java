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
 * Find only 3 driver order by positive mark
 * FindSpecification<Driver> find driver_login,driver_car_number,car_mark_id,car_mark_name,driver_positive_mark,driver_negative_mark,driver_tariff,driver_current_lat_coordinate,driver_current_lng_coordinate,user_first_name,user_last_name by  buber_db.driver by SQRT(POW(driver_current_lat_coordinate- ?,2) + POW(driver_current_lng_coordinate-?,2))<5 AND driver_is_working=1 AND driver_login=? inner join car_mark,driver,user
 */
public class FindSuitableDriverSpecification implements FindSpecification<Driver> {
    private static final String SELECT_SUITABLE_DRIVER = "SELECT driver_login,driver_car_number,car_mark_id,car_mark_name,driver_positive_mark,driver_negative_mark,driver_tariff,driver_current_lat_coordinate,driver_current_lng_coordinate,user_first_name,user_last_name FROM buber_db.driver INNER JOIN buber_db.car_mark ON buber_db.car_mark.car_mark_id=buber_db.driver.driver_car_mark INNER JOIN buber_db.user ON buber_db.driver.driver_login=buber_db.user.user_login WHERE SQRT(POW(driver_current_lat_coordinate- ?,2) + POW(driver_current_lng_coordinate- ?,2))<5 AND driver_is_working=1 AND driver_login!=? ORDER BY driver_positive_mark DESC LIMIT 3";
    private float lat;
    private float lng;
    private String login;

    public FindSuitableDriverSpecification(float lat, float lng, String login) {
        this.lat = lat;
        this.lng = lng;
        this.login = login;
    }

    @Override
    public String takePrepareQuery() {
        return SELECT_SUITABLE_DRIVER;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {
        try {
            statement.setFloat(1, lat);
            statement.setFloat(2, lng);
            statement.setString(3, login);
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
                driver.setLogin(resultSet.getString(1));
                driver.setCarNumber(resultSet.getString(2));
                CarMark carMark = new CarMark();
                carMark.setIndex(resultSet.getInt(3));
                carMark.setMarkName(resultSet.getString(4));
                driver.setCarMark(carMark);

                driver.setPositiveMark(resultSet.getInt(5));
                driver.setNegativeMark(resultSet.getInt(6));
                driver.setTariff(resultSet.getBigDecimal(7));


                driver.setCurrentLatCoordinate(resultSet.getFloat(8));
                driver.setCurrentLngCoordinate(resultSet.getFloat(9));

                driver.setFirstName(resultSet.getString(10));
                driver.setLastName(resultSet.getString(11));


                drivers.add(driver);
            }
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
        return drivers;
    }
}