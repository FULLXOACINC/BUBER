package by.zhuk.buber.receiver;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.exeption.RepositoryException;
import by.zhuk.buber.mail.MailProperty;
import by.zhuk.buber.mail.MailThread;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.model.Ride;
import by.zhuk.buber.model.User;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.repository.RepositoryController;
import by.zhuk.buber.specification.Specification;
import by.zhuk.buber.specification.add.AddRideSpecification;
import by.zhuk.buber.specification.find.FindSpecification;
import by.zhuk.buber.specification.find.driver.FindDriverEarnedMoneySpecification;
import by.zhuk.buber.specification.find.ride.FindCurrentUserRideSpecification;
import by.zhuk.buber.specification.find.ride.FindRideDriverLoginByRideIdSpecification;
import by.zhuk.buber.specification.find.ride.FindRideInfoPassengerSpecification;
import by.zhuk.buber.specification.find.user.FindUserBalanceSpecification;
import by.zhuk.buber.specification.update.UpdateDriverEarnedMoneySpecification;
import by.zhuk.buber.specification.update.UpdateDriverIsWorkingSpecification;
import by.zhuk.buber.specification.update.UpdateRideUserAcceptEndSpecification;
import by.zhuk.buber.specification.update.UpdateRideUserAcceptStartSpecification;
import by.zhuk.buber.specification.update.UpdateUserBalanceSpecification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.math.BigDecimal;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class RideReceiver {
    private static Logger logger = LogManager.getLogger(SignUpReceiver.class);
    private static final String USER_MAIL_BUNDLE = "properties/rideUserMailContent";
    private static final String DRIVER_MAIL_BUNDLE = "properties/rideDriverMailContent";
    private static final String HEAD = "head";
    private static final String CONTENT = "content";

    private static final String DEFAULT_USER_CONTENT = "Your driver: %s %s <br/>. Machine number: %s <br/>.Mark of the machine: %s <br/>.Phone number: %s.<br/><a href=\"http://localhost:8080/controller?command=view-user-ride\">More information</a>";
    private static final String DEFAULT_USER_HEAD = "BUBER ride.";
    private static final String DEFAULT_DRIVER_CONTENT = "Your passenger: %s %s <br/>.Phone number: %s.<br/><a href=\"http://localhost:8080/controller?command=view-driver-ride\">More information</a>";
    private static final String DEFAULT_DRIVER_HEAD = "BUBER ride(driver).";
    private static final String PROPERTIES_EXTENSION = ".properties";

    private static final BigDecimal DRIVER_PROCENT=new BigDecimal("0.85");


    public void createRide(String driver, String passenger, float startLat, float startLng, float endLat, float endLng, BigDecimal price) throws ReceiverException {
        Repository<Ride> rideRepository = new Repository<>();
        Repository<Driver> driverRepository = new Repository<>();
        RepositoryController controller = new RepositoryController(rideRepository, driverRepository);
        try {
            controller.startTransaction();
            Specification rideAddSpecification = new AddRideSpecification(driver, passenger, startLat, startLng, endLat, endLng, price);
            rideRepository.add(rideAddSpecification);
            Specification updateDriverSpecification = new UpdateDriverIsWorkingSpecification(false, driver);
            driverRepository.update(updateDriverSpecification);
            controller.commit();
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

    public void sendUserMail(String userLogin, String driverFirstName, String driverLastName, String carNumber, String carMark, String phoneNumber, String lang) {
        String propsPath = this.getClass().getClassLoader().getResource(".").getPath();
        Locale locale = new Locale(lang);
        File file = new File(propsPath, USER_MAIL_BUNDLE + PROPERTIES_EXTENSION);
        String head;
        String content;
        if (file.exists()) {
            ResourceBundle bundle = ResourceBundle.getBundle(USER_MAIL_BUNDLE, locale);
            head = bundle.getString(HEAD);
            content = bundle.getString(CONTENT);
        } else {
            head = DEFAULT_DRIVER_HEAD;
            content = DEFAULT_DRIVER_CONTENT;
            logger.log(Level.WARN, "bundle not found + " + USER_MAIL_BUNDLE);
        }

        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);

        formatter.format(content, driverFirstName, driverLastName, carNumber, carMark, phoneNumber);


        MailThread thread = new MailThread(userLogin, head, stringBuilder.toString(), MailProperty.getInstance().getProperties());
        thread.start();
    }

    public void sendDriverMail(String driverLogin, String userFirstName, String userLastName, String phoneNumber, String lang) {
        String propsPath = this.getClass().getClassLoader().getResource(".").getPath();
        Locale locale = new Locale(lang);
        File file = new File(propsPath, DRIVER_MAIL_BUNDLE + PROPERTIES_EXTENSION);
        String head;
        String content;
        if (file.exists()) {
            ResourceBundle bundle = ResourceBundle.getBundle(DRIVER_MAIL_BUNDLE, locale);
            head = bundle.getString(HEAD);
            content = bundle.getString(CONTENT);
        } else {
            head = DEFAULT_USER_HEAD;
            content = DEFAULT_USER_CONTENT;
            logger.log(Level.WARN, "bundle not found + " + DRIVER_MAIL_BUNDLE);
        }

        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);

        formatter.format(content, userFirstName, userLastName, phoneNumber);


        MailThread thread = new MailThread(driverLogin, head, stringBuilder.toString(), MailProperty.getInstance().getProperties());
        thread.start();
    }

    public Optional<Ride> findCurrentUserRide(String login) throws ReceiverException {
        FindSpecification<Ride> specification = new FindRideInfoPassengerSpecification(login);
        Finder<Ride> finder = new Finder<>();
        List<Ride> rides = finder.findBySpecification(specification);
        Optional<Ride> ride = Optional.empty();
        if (!rides.isEmpty()) {
            ride = Optional.ofNullable(rides.get(0));
        }
        return ride;
    }

    public void userAcceptStart(int rideId) throws ReceiverException {
        Repository<Ride> rideRepository = new Repository<>();
        RepositoryController controller = new RepositoryController(rideRepository);
        try {
            Specification rideUpdateSpecification = new UpdateRideUserAcceptStartSpecification(rideId);
            rideRepository.update(rideUpdateSpecification);
            controller.end();
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
    }

    public void passengerAcceptEnd(Ride ride) throws ReceiverException {
        String passengerLogin=ride.getPassenger().getLogin();
        String driverLogin=ride.getDriver().getLogin();

        Repository<Ride> rideRepository = new Repository<>();
        Repository<Driver> driverRepository = new Repository<>();
        Repository<User> userRepository = new Repository<>();
        RepositoryController controller = new RepositoryController(rideRepository,driverRepository,userRepository);
        try {
            controller.startTransaction();
            Specification rideUpdateSpecification = new UpdateRideUserAcceptEndSpecification(ride.getRideId());
            rideRepository.update(rideUpdateSpecification);

            BigDecimal price = ride.getPrice();

            FindSpecification<User> findUserBalanceSpecification = new FindUserBalanceSpecification(passengerLogin);
            List<User> users=userRepository.find(findUserBalanceSpecification);
            User passenger = users.get(0);
            BigDecimal balance = passenger.getBalance();
            BigDecimal newPassengerBalance = balance.subtract(price);
            Specification updateUserSpecification = new UpdateUserBalanceSpecification(passengerLogin,newPassengerBalance);
            userRepository.update(updateUserSpecification);

            FindSpecification<Driver> findDriverEarnedMoneySpecification = new FindDriverEarnedMoneySpecification(driverLogin);
            List<Driver> drivers=driverRepository.find(findDriverEarnedMoneySpecification);
            Driver driver = drivers.get(0);
            BigDecimal earnedMoney = driver.getEarnedMoney();
            BigDecimal newDriverEarnedMoney = price.multiply(DRIVER_PROCENT).add(earnedMoney);
            Specification updateDriverEarnedMoneySpecification = new UpdateDriverEarnedMoneySpecification(driverLogin,newDriverEarnedMoney);
            userRepository.update(updateDriverEarnedMoneySpecification);

            controller.commit();
            controller.endTransaction();
        } catch (RepositoryException e) {
            controller.rollBack();
            throw new ReceiverException(e);
        }
    }

    public Optional<String> findDriverLoginByRide(int rideId) throws ReceiverException {
        FindSpecification<Ride> specification = new FindRideDriverLoginByRideIdSpecification(rideId);
        Finder<Ride> finder = new Finder<>();
        List<Ride> rides = finder.findBySpecification(specification);
        Optional<String> driverLogin = Optional.empty();
        if (!rides.isEmpty()) {
            driverLogin = Optional.ofNullable(rides.get(0).getDriver().getLogin());
        }
        return driverLogin;
    }
}