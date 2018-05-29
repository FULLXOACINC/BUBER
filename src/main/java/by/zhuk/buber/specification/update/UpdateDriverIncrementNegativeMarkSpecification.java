package by.zhuk.buber.specification.update;

import by.zhuk.buber.exeption.SpecificationException;
import by.zhuk.buber.specification.Specification;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateDriverIncrementNegativeMarkSpecification implements Specification {
    private static final String UPDATE_DRIVER_INCREMENT_NEGATIVE_MARK = "UPDATE buber_db.driver SET driver_negative_mark=driver_negative_mark + 1 WHERE driver_login=?;";
    private String login;


    public UpdateDriverIncrementNegativeMarkSpecification(String login) {
        this.login = login;
    }

    @Override
    public String takePrepareQuery() {
        return UPDATE_DRIVER_INCREMENT_NEGATIVE_MARK;
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