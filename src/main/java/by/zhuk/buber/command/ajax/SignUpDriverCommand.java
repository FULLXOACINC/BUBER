package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.DriverConstant;
import by.zhuk.buber.constant.ErrorConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.receiver.DriverReceiver;
import by.zhuk.buber.receiver.SignUpReceiver;
import by.zhuk.buber.receiver.UserReceiver;
import by.zhuk.buber.validator.DriverValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class SignUpDriverCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(SignUpDriverCommand.class);

    private static final String CAR_NUMBER_ERROR = "carNumberError";
    private static final String DOCUMENT_ID_ERROR = "documentIdError";
    private static final String CAR_MARK_ERROR = "carMarkError";
    private static final String USER_NOT_EXIST_ERROR = "userNotExistError";
    private static final String DRIVER_EXIST_ERROR = "driverExistError";
    private static final String CAR_NUMBER_EXIST_ERROR = "carNumberExistError";
    private static final String DOCUMENT_ID_EXIST_ERROR = "documentIdExistError";
    private static final String TARIFF_ERROR = "tariffError";

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();

        String login = request.getParameter(UserConstant.LOGIN);
        String carNumber = request.getParameter(DriverConstant.CAR_NUMBER);
        String documentId = request.getParameter(DriverConstant.DOCUMENT_ID);
        String carMark = request.getParameter(DriverConstant.CAR_MARK);
        String tariff = request.getParameter(DriverConstant.TARIFF);
        if (!DriverValidator.isCarNumberValid(carNumber)) {
            json.put(CAR_NUMBER_ERROR, CAR_NUMBER_ERROR);
        }
        if (!DriverValidator.isDocIdValid(documentId)) {
            json.put(DOCUMENT_ID_ERROR, DOCUMENT_ID_ERROR);
        }
        if (!DriverValidator.isCarMarkValid(carMark)) {
            json.put(CAR_MARK_ERROR, CAR_MARK_ERROR);
        }
        if (!DriverValidator.isTariffValid(tariff)) {
            json.put(TARIFF_ERROR, TARIFF_ERROR);
        }
        UserReceiver userReceiver = new UserReceiver();
        DriverReceiver driverReceiver = new DriverReceiver();
        try {
            if (!userReceiver.isUserExist(login)) {
                json.put(USER_NOT_EXIST_ERROR, USER_NOT_EXIST_ERROR);
            }
            if (driverReceiver.isDriverExist(login)) {
                json.put(DRIVER_EXIST_ERROR, DRIVER_EXIST_ERROR);
            }
            if (driverReceiver.isCarNumberExist(carNumber)) {
                json.put(CAR_NUMBER_EXIST_ERROR, CAR_NUMBER_EXIST_ERROR);
            }
            if (driverReceiver.isDriverDocumentIdExist(documentId)) {
                json.put(DOCUMENT_ID_EXIST_ERROR, DOCUMENT_ID_EXIST_ERROR);
            }
            if (json.length() == 0) {
                SignUpReceiver signUpReceiver = new SignUpReceiver();
                BigDecimal tariffBigDecimal = new BigDecimal(tariff);
                signUpReceiver.saveDriver(login, carNumber, documentId, carMark, tariffBigDecimal);
                json.put(ALL_CORRECT, ALL_CORRECT);
            }
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ErrorConstant.ERROR, ErrorConstant.ERROR);
        }

        return json;
    }
}