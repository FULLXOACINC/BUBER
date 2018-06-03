package by.zhuk.buber.specification.add;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.specification.Specification;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Specification how to add to database car mark
 */
public class AddCarMarkSpecification implements Specification {
    private static final String INSERT_CAR_MARK = "INSERT INTO buber_db.car_mark (car_mark_name) VALUES (?);";
    private String carMarkName;

    public AddCarMarkSpecification(String carMarkName) {
        this.carMarkName = carMarkName;
    }

    @Override
    public String takePrepareQuery() {
        return INSERT_CAR_MARK;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {
        try {
            statement.setString(1, carMarkName);
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
    }
}