package by.zhuk.buber.receiver;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.exeption.RepositoryException;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.model.Ride;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.repository.RepositoryController;
import by.zhuk.buber.specification.Specification;
import by.zhuk.buber.specification.add.AddRideSpecification;
import by.zhuk.buber.specification.find.FindSpecification;
import by.zhuk.buber.specification.find.driver.FindSuitableDriverByLoginSpecification;
import by.zhuk.buber.specification.find.ride.FindCurrentUserRideSpecification;
import by.zhuk.buber.specification.update.UpdateDriverIsWorkingSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

public class RideReceiver {
    private static Logger logger = LogManager.getLogger(SignUpReceiver.class);
    private static final String MAIL_BUNDLE = "properties/signUpMailContent";
    private static final String HEAD = "head";
    private static final String CONTENT = "content";

    private static final String DEFAULT_HEAD = "Hello,%s %s , you are greeted by the employees of BUBER, click on the link to confirm registration, all the best.<br/><a href=\"http://localhost:8080/controller?command=sign-up-accept&hash=%s\">Go to confirm</a> ";
    private static final String DEFAULT_CONTENT = "BUBER sign up";
    private static final String PROPERTIES_EXTENSION =".properties";


    public void createRide(String driver, String passenger,float startLat, float startLng, float endLat, float endLng, BigDecimal price) throws ReceiverException {
        Repository<Ride> rideRepository = new Repository<>();
        Repository<Driver> driverRepository = new Repository<>();
        RepositoryController controller = new RepositoryController(rideRepository,driverRepository);
        try {
            controller.startTransaction();
            Specification rideAddSpecification = new AddRideSpecification(driver,passenger, startLat, startLng, endLat, endLng, price);
            rideRepository.add(rideAddSpecification);
            Specification updateDriverSpecification = new UpdateDriverIsWorkingSpecification(false,driver);
            driverRepository.update(updateDriverSpecification);
            controller.endTransaction();
        } catch (RepositoryException e) {
            controller.rollBack();
            throw new ReceiverException(e);
        }
    }

    public boolean isRideExist(String login) throws ReceiverException {
        FindSpecification<Ride> specification = new FindCurrentUserRideSpecification(login);
        Finder<Ride> finder = new Finder<>();
        List<Ride> rides = finder.findBySpecification(specification);
        return !rides.isEmpty();
    }

    public void sendUserMail(String driverFirstName,String driverLastName,){

    }
}