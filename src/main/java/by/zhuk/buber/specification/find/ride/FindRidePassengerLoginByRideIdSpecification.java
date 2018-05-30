package by.zhuk.buber.specification.find.ride;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.model.Ride;
import by.zhuk.buber.model.User;
import by.zhuk.buber.specification.find.FindSpecification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindRidePassengerLoginByRideIdSpecification implements FindSpecification<Ride> {
    private static final String SELECT_DRIVER_LOGIN_BY_ID = "SELECT ride_passenger_login FROM buber_db.ride WHERE ride_index=?";
    private int rideId;

    public FindRidePassengerLoginByRideIdSpecification(int rideId) {
        this.rideId = rideId;
    }

    @Override
    public String takePrepareQuery() {
        return SELECT_DRIVER_LOGIN_BY_ID;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {
        try {
            statement.setInt(1, rideId);
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
                User passenger = new User();
                passenger.setLogin(resultSet.getString(1));
                ride.setPassenger(passenger);
                rides.add(ride);
            }
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
        return rides;
    }
}