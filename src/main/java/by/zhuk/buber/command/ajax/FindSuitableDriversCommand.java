package by.zhuk.buber.command.ajax;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.receiver.DriverReceiver;
import by.zhuk.buber.validator.CoordinateValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class FindSuitableDriversCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(FindSuitableDriversCommand.class);


    private static final String LNG = "lng";
    private static final String LAT = "lat";


    private static final String NOT_VALID_COORDINATE = "notValidCoordinate";

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String lng = request.getParameter(LNG);
        String lat = request.getParameter(LAT);

        if (!CoordinateValidator.isCoordinateValid(lng) || !CoordinateValidator.isCoordinateValid(lat)) {
            json.put(NOT_VALID_COORDINATE, NOT_VALID_COORDINATE);
            return json;
        }

        DriverReceiver driverReceiver = new DriverReceiver();

        try {
            List<Driver> drivers = driverReceiver.findSuitableDrivers(Float.parseFloat(lat), Float.parseFloat(lng));
            JSONArray array = new JSONArray();
            for (Driver driver : drivers) {
                JSONObject object = new JSONObject();
                object.put("firstName", "TEST");
                object.put("lastName", "TEST");
                object.put("lat", driver.getCurrentLatCoordinate());
                object.put("lng", driver.getCurrentLngCoordinate());
                object.put("login", driver.getLogin());
                object.put("positiveMark", driver.getPositiveMark());
                object.put("negativeMark", driver.getNegativeMark());
                object.put("tariff", driver.getTariff());
                object.put("carNumber", driver.getCarNumber());
                object.put("carMark", driver.getCarMark().getMarkName());
                array.put(object);
            }
            json.put("drivers", array);

        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ERROR, ERROR);
        }
        return json;
    }
}