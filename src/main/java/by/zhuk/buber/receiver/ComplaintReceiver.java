package by.zhuk.buber.receiver;

import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.exception.RepositoryException;
import by.zhuk.buber.model.Complaint;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.repository.RepositoryController;
import by.zhuk.buber.specification.Specification;
import by.zhuk.buber.specification.add.AddComplaintSpecification;
import by.zhuk.buber.specification.find.FindSpecification;
import by.zhuk.buber.specification.find.complaint.FindComplaintByRideIdSpecification;
import by.zhuk.buber.specification.find.complaint.FindUnacceptedComplaintsSpecification;
import by.zhuk.buber.specification.update.complaint.UpdateComplaintAcceptSpecification;
import org.json.JSONObject;

import java.util.List;

public class ComplaintReceiver {
    private static final String COMPLAINT_ID = "complaintId";
    private static final String COMPLAINT_PERSON_LOGIN = "complaintPersonLogin";
    private static final String COMPLAINT_TEXT = "complaintText";
    private static final String RIDE_ID = "rideId";
    private static final String COMPLAINTS_EMPTY = "complaintsEmpty";


    public List<Complaint> findUnacceptedComplaints() throws ReceiverException {
        FindSpecification<Complaint> specification = new FindUnacceptedComplaintsSpecification();
        Finder<Complaint> complaintFinder = new Finder<>();
        return complaintFinder.findBySpecification(specification);
    }

    public void acceptComplaint(String complaintId) throws ReceiverException {
        Repository<Complaint> repository = new Repository<>();
        RepositoryController controller = new RepositoryController(repository);
        try {
            Specification updateSpecification = new UpdateComplaintAcceptSpecification(complaintId);
            repository.update(updateSpecification);
            controller.end();
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
    }

    public boolean isPassengerComplaintExist(int rideId, String login) throws ReceiverException {
        FindSpecification<Complaint> specification = new FindComplaintByRideIdSpecification(login, rideId);
        Finder<Complaint> complaintFinder = new Finder<>();
        List<Complaint> complaints = complaintFinder.findBySpecification(specification);
        return !complaints.isEmpty();
    }

    public void createComplaint(String login, int intRideId, String complaint) throws ReceiverException {
        Specification complaintAddSpecification = new AddComplaintSpecification(login, intRideId, complaint);
        Adder<Complaint> adder = new Adder<>();
        adder.addBySpecification(complaintAddSpecification);
    }

    public JSONObject createComplaintJSON(List<Complaint> complaints, int index) {
        JSONObject json = new JSONObject();
        if (complaints.isEmpty()) {
            json.put(COMPLAINTS_EMPTY, COMPLAINTS_EMPTY);
        } else {
            Complaint complaint = complaints.get(index);
            json.put(COMPLAINT_ID, complaint.getComplaintId());
            json.put(COMPLAINT_PERSON_LOGIN, complaint.getComplaintPersonLogin());
            json.put(COMPLAINT_TEXT, complaint.getComplaintText());
            json.put(RIDE_ID, complaint.getRideId());
        }
        return json;
    }
}