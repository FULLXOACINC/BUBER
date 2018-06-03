package by.zhuk.buber.specification.update.driver;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.specification.Specification;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Specification update driver coordinate : driver_current_lat_coordinate, driver_current_lng_coordinate
 */
public class UpdateDriverCoordinateSpecification implements Specification {
    private static final String UPDATE_DRIVER_COORDINATE_BY_LOGIN = "UPDATE buber_db.driver SET driver_current_lat_coordinate=?, driver_current_lng_coordinate=? WHERE driver_login=?";
    private float lat;
    private float lng;
    private String driverLogin;


    public UpdateDriverCoordinateSpecification(float lat, float lng, String driverLogin) {
        this.lat = lat;
        this.lng = lng;
        this.driverLogin = driverLogin;
    }

    @Override
    public String takePrepareQuery() {
        return UPDATE_DRIVER_COORDINATE_BY_LOGIN;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {
        try {
            statement.setFloat(1, lat);
            statement.setFloat(2, lng);
            statement.setString(3, driverLogin);

        } catch (SQLException e) {
            throw new SpecificationException(e);
        }

    }
}