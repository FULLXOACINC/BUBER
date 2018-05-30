package by.zhuk.buber.specification.find.complaint;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.model.Complaint;
import by.zhuk.buber.specification.find.FindSpecification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindUnacceptedComplaintsSpecification implements FindSpecification<Complaint> {
    private static final String SELECT_UNACCEPTED_COMPLAINTS = "SELECT complaint_id,complaint_person_login,complaint_ride_id,complaint_text FROM buber_db.complaint WHERE complaint_is_accept=0";

    @Override
    public String takePrepareQuery() {
        return SELECT_UNACCEPTED_COMPLAINTS;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) {

    }

    @Override
    public List<Complaint> createEntities(ResultSet resultSet) throws SpecificationException {
        List<Complaint> complaints = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Complaint complaint = new Complaint();
                complaint.setComplaintId(resultSet.getInt(1));
                complaint.setComplaintPersonLogin(resultSet.getString(2));
                complaint.setRideId(resultSet.getInt(3));
                complaint.setComplaintText(resultSet.getString(4));
                complaints.add(complaint);
            }
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
        return complaints;
    }
}