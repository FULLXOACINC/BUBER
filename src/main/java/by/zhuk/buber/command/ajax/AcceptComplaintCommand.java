package by.zhuk.buber.command.ajax;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.receiver.ComplaintReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

public class AcceptComplaintCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(FindUsersCommand.class);
    private static final String ID = "id";

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String complaintId = request.getParameter(ID);
        ComplaintReceiver complaintReceiver = new ComplaintReceiver();
        try {
            complaintReceiver.acceptComplaint(complaintId);
            json.put(ALL_CORRECT, ALL_CORRECT);
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ERROR, e.getMessage());
        }
        return json;
    }
}