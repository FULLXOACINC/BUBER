package by.zhuk.buber.specification.find.ride;

import by.zhuk.buber.exeption.SpecificationException;
import by.zhuk.buber.model.Coordinate;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.model.Ride;
import by.zhuk.buber.specification.find.FindSpecification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindRideInfoPassengerSpecification implements FindSpecification<Ride> {
    private static final String SELECT_PASSENGER_RIDE_INFO = "SELECT ride_index,ride_start_lat_coordinate,ride_start_lng_coordinate,ride_end_lat_coordinate,ride_end_lng_coordinate,ride_driver_login,ride_is_driver_start_accept,ride_is_driver_end_accept,ride_is_passenger_start_accept,ride_is_passenger_end_accept FROM buber_db.ride WHERE (ride_passenger_login=?)AND(ride_is_driver_end_accept=0 OR ride_is_driver_start_accept=0 OR ride_is_passenger_end_accept=0 OR ride_is_passenger_end_accept=0)";
    private String login;

    public FindRideInfoPassengerSpecification(String login) {
        this.login = login;
    }

    @Override
    public String takePrepareQuery() {
        return SELECT_PASSENGER_RIDE_INFO;
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
                Coordinate startCoordinate = new Coordinate(resultSet.getFloat(2), resultSet.getFloat(3));
                Coordinate endCoordinate = new Coordinate(resultSet.getFloat(4), resultSet.getFloat(5));
                ride.setStartCoordinate(startCoordinate);
                ride.setEndCoordinate(endCoordinate);

                Driver driver = new Driver();
                driver.setLogin(resultSet.getString(6));
                ride.setDriver(driver);
                ride.setDriverAcceptStart(resultSet.getBoolean(7));
                ride.setDriverAcceptEnd(resultSet.getBoolean(8));
                ride.setPassengerAcceptStart(resultSet.getBoolean(9));
                ride.setPassengerAcceptEnd(resultSet.getBoolean(10));
                rides.add(ride);
            }
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
        return rides;
    }
}