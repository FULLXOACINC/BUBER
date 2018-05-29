package by.zhuk.buber.command;

import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.model.Complaint;
import by.zhuk.buber.receiver.ComplaintReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ViewUnacceptedComplaintsCommand implements Command {
    private static Logger logger = LogManager.getLogger(ViewUnacceptedComplaintsCommand.class);
    private static final String COMPLAINTS = "complaint";

    @Override
    public Router execute(HttpServletRequest request) {
        ComplaintReceiver complaintReceiver = new ComplaintReceiver();
        try {
            List<Complaint> complaints = complaintReceiver.findUnacceptedComplaints();
            request.setAttribute(COMPLAINTS, complaints);
            return new Router(TransitionType.FORWARD, PagesConstant.VIEW_COMPLAINTS_PAGE);
        } catch (ReceiverException e) {
            logger.catching(e);
            return new Router(TransitionType.REDIRECT, PagesConstant.ERROR_PAGE);
        }
    }
}