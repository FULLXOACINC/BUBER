package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.DriverConstant;
import by.zhuk.buber.constant.ErrorConstant;
import by.zhuk.buber.constant.GeoConstant;
import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.receiver.DriverReceiver;
import by.zhuk.buber.validator.CoordinateValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class FindSuitableDriversCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(FindSuitableDriversCommand.class);
    private static final String POSITIVE_MARK = "positiveMark";
    private static final String NEGATIVE_MARK = "negativeMark";


    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        String lng = request.getParameter(GeoConstant.LNG);
        String lat = request.getParameter(GeoConstant.LAT);

        if (!CoordinateValidator.isCoordinateValid(lng) || !CoordinateValidator.isCoordinateValid(lat)) {
            json.put(GeoConstant.NOT_VALID_COORDINATE, GeoConstant.NOT_VALID_COORDINATE);
            return json;
        }

        DriverReceiver driverReceiver = new DriverReceiver();
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute(UserConstant.LOGIN);
        try {
            List<Driver> drivers = driverReceiver.findSuitableDrivers(Float.parseFloat(lat), Float.parseFloat(lng), login);
            JSONArray array = new JSONArray();
            for (Driver driver : drivers) {
                JSONObject object = new JSONObject();
                object.put(UserConstant.FIRST_NAME, driver.getFirstName());
                object.put(UserConstant.LAST_NAME, driver.getLastName());
                object.put(GeoConstant.LAT, driver.getCurrentLatCoordinate());
                object.put(GeoConstant.LNG, driver.getCurrentLngCoordinate());
                object.put(UserConstant.LOGIN, driver.getLogin());
                object.put(POSITIVE_MARK, driver.getPositiveMark());
                object.put(NEGATIVE_MARK, driver.getNegativeMark());
                object.put(DriverConstant.TARIFF, driver.getTariff());
                object.put(DriverConstant.CAR_NUMBER, driver.getCarNumber());
                object.put(DriverConstant.CAR_MARK, driver.getCarMark().getMarkName());
                array.put(object);
            }
            json.put(DriverConstant.DRIVERS, array);

        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ErrorConstant.ERROR, ErrorConstant.ERROR);
        }
        return json;
    }
}