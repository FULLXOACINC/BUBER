package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.ErrorConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.Complaint;
import by.zhuk.buber.receiver.ComplaintReceiver;
import by.zhuk.buber.validator.IntegerValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class FindUnacceptedComplaintCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(FindUnacceptedComplaintCommand.class);

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        ComplaintReceiver complaintReceiver = new ComplaintReceiver();
        String stringIndex = request.getParameter(UserConstant.INDEX);
        int index;
        if (!IntegerValidator.isInteger(stringIndex)) {
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
            json.put(UserConstant.INDEX, index);
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ErrorConstant.ERROR, ErrorConstant.ERROR);
        }
        return json;
    }
}