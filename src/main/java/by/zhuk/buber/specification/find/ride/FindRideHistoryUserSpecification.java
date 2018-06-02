package by.zhuk.buber.specification.find.ride;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.model.Coordinate;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.model.Ride;
import by.zhuk.buber.specification.find.FindSpecification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindRideHistoryUserSpecification implements FindSpecification<Ride> {
    private static final String SELECT_USER_RIDE_HISTORY = "SELECT ride_index,ride_driver_login,ride_start_lat_coordinate,ride_start_lng_coordinate,ride_end_lat_coordinate,ride_end_lng_coordinate,ride_price,user_phone_number,user_name,user_second_name FROM buber_db.ride INNER JOIN buber_db.user ON buber_db.ride.ride_driver_login=buber_db.user.user_login WHERE ride_passenger_login=? AND ride_is_driver_end_accept=1 AND ride_is_driver_start_accept=1 AND ride_is_passenger_end_accept=1 AND ride_is_passenger_end_accept=1";
    private String login;

    public FindRideHistoryUserSpecification(String login) {
        this.login = login;
    }

    @Override
    public String takePrepareQuery() {
        return SELECT_USER_RIDE_HISTORY;
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
    public List<Ride> createEntities(ResultSet resultSet) throws SpecificationException {
        List<Ride> rides = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Ride ride = new Ride();
                ride.setRideId(resultSet.getInt(1));
                Driver driver = new Driver();
                driver.setLogin(resultSet.getString(2));
                Coordinate startCoordinate = new Coordinate(resultSet.getFloat(3), resultSet.getFloat(4));
                Coordinate endCoordinate = new Coordinate(resultSet.getFloat(5), resultSet.getFloat(6));
                ride.setStartCoordinate(startCoordinate);
                ride.setEndCoordinate(endCoordinate);

                ride.setPrice(resultSet.getBigDecimal(7));
                driver.setPhoneNumber(resultSet.getString(8));
                driver.setFirstName(resultSet.getString(9));
                driver.setLastName(resultSet.getString(10));
                ride.setDriver(driver);
                rides.add(ride);
            }
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
        return rides;
    }
}
