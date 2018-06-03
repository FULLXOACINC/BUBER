package by.zhuk.buber.specification.update.driver;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.specification.Specification;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Specification update driver info : driver_is_working= isWorking
 */
public class UpdateDriverIsWorkingSpecification implements Specification {
    private static final String UPDATE_IS_WORKING = "UPDATE buber_db.driver SET driver_is_working=? WHERE driver_login=?";
    private boolean isWorking;
    private String driver;

    public UpdateDriverIsWorkingSpecification(boolean isWorking, String driver) {
        this.isWorking = isWorking;
        this.driver = driver;
    }

    @Override
    public String takePrepareQuery() {
        return UPDATE_IS_WORKING;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {
        try {
            int isWork = isWorking ? 1 : 0;
            statement.setInt(1, isWork);
            statement.setString(2, driver);
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }

    }
}