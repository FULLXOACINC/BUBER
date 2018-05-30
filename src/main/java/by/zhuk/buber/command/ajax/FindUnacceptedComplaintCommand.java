package by.zhuk.buber.command.ajax;

import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.Complaint;
import by.zhuk.buber.receiver.ComplaintReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class FindUnacceptedComplaintCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(FindUnacceptedComplaintCommand.class);
    private static final String COMPLAINT_ID = "complaintId";
    private static final String COMPLAINT_PERSON_LOGIN = "complaintPersonLogin";
    private static final String COMPLAINT_TEXT = "complaintText";
    private static final String RIDE_ID = "rideId";


    private static final String INDEX = "index";
    private static final String COMPLAINTS_EMPTY = "complaintsEmpty";
    private static final String INTEGER_REGEX = "\\d+";

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        ComplaintReceiver complaintReceiver = new ComplaintReceiver();
        String stringIndex = request.getParameter(INDEX);
        int index;
        if (stringIndex == null || !stringIndex.matches(INTEGER_REGEX)) {
            index = 0;
        } else {
            index = Integer.parseInt(stringIndex);
        }
        try {
            List<Complaint> complaints = complaintReceiver.findUnacceptedComplaints();
            if (index > complaints.size() - 1) {
                index = complaints.size() - 1;
            }
            json = complaintReceiver.createComplaintJSON(complaints, index);
            json.put(INDEX, index);
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ERROR, ERROR);
        }
        return json;
    }
}