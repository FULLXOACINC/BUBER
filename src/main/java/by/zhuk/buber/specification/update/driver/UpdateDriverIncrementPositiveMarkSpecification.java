package by.zhuk.buber.specification.update.driver;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.specification.Specification;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Specification update driver positive mark : driver_positive_mark=driver_positive_mark + 1
 */
public class UpdateDriverIncrementPositiveMarkSpecification implements Specification {
    private static final String UPDATE_DRIVER_INCREMENT_POSITIVE_MARK = "UPDATE buber_db.driver SET driver_positive_mark=driver_positive_mark + 1 WHERE driver_login=?;";
    private String login;


    public UpdateDriverIncrementPositiveMarkSpecification(String login) {
        this.login = login;
    }

    @Override
    public String takePrepareQuery() {
        return UPDATE_DRIVER_INCREMENT_POSITIVE_MARK;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {
        try {
            statement.setString(1, login);
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }

    }
}