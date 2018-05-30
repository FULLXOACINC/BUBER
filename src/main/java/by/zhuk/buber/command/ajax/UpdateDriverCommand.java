package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.receiver.DriverReceiver;
import by.zhuk.buber.validator.SignUpDriverValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;

public class UpdateDriverCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(SignUpDriverCommand.class);
    private static final String CAR_NUMBER = "carNumber";
    private static final String DOCUMENT_ID = "documentId";
    private static final String CAR_MARK = "carMark";
    private static final String TARIFF = "tariff";

    private static final String CAR_NUMBER_ERROR = "carNumberError";
    private static final String DOCUMENT_ID_ERROR = "documentIdError";
    private static final String CAR_MARK_ERROR = "carMarkError";
    private static final String DRIVER_NOT_EXIST_ERROR = "driverNotExistError";
    private static final String CAR_NUMBER_EXIST_ERROR = "carNumberExistError";
    private static final String DOCUMENT_ID_EXIST_ERROR = "documentIdExistError";
    private static final String TARIFF_ERROR = "tariffError";


    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();

        String login = request.getParameter(UserConstant.LOGIN);
        String carNumber = request.getParameter(CAR_NUMBER);
        String documentId = request.getParameter(DOCUMENT_ID);
        String carMark = request.getParameter(CAR_MARK);
        String tariff = request.getParameter(TARIFF);

        if (!SignUpDriverValidator.isCarNumberValid(carNumber)) {
            json.put(CAR_NUMBER_ERROR, CAR_NUMBER_ERROR);
        }
        if (!SignUpDriverValidator.isDocIdValid(documentId)) {
            json.put(DOCUMENT_ID_ERROR, DOCUMENT_ID_ERROR);
        }
        if (!SignUpDriverValidator.isCarMarkValid(carMark)) {
            json.put(CAR_MARK_ERROR, CAR_MARK_ERROR);
        }
        if (!SignUpDriverValidator.isTariffValid(tariff)) {
            json.put(TARIFF_ERROR, TARIFF_ERROR);
        }
        DriverReceiver driverReceiver = new DriverReceiver();

        try {
            if (!driverReceiver.isDriverExist(login)) {
                json.put(DRIVER_NOT_EXIST_ERROR, DRIVER_NOT_EXIST_ERROR);
            }
            Optional<Driver> driverOptional = driverReceiver.findDriverByLogin(login);
            Driver driver = driverOptional.get();
            if (driverReceiver.isCarNumberExist(carNumber) && !driver.getCarNumber().equals(carNumber)) {
                json.put(CAR_NUMBER_EXIST_ERROR, CAR_NUMBER_EXIST_ERROR);
            }
            if (driverReceiver.isDriverDocumentIdExist(documentId) && !driver.getDocumentId().equals(documentId)) {
                json.put(DOCUMENT_ID_EXIST_ERROR, DOCUMENT_ID_EXIST_ERROR);
            }
            if (json.length() == 0) {
                BigDecimal tariffBigDecimal = new BigDecimal(tariff);
                driverReceiver.updateDriver(login, carNumber, documentId, carMark, tariffBigDecimal);
                json.put(ALL_CORRECT, ALL_CORRECT);
            }
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ERROR, e.getMessage());
        }

        return json;
    }
}