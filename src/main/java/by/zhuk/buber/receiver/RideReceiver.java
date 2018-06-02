package by.zhuk.buber.receiver;

import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.exception.RepositoryException;
import by.zhuk.buber.mail.MailProperty;
import by.zhuk.buber.mail.MailThread;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.model.Mail;
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
import by.zhuk.buber.specification.find.ride.FindRideHistoryDriverSpecification;
import by.zhuk.buber.specification.find.ride.FindRideHistoryUserSpecification;
import by.zhuk.buber.specification.find.ride.FindRideInfoDriverSpecification;
import by.zhuk.buber.specification.find.ride.FindRideInfoPassengerSpecification;
import by.zhuk.buber.specification.find.ride.FindRidePassengerLoginByRideIdSpecification;
import by.zhuk.buber.specification.find.user.FindUserBalanceSpecification;
import by.zhuk.buber.specification.update.driver.UpdateDriverEarnedMoneySpecification;
import by.zhuk.buber.specification.update.driver.UpdateDriverIsWorkingSpecification;
import by.zhuk.buber.specification.update.ride.UpdateRideAllAcceptSpecification;
import by.zhuk.buber.specification.update.ride.UpdateRideDriverAcceptEndSpecification;
import by.zhuk.buber.specification.update.ride.UpdateRideDriverAcceptStartSpecification;
import by.zhuk.buber.specification.update.ride.UpdateRideUserAcceptEndSpecification;
import by.zhuk.buber.specification.update.ride.UpdateRideUserAcceptStartSpecification;
import by.zhuk.buber.specification.update.user.UpdateUserBalanceSpecification;

import java.math.BigDecimal;
import java.util.Formatter;
import java.util.List;
import java.util.Optional;
/**
 * Class include method to interaction with things connection with ride business logic
 */
public class RideReceiver {
    private static final String USER_MAIL_BUNDLE = "properties/rideUserMailContent";
    private static final String USER_REFUSE_MAIL_BUNDLE = "properties/refuseUserMailContent";
    private static final String DRIVER_REFUSE_MAIL_BUNDLE = "properties/refuseDriverMailContent";
    private static final String DRIVER_MAIL_BUNDLE = "properties/rideDriverMailContent";

    private static final String DEFAULT_USER_CONTENT = "Your driver: %s %s <br/>. Machine number: %s <br/>.Mark of the machine: %s <br/>.Phone number: %s.<br/><a href=\"http://localhost:8080/controller?command=view-user-ride\">More information</a>";
    private static final String DEFAULT_USER_HEAD = "BUBER ride.";

    private static final String DEFAULT_REFUSE_USER_CONTENT = "Your passenger refused to ride.";
    private static final String DEFAULT_REFUSE_USER_HEAD = "BUBER passenger refuse.";
    private static final String DEFAULT_REFUSE_DRIVER_CONTENT = "Your driver refused to ride.";
    private static final String DEFAULT_REFUSE_DRIVER_HEAD = "BUBER driver refuse.";
    private static final String DEFAULT_DRIVER_CONTENT = "Your passenger: %s %s <br/>.Phone number: %s.<br/><a href=\"http://localhost:8080/controller?command=view-driver-ride\">More information</a>";
    private static final String DEFAULT_DRIVER_HEAD = "BUBER ride(driver).";

    private static final BigDecimal DRIVER_PERCENT = new BigDecimal("0.85");
    private static final BigDecimal FINE_PRICE = new BigDecimal("2.50");

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
        MailReceiver mailReceiver = new MailReceiver();
        Mail mail = mailReceiver.createMailFromBundle(USER_MAIL_BUNDLE, lang, DEFAULT_USER_HEAD, DEFAULT_USER_CONTENT);

        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);

        formatter.format(mail.getContent(), driverFirstName, driverLastName, carNumber, carMark, phoneNumber);

        MailThread thread = new MailThread(userLogin, mail.getHead(), stringBuilder.toString(), MailProperty.getInstance().getProperties());
        thread.start();
    }

    public void sendDriverMail(String driverLogin, String userFirstName, String userLastName, String phoneNumber, String lang) {
        MailReceiver mailReceiver = new MailReceiver();
        Mail mail = mailReceiver.createMailFromBundle(DRIVER_MAIL_BUNDLE, lang, DEFAULT_DRIVER_HEAD, DEFAULT_DRIVER_CONTENT);

        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);

        formatter.format(mail.getContent(), userFirstName, userLastName, phoneNumber);


        MailThread thread = new MailThread(driverLogin, mail.getHead(), stringBuilder.toString(), MailProperty.getInstance().getProperties());
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

    public void passengerAcceptStart(int rideId) throws ReceiverException {
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
        String passengerLogin = ride.getPassenger().getLogin();
        String driverLogin = ride.getDriver().getLogin();

        Repository<Ride> rideRepository = new Repository<>();
        Repository<Driver> driverRepository = new Repository<>();
        Repository<User> userRepository = new Repository<>();
        RepositoryController controller = new RepositoryController(rideRepository, driverRepository, userRepository);
        try {
            controller.startTransaction();
            Specification rideUpdateSpecification = new UpdateRideUserAcceptEndSpecification(ride.getRideId());
            rideRepository.update(rideUpdateSpecification);

            BigDecimal price = ride.getPrice();

            FindSpecification<User> findUserBalanceSpecification = new FindUserBalanceSpecification(passengerLogin);
            List<User> users = userRepository.find(findUserBalanceSpecification);
            User passenger = users.get(0);
            BigDecimal balance = passenger.getBalance();
            BigDecimal newPassengerBalance = balance.subtract(price);
            Specification updateUserSpecification = new UpdateUserBalanceSpecification(passengerLogin, newPassengerBalance);
            userRepository.update(updateUserSpecification);

            FindSpecification<Driver> findDriverEarnedMoneySpecification = new FindDriverEarnedMoneySpecification(driverLogin);
            List<Driver> drivers = driverRepository.find(findDriverEarnedMoneySpecification);
            Driver driver = drivers.get(0);
            BigDecimal earnedMoney = driver.getEarnedMoney();
            BigDecimal newDriverEarnedMoney = price.multiply(DRIVER_PERCENT).add(earnedMoney);
            Specification updateDriverEarnedMoneySpecification = new UpdateDriverEarnedMoneySpecification(driverLogin, newDriverEarnedMoney);
            driverRepository.update(updateDriverEarnedMoneySpecification);


            Specification updateDriverIsWorkingSpecification = new UpdateDriverIsWorkingSpecification(true, driverLogin);
            driverRepository.update(updateDriverIsWorkingSpecification);

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

    public void passengerRefuse(Ride ride) throws ReceiverException {
        String passengerLogin = ride.getPassenger().getLogin();
        String driverLogin = ride.getDriver().getLogin();

        Repository<Ride> rideRepository = new Repository<>();
        Repository<Driver> driverRepository = new Repository<>();
        Repository<User> userRepository = new Repository<>();
        RepositoryController controller = new RepositoryController(rideRepository, driverRepository, userRepository);
        try {
            controller.startTransaction();
            Specification rideUpdateSpecification = new UpdateRideAllAcceptSpecification(ride.getRideId());
            rideRepository.update(rideUpdateSpecification);

            FindSpecification<User> findUserBalanceSpecification = new FindUserBalanceSpecification(passengerLogin);
            List<User> users = userRepository.find(findUserBalanceSpecification);
            User passenger = users.get(0);
            BigDecimal balance = passenger.getBalance();
            BigDecimal newPassengerBalance = balance.subtract(FINE_PRICE);
            Specification updateUserSpecification = new UpdateUserBalanceSpecification(passengerLogin, newPassengerBalance);
            userRepository.update(updateUserSpecification);

            FindSpecification<Driver> findDriverEarnedMoneySpecification = new FindDriverEarnedMoneySpecification(driverLogin);
            List<Driver> drivers = driverRepository.find(findDriverEarnedMoneySpecification);
            Driver driver = drivers.get(0);
            BigDecimal earnedMoney = driver.getEarnedMoney();
            BigDecimal newDriverEarnedMoney = FINE_PRICE.multiply(DRIVER_PERCENT).add(earnedMoney);
            Specification updateDriverEarnedMoneySpecification = new UpdateDriverEarnedMoneySpecification(driverLogin, newDriverEarnedMoney);
            driverRepository.update(updateDriverEarnedMoneySpecification);


            Specification updateDriverIsWorkingSpecification = new UpdateDriverIsWorkingSpecification(true, driverLogin);
            driverRepository.update(updateDriverIsWorkingSpecification);


            controller.commit();
            controller.endTransaction();
        } catch (RepositoryException e) {
            controller.rollBack();
            throw new ReceiverException(e);
        }
    }

    public void sendRefuseUserMail(String login, String lang) {
        MailReceiver mailReceiver = new MailReceiver();
        Mail mail = mailReceiver.createMailFromBundle(USER_REFUSE_MAIL_BUNDLE, lang, DEFAULT_REFUSE_USER_HEAD, DEFAULT_REFUSE_USER_CONTENT);

        MailThread thread = new MailThread(login, mail.getHead(), mail.getContent(), MailProperty.getInstance().getProperties());
        thread.start();
    }

    public void sendRefuseDriverMail(String login, String lang) {
        MailReceiver mailReceiver = new MailReceiver();
        Mail mail = mailReceiver.createMailFromBundle(DRIVER_REFUSE_MAIL_BUNDLE, lang, DEFAULT_REFUSE_DRIVER_HEAD, DEFAULT_REFUSE_DRIVER_CONTENT);

        MailThread thread = new MailThread(login, mail.getHead(), mail.getContent(), MailProperty.getInstance().getProperties());
        thread.start();
    }

    public Optional<Ride> findCurrentDriverRide(String login) throws ReceiverException {
        FindSpecification<Ride> specification = new FindRideInfoDriverSpecification(login);
        Finder<Ride> finder = new Finder<>();
        List<Ride> rides = finder.findBySpecification(specification);
        Optional<Ride> ride = Optional.empty();
        if (!rides.isEmpty()) {
            ride = Optional.ofNullable(rides.get(0));
        }
        return ride;
    }

    public void driverAcceptStart(int rideId) throws ReceiverException {
        Repository<Ride> rideRepository = new Repository<>();
        RepositoryController controller = new RepositoryController(rideRepository);
        try {
            Specification rideUpdateSpecification = new UpdateRideDriverAcceptStartSpecification(rideId);
            rideRepository.update(rideUpdateSpecification);
            controller.end();
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
    }

    public void driverAcceptEnd(int rideId) throws ReceiverException {
        Repository<Ride> rideRepository = new Repository<>();
        RepositoryController controller = new RepositoryController(rideRepository);
        try {
            Specification rideUpdateSpecification = new UpdateRideDriverAcceptEndSpecification(rideId);
            rideRepository.update(rideUpdateSpecification);
            controller.end();
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
    }

    public Optional<String> findPassengerLoginByRide(int rideId) throws ReceiverException {
        FindSpecification<Ride> specification = new FindRidePassengerLoginByRideIdSpecification(rideId);
        Finder<Ride> finder = new Finder<>();
        List<Ride> rides = finder.findBySpecification(specification);
        Optional<String> driverLogin = Optional.empty();
        if (!rides.isEmpty()) {
            driverLogin = Optional.ofNullable(rides.get(0).getPassenger().getLogin());
        }
        return driverLogin;
    }

    public void driverRefuse(Ride ride) throws ReceiverException {
        String driverLogin = ride.getDriver().getLogin();

        Repository<Ride> rideRepository = new Repository<>();
        Repository<Driver> driverRepository = new Repository<>();
        RepositoryController controller = new RepositoryController(rideRepository, driverRepository);
        try {
            controller.startTransaction();
            Specification rideUpdateSpecification = new UpdateRideAllAcceptSpecification(ride.getRideId());
            rideRepository.update(rideUpdateSpecification);

            Specification updateDriverIsWorkingSpecification = new UpdateDriverIsWorkingSpecification(true, driverLogin);
            driverRepository.update(updateDriverIsWorkingSpecification);


            controller.commit();
            controller.endTransaction();
        } catch (RepositoryException e) {
            controller.rollBack();
            throw new ReceiverException(e);
        }
    }

    public List<Ride> findDriverRideHistory(String login) throws ReceiverException {
        FindSpecification<Ride> specification = new FindRideHistoryDriverSpecification(login);
        Finder<Ride> finder = new Finder<>();
        List<Ride> rides = finder.findBySpecification(specification);
        for (Ride ride : rides) {
            Driver driver = ride.getDriver();
            driver.setEarnedMoney(driver.getEarnedMoney().multiply(DRIVER_PERCENT));
        }
        return rides;

    }

    public List<Ride> findUserRideHistory(String login) throws ReceiverException {
        FindSpecification<Ride> specification = new FindRideHistoryUserSpecification(login);
        Finder<Ride> finder = new Finder<>();
        return finder.findBySpecification(specification);
    }
}