package by.zhuk.buber.specification.update.ride;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.specification.Specification;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Specification update ride driver accept start by ride id: ride_is_driver_start_accept=1
 */
public class UpdateRideDriverAcceptStartSpecification implements Specification {
    private static final String UPDATE_RIDE_BY_RIDE_INDEX = "UPDATE buber_db.ride SET ride_is_driver_start_accept=1 WHERE ride_index=?";
    private int rideId;

    public UpdateRideDriverAcceptStartSpecification(int rideId) {
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