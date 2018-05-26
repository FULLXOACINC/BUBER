package by.zhuk.buber.specification.add;

import by.zhuk.buber.exeption.SpecificationException;
import by.zhuk.buber.specification.Specification;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddRideSpecification implements Specification {
    private static final String INSERT_RIDE = "INSERT INTO buber_db.ride (ride_driver_login, ride_passenger_login, ride_start_lat_coordinate, ride_start_lng_coordinate, ride_end_lat_coordinate, ride_end_lng_coordinate, ride_price,  ride_is_passenger_start_accept, ride_is_driver_start_accept, ride_is_passenger_end_accept, ride_is_driver_end_accept) VALUES ( ?, ?, ?, ?, ?, ?, ?, '0', '0', '0', '0')";
    private String driver;
    private String passenger;
    private float startLat;
    private float startLng;
    private float endLat;
    private float endLng;
    private BigDecimal price;

    public AddRideSpecification(String driver, String passenger, float startLat, float startLng, float endLat, float endLng, BigDecimal price) {
        this.driver = driver;
        this.passenger = passenger;
        this.startLat = startLat;
        this.startLng = startLng;
        this.endLat = endLat;
        this.endLng = endLng;
        this.price = price;
    }

    @Override
    public String takePrepareQuery() {
        return INSERT_RIDE;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {
        try {
            statement.setString(1, driver);
            statement.setString(2, passenger);
            statement.setFloat(3, startLat);
            statement.setFloat(4, startLng);
            statement.setFloat(5, endLat);
            statement.setFloat(6, endLng);
            statement.setBigDecimal(7, price);

        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
    }
}