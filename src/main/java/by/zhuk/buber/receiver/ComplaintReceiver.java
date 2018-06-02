package by.zhuk.buber.receiver;

import by.zhuk.buber.constant.RideConstant;
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

/**
 * Class used to work with complaints
 */
public class ComplaintReceiver {
    private static final String COMPLAINT_ID = "complaintId";
    private static final String COMPLAINT_PERSON_LOGIN = "complaintPersonLogin";
    private static final String COMPLAINT_TEXT = "complaintText";
    private static final String COMPLAINTS_EMPTY = "complaintsEmpty";

    /**
     * Method find unaccepted complaints
     *
     * @return unaccepted complaints list
     * @throws ReceiverException throws when there are problems with the database
     * @see Specification,Finder,FindUnacceptedComplaintsSpecification
     */
    public List<Complaint> findUnacceptedComplaints() throws ReceiverException {
        FindSpecification<Complaint> specification = new FindUnacceptedComplaintsSpecification();
        Finder<Complaint> complaintFinder = new Finder<>();
        return complaintFinder.findBySpecification(specification);
    }

    /**
     * Method to accepted complaints by complaints id
     *
     * @throws ReceiverException throws when there are problems with the database
     * @see Specification,Repository,RepositoryController,UpdateComplaintAcceptSpecification
     */
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

    /**
     * Method check is exist complaint on person
     *
     * @param login  person login
     * @param rideId ride id
     * @return unaccepted complaints list
     * @throws ReceiverException throws when there are problems with the database
     * @see Specification,Finder,FindComplaintByRideIdSpecification
     */
    public boolean isPersonComplaintExist(int rideId, String login) throws ReceiverException {
        FindSpecification<Complaint> specification = new FindComplaintByRideIdSpecification(login, rideId);
        Finder<Complaint> complaintFinder = new Finder<>();
        List<Complaint> complaints = complaintFinder.findBySpecification(specification);
        return !complaints.isEmpty();
    }

    /**
     * Method create complaint to login
     *
     * @param complaint complaint text
     * @param rideId    ride id ,where the complaint was created
     * @throws ReceiverException throws when there are problems with the database
     * @see Specification,Finder,FindUnacceptedComplaintsSpecification
     */
    public void createComplaint(String login, int rideId, String complaint) throws ReceiverException {
        Specification complaintAddSpecification = new AddComplaintSpecification(login, rideId, complaint);
        Adder adder = new Adder();
        adder.addBySpecification(complaintAddSpecification);
    }

    /**
     * Create complaint json
     *
     * @return json that include complaint id,complaint person login,complaint text,ride id,
     * or complaint empty mar if complaints is empty
     * @see JSONObject
     */
    public JSONObject createComplaintJSON(List<Complaint> complaints, int index) {
        JSONObject json = new JSONObject();
        if (complaints.isEmpty()) {
            json.put(COMPLAINTS_EMPTY, COMPLAINTS_EMPTY);
        } else {
            Complaint complaint = complaints.get(index);
            json.put(COMPLAINT_ID, complaint.getComplaintId());
            json.put(COMPLAINT_PERSON_LOGIN, complaint.getComplaintPersonLogin());
            json.put(COMPLAINT_TEXT, complaint.getComplaintText());
            json.put(RideConstant.RIDE_ID, complaint.getRideId());
        }
        return json;
    }
}