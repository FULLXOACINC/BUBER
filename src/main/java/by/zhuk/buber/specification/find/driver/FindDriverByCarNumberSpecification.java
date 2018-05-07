package by.zhuk.buber.specification.find.driver;

import by.zhuk.buber.exeption.SpecificationException;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.specification.find.FindSpecification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindDriverByCarNumberSpecification implements FindSpecification<Driver> {
    private static final String SELECT_BY_DRIVER_CAR_NUMBER = "SELECT driver_car_number FROM buber_db.driver WHERE driver_car_number=?";
    private String carNumber;

    public FindDriverByCarNumberSpecification(String carNumber) {
        this.carNumber = carNumber;
    }

    @Override
    public String takePrepareQuery() {
        return SELECT_BY_DRIVER_CAR_NUMBER;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {
        try {
            statement.setString(1, carNumber);
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
                driver.setCarNumber(resultSet.getString(1));
                drivers.add(driver);
            }
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
        return drivers;
    }
}