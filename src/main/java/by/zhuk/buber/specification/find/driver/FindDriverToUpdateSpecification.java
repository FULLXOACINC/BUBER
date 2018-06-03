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
 * FindSpecification<Driver> find driver_login,driver_car_number,driver_document_id,car_mark_id,car_mark_name,driver_tariff,driver_earned_money,driver_positive_mark,driver_negative_mark,driver_is_working by driver login inner join car_mark,driver
 */
public class FindDriverToUpdateSpecification implements FindSpecification<Driver> {
    private static final String SELECT_BY_DRIVER_LOGIN = "SELECT driver_login,driver_car_number,driver_document_id,car_mark_id,car_mark_name,driver_tariff,driver_earned_money,driver_positive_mark,driver_negative_mark,driver_is_working FROM buber_db.driver INNER JOIN buber_db.car_mark ON buber_db.car_mark.car_mark_id=buber_db.driver.driver_car_mark WHERE driver_login=?";
    private String login;

    public FindDriverToUpdateSpecification(String login) {
        this.login = login;
    }

    @Override
    public String takePrepareQuery() {
        return SELECT_BY_DRIVER_LOGIN;
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
                driver.setLogin(resultSet.getString(1));
                driver.setCarNumber(resultSet.getString(2));
                driver.setDocumentId(resultSet.getString(3));

                CarMark carMark = new CarMark();
                carMark.setIndex(resultSet.getInt(4));
                carMark.setMarkName(resultSet.getString(5));
                driver.setCarMark(carMark);
                driver.setTariff(resultSet.getBigDecimal(6));
                driver.setEarnedMoney(resultSet.getBigDecimal(7));

                driver.setPositiveMark(resultSet.getInt(8));
                driver.setNegativeMark(resultSet.getInt(9));
                driver.setWorking(resultSet.getBoolean(10));
                drivers.add(driver);
            }
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
        return drivers;
    }
}