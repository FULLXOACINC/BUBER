package by.zhuk.buber.specification.find.ride;

import by.zhuk.buber.exeption.SpecificationException;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.model.Ride;
import by.zhuk.buber.model.User;
import by.zhuk.buber.specification.find.FindSpecification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindCurrentUserRideSpecification implements FindSpecification<Ride> {
    private static final String SELECT_USER_BALANCE_BY_LOGIN = "SELECT ride_index FROM buber_db.ride WHERE ride_passenger_login=? AND(ride_is_driver_end_accept=0 OR ride_is_driver_start_accept=0 OR ride_is_passenger_end_accept=0 OR ride_is_passenger_end_accept=0)";
    private String login;

    public FindCurrentUserRideSpecification(String login) {
        this.login = login;
    }

    @Override
    public String takePrepareQuery() {
        return SELECT_USER_BALANCE_BY_LOGIN;
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
                rides.add(ride);
            }
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
        return rides;
    }


}