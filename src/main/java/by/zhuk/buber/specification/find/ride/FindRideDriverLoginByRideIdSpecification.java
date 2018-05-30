package by.zhuk.buber.specification.find.ride;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.model.Ride;
import by.zhuk.buber.specification.find.FindSpecification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindRideDriverLoginByRideIdSpecification implements FindSpecification<Ride> {
    private static final String SELECT_DRIVER_LOGIN_BY_ID = "SELECT ride_driver_login FROM buber_db.ride WHERE ride_index=?";
    private int rideId;

    public FindRideDriverLoginByRideIdSpecification(int rideId) {
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
                Driver driver = new Driver();
                driver.setLogin(resultSet.getString(1));
                ride.setDriver(driver);
                rides.add(ride);
            }
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
        return rides;
    }
}