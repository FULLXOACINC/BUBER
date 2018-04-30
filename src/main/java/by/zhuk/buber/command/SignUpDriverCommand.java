package by.zhuk.buber.command;

import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.receiver.SignInReceiver;
import by.zhuk.buber.receiver.SignUpReceiver;
import by.zhuk.buber.validator.SignUpDriverValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class SignUpDriverCommand implements Command {
    private static Logger logger = LogManager.getLogger(SignUpDriverCommand.class);
    private static final String CAR_NUMBER = "carNumber";
    private static final String DOCUMENT_ID = "documentId";
    private static final String CAR_MARK = "carMark";

    private static final String CAR_NUMBER_ERROR = "carNumberError";
    private static final String DOCUMENT_ID_ERROR = "documentIdError";
    private static final String CAR_MARK_ERROR = "carMarkError";
    private static final String LOGIN_NOT_EXIST_ERROR = "loginNotExistError";

    private static final String OLD_LOGIN = "oldLogin";
    private static final String OLD_CAR_NUMBER = "oldCarNumber";
    private static final String OLD_DOCUMENT_ID = "oldDocumentId";
    private static final String OLD_CAR_MARK = "oldCarMark";

    @Override
    public Router execute(HttpServletRequest request) {
        String login = request.getParameter(UserConstant.LOGIN);
        String carNumber = request.getParameter(CAR_NUMBER);
        String documentId = request.getParameter(DOCUMENT_ID);
        String carMark = request.getParameter(CAR_MARK);

        boolean signUpProblem = false;
        if (!SignUpDriverValidator.isCarNumberValid(carNumber)) {
            signUpProblem = true;
            request.setAttribute(CAR_NUMBER_ERROR, true);
        }
        if (!SignUpDriverValidator.isDocIdValid(documentId)) {
            signUpProblem = true;
            request.setAttribute(DOCUMENT_ID_ERROR, true);
        }
        if (!SignUpDriverValidator.isCarMarkValid(carMark)) {
            signUpProblem = true;
            request.setAttribute(CAR_MARK_ERROR, true);
        }

        SignInReceiver signInReceiver = new SignInReceiver();
        try {
            if (!signInReceiver.isLoginExist(login)) {
                signUpProblem = true;
                request.setAttribute(LOGIN_NOT_EXIST_ERROR, true);
            }

            if (!signUpProblem) {
                SignUpReceiver signUpReceiver = new SignUpReceiver();

                signUpReceiver.saveDriver(login, carNumber, documentId, carMark);
            } else {
                request.setAttribute(OLD_LOGIN, login);
                request.setAttribute(OLD_CAR_NUMBER, carNumber);
                request.setAttribute(OLD_DOCUMENT_ID, documentId);
                request.setAttribute(OLD_CAR_MARK, carMark);
            }
        } catch (ReceiverException e) {
            //TODO error page
            logger.catching(e);
            return new Router(TransitionType.FORWARD, PagesConstant.LOGIN_PAGE);
        }

        return new Router(TransitionType.FORWARD, PagesConstant.SING_UP_DRIVER_PAGE);
    }
}