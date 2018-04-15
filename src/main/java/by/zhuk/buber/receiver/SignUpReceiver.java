package by.zhuk.buber.receiver;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.exeption.RepositoryException;
import by.zhuk.buber.mail.MailProperty;
import by.zhuk.buber.mail.MailThread;
import by.zhuk.buber.model.CarMark;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.model.User;
import by.zhuk.buber.model.UserType;
import by.zhuk.buber.repository.CarMarkRepository;
import by.zhuk.buber.repository.DriverRepository;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.repository.RepositoryTransaction;
import by.zhuk.buber.repository.TransactionRepository;
import by.zhuk.buber.repository.UserRepository;
import by.zhuk.buber.signuppool.SignUpUserInfo;
import by.zhuk.buber.signuppool.SignUpUserPool;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class SignUpReceiver {
    private static String MAIL_BUNDLE = "";

    public void saveUser(User user) throws ReceiverException {
        Repository<User> repository = new UserRepository();
        try {
            repository.add(user);
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
    }

    public void sendAcceptMail(String login, String firstName, String lastName, String password, String birthDay, String phoneNumber) {
        String hash = String.valueOf(login.concat(password).hashCode());

        MailThread thread = new MailThread(login, "test", "<a href=\"http://localhost:8080/controller?command=sign-up-accept&hash=" + hash + "\">Go to accept</a> ", MailProperty.getInstance().getProperties());
        thread.start();
        SignUpUserPool pool = SignUpUserPool.getInstance();
        User user = new User();
        user.setLogin(login);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        user.setBaned(false);
        user.setType(UserType.USER);
        user.setBalance(new BigDecimal(0));
        user.setBirthDay(LocalDate.parse(birthDay));
        SignUpUserInfo info = new SignUpUserInfo(user, LocalTime.now());
        pool.putInfo(hash, info);
    }

    public void saveDriver(String login, String carNumber, String documentId, String carMark) {
        RepositoryTransaction transaction = new RepositoryTransaction();

        TransactionRepository<Driver> driverRepository = new DriverRepository();
        TransactionRepository<CarMark> carMarkRepository = new CarMarkRepository();
        Driver driver = new Driver();
        driver.setLogin(login);
        driver.setCarNumber(carNumber);
        driver.setDocumentId(documentId);
        driver.setCarMark(carMark);

    }
}