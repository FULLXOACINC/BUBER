package by.zhuk.buber.command.ajax;

import by.zhuk.buber.constant.UserConstant;
import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.Complaint;
import by.zhuk.buber.model.Coordinate;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.model.Ride;
import by.zhuk.buber.model.User;
import by.zhuk.buber.receiver.ComplaintReceiver;
import by.zhuk.buber.receiver.RideReceiver;
import by.zhuk.buber.receiver.UserReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class FindDriverRideHistoryCommand implements AJAXCommand {
    private static Logger logger = LogManager.getLogger(FindUsersCommand.class);
    private static final String INDEX = "index";
    private static final String INTEGER_REGEX = "\\d+";
    private static final String LNG = "lng";
    private static final String LAT = "lat";
    private static final String START = "start";
    private static final String END = "end";

    private static final String RIDE_ID = "rideId";
    private static final String PASSENGER_LOGIN = "passengerLogin";
    private static final String PASSENGER_PHONE_NUMBER = "passengerPhoneNumber";
    private static final String EARNED_MONEY = "earnedMoney";
    private static final String RIDE_NOT_FOUND = "rideNotFound";

    @Override
    public JSONObject execute(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        HttpSession session =request.getSession();
        String login = (String)session.getAttribute(UserConstant.LOGIN);
        RideReceiver rideReceiver = new RideReceiver();
        String stringIndex = request.getParameter(INDEX);
        int index;
        if (!(stringIndex != null && stringIndex.matches(INTEGER_REGEX))) {
            index = 0;
        } else {
            index = Integer.parseInt(stringIndex);
        }
        try {
            List<Ride> rides = rideReceiver.findDriverRideHistory(login);
            if(!rides.isEmpty()){
                if (index > rides.size() - 1) {
                    index = rides.size() - 1;
                }
                Ride ride = rides.get(index);
                Driver driver = ride.getDriver();
                User passenger = ride.getPassenger();
                json.put(RIDE_ID, ride.getRideId());
                json.put(PASSENGER_LOGIN, passenger.getLogin());
                json.put(PASSENGER_PHONE_NUMBER, passenger.getPhoneNumber());
                json.put(EARNED_MONEY, driver.getEarnedMoney());

                Coordinate startCoordinate = ride.getStartCoordinate();
                Coordinate endCoordinate = ride.getEndCoordinate();

                JSONObject startJSONObject = new JSONObject();
                startJSONObject.put(LNG, startCoordinate.getLng());
                startJSONObject.put(LAT, startCoordinate.getLat());

                JSONObject endJSONObject = new JSONObject();
                endJSONObject.put(LNG, endCoordinate.getLng());
                endJSONObject.put(LAT, endCoordinate.getLat());

                json.put(START, startJSONObject);
                json.put(END, endJSONObject);
            }else {
                json.put(RIDE_NOT_FOUND, RIDE_NOT_FOUND);
                index=0;
            }
            json.put(INDEX, index);
        } catch (ReceiverException e) {
            logger.catching(e);
            json.put(ERROR, ERROR);
        }
        return json;
    }
}