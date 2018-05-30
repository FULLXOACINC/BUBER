package by.zhuk.buber.specification.add;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.specification.Specification;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddComplaintSpecification implements Specification {
    private static final String INSERT_COMPLAINT = "INSERT INTO buber_db.complaint (complaint_person_login, complaint_ride_id, complaint_text) VALUES (?, ?, ?)";
    private String login;
    private int intRideId;
    private String complaint;

    public AddComplaintSpecification(String login, int intRideId, String complaint) {
        this.login = login;
        this.intRideId = intRideId;
        this.complaint = complaint;
    }

    @Override
    public String takePrepareQuery() {
        return INSERT_COMPLAINT;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {
        try {
            statement.setString(1, login);
            statement.setInt(2, intRideId);
            statement.setString(3, complaint);
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
    }

}