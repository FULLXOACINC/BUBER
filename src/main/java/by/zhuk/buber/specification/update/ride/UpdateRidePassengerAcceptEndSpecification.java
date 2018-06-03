package by.zhuk.buber.specification.update.ride;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.specification.Specification;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Specification update ride passenger accept end by ride id: ride_is_passenger_end_accept=1
 */
public class UpdateRidePassengerAcceptEndSpecification implements Specification {
    private static final String UPDATE_RIDE_BY_RIDE_INDEX = "UPDATE buber_db.ride SET ride_is_passenger_end_accept=1 WHERE ride_index=?";
    private int rideId;

    public UpdateRidePassengerAcceptEndSpecification(int rideId) {
        this.rideId = rideId;
    }

    @Override
    public String takePrepareQuery() {
        return UPDATE_RIDE_BY_RIDE_INDEX;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {
        try {
            statement.setInt(1, rideId);
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }

    }
}