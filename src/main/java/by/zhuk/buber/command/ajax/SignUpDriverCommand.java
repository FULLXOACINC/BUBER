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

    private static final String DRIVER_EXIST = "driverExist";


    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();

        String login = request.getParameter(UserConstant.LOGIN);
        String carNumber = request.getParameter(DriverConstant.CAR_NUMBER);
        String documentId = request.getParameter(DriverConstant.DOCUMENT_ID);
        String carMark = request.getParameter(DriverConstant.CAR_MARK);
        String tariff = request.getParameter(DriverConstant.TARIFF);
        if (!DriverValidator.isCarNumberValid(carNumber)) {
            json.put(DriverConstant.CAR_NUMBER_NOT_VALID, DriverConstant.CAR_NUMBER_NOT_VALID);
        }
        if (!DriverValidator.isDocIdValid(documentId)) {
            json.put(DriverConstant.DOCUMENT_ID_NOT_VALID, DriverConstant.DOCUMENT_ID_NOT_VALID);
        }
        if (!DriverValidator.isCarMarkValid(carMark)) {
            json.put(DriverConstant.CAR_MARK_NOT_VALID, DriverConstant.CAR_MARK_NOT_VALID);
        }
        if (!DriverValidator.isTariffValid(tariff)) {
            json.put(DriverConstant.TARIFF_NOT_VALID, DriverConstant.TARIFF_NOT_VALID);
        }
        UserReceiver userReceiver = new UserReceiver();
        DriverReceiver driverReceiver = new DriverReceiver();
        try {
            if (!userReceiver.isLoginExist(login)) {
                json.put(UserConstant.LOGIN_NOT_EXIST, UserConstant.LOGIN_NOT_EXIST);
            }
            if (driverReceiver.isDriverExist(login)) {
                json.put(DRIVER_EXIST, DRIVER_EXIST);
            }
            if (driverReceiver.isCarNumberExist(carNumber)) {
                json.put(DriverConstant.CAR_NUMBER_EXIST, DriverConstant.CAR_NUMBER_EXIST);
            }
            if (driverReceiver.isDriverDocumentIdExist(documentId)) {
                json.put(DriverConstant.DOCUMENT_ID_EXIST, DriverConstant.DOCUMENT_ID_EXIST);
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