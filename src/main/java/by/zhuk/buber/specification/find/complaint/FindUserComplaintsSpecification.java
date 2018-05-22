package by.zhuk.buber.specification.find.complaint;

import by.zhuk.buber.exeption.SpecificationException;
import by.zhuk.buber.model.Complaint;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.specification.find.FindSpecification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindUserComplaintsSpecification implements FindSpecification<Complaint> {
    private static final String SELECT_COMPLAINTS_BY_LOGIN = "SELECT complaint_id,complaint_raid_id,complaint_text,complaint_is_accept FROM buber_db.complaint WHERE complaint_person_login=?";
    private String login;
    public FindUserComplaintsSpecification(String login) {
        this.login=login;
    }

    @Override
    public String takePrepareQuery() {
        return SELECT_COMPLAINTS_BY_LOGIN;
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
    public List<Complaint> createEntities(ResultSet resultSet) throws SpecificationException {
        List<Complaint> complaints = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Complaint complaint = new Complaint();
                complaint.setComplaintId(resultSet.getInt(1));
                complaint.setComplaintPersonLogin(login);
                complaint.setRaidId(resultSet.getInt(2));
                complaint.setComplaintText(resultSet.getString(3));
                complaint.setAccept(resultSet.getBoolean(4));
                complaints.add(complaint);
            }
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
        return complaints;
    }

}