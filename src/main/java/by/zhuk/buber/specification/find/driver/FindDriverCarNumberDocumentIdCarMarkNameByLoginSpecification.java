package by.zhuk.buber.specification.find.driver;

import by.zhuk.buber.exeption.SpecificationException;
import by.zhuk.buber.model.CarMark;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.specification.find.FindSpecification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindDriverCarNumberDocumentIdCarMarkNameByLoginSpecification implements FindSpecification<Driver> {
    private static final String SELECT_BY_DRIVER_LOGIN = "SELECT driver_login,driver_car_number,driver_document_id,car_mark_name FROM buber_db.driver INNER JOIN buber_db.car_mark ON buber_db.car_mark.car_mark_id=buber_db.driver.driver_car_mark WHERE driver_login=?";
    private String login;

    public FindDriverCarNumberDocumentIdCarMarkNameByLoginSpecification(String login) {
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
                carMark.setMarkName(resultSet.getString(4));
                driver.setCarMark(carMark);
                drivers.add(driver);
            }
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
        return drivers;
    }
}