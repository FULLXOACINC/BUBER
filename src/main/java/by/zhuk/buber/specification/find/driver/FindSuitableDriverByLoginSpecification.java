package by.zhuk.buber.specification.find.driver;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.specification.find.FindSpecification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindSuitableDriverByLoginSpecification implements FindSpecification<Driver> {
    private static final String SELECT_SUITABLE_DRIVER = "SELECT driver_login FROM buber_db.driver WHERE SQRT(POW(driver_current_lat_coordinate- ?,2) + POW(driver_current_lng_coordinate- ?,2))<10 AND driver_is_working=1 AND driver_login=? ORDER BY driver_positive_mark DESC LIMIT 3";
    private float lat;
    private float lng;
    private String driver;

    public FindSuitableDriverByLoginSpecification(float lat, float lng, String driver) {
        this.lat = lat;
        this.lng = lng;
        this.driver = driver;
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
            statement.setString(3, driver);

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
                drivers.add(driver);
            }
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
        return drivers;
    }
}