package by.zhuk.buber.receiver;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.exeption.RepositoryException;
import by.zhuk.buber.model.Complaint;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.repository.RepositoryController;
import by.zhuk.buber.specification.Specification;
import by.zhuk.buber.specification.find.FindSpecification;
import by.zhuk.buber.specification.find.complaint.FindUnacceptedComplaintsSpecification;
import by.zhuk.buber.specification.update.UpdateComplaintAcceptSpecification;

import java.util.List;

public class ComplaintReceiver {

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
}