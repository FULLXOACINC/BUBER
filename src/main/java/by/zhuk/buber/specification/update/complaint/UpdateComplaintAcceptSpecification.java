package by.zhuk.buber.specification.update.complaint;

import by.zhuk.buber.exeption.SpecificationException;
import by.zhuk.buber.specification.Specification;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateComplaintAcceptSpecification implements Specification {
    private static final String UPDATE_ACCEPT_FIELD_BY_ID = "UPDATE buber_db.complaint SET complaint_is_accept=1 WHERE complaint_id=?";
    private String complaintId;

    public UpdateComplaintAcceptSpecification(String complaintId) {
        this.complaintId = complaintId;
    }

    @Override
    public String takePrepareQuery() {
        return UPDATE_ACCEPT_FIELD_BY_ID;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {
        try {
            statement.setString(1, complaintId);
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }

    }
}