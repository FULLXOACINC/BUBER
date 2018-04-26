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
import by.zhuk.buber.repository.UserRepository;
import by.zhuk.buber.signuppool.SignUpUserInfo;
import by.zhuk.buber.signuppool.SignUpUserPool;
import by.zhuk.buber.specification.Specification;
import by.zhuk.buber.specification.impl.FindCarMarkByName;
import by.zhuk.buber.specification.impl.FindUserByLoginSpecification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SignUpReceiver {
    private static String MAIL_BUNDLE = "";

    public void saveUser(User user) throws ReceiverException {
        RepositoryTransaction transaction = new RepositoryTransaction();
        Repository<User> repository = new UserRepository();
        try {
            transaction.startTransaction(repository);
            repository.add(user);
            transaction.commit();
            transaction.endTransaction();
        } catch (RepositoryException e) {
            transaction.rollBack();
            throw new ReceiverException(e);
        }
    }

    public void sendAcceptMail(String login, String firstName, String lastName, String password, String birthDay, String phoneNumber) {
        String hash = String.valueOf(login.concat(password).hashCode());

        MailThread thread = new MailThread(login, "test", "<a href=\"http://localhost:8080/controller?command=sign-up-accept&hash=" + hash + "\">Go to accept</a> ", MailProperty.getInstance().getProperties());
        thread.start();
        SignUpUserPool pool = SignUpUserPool.getInstance();
        User user = new User(login, firstName, lastName, password, LocalDate.parse(birthDay), false, phoneNumber, new BigDecimal(0), UserType.USER);
        SignUpUserInfo info = new SignUpUserInfo(user, LocalTime.now());
        pool.putInfo(hash, info);
    }

    public void saveDriver(String login, String carNumber, String documentId, String carMarkName) throws ReceiverException {
        RepositoryTransaction transaction = new RepositoryTransaction();

        Repository<Driver> driverRepository = new DriverRepository();
        Repository<CarMark> carMarkRepository = new CarMarkRepository();
        Repository<User> userRepository = new UserRepository();
        try {
            transaction.startTransaction(driverRepository, carMarkRepository,userRepository);
            CarMark carMark = new CarMark();
            carMark.setMarkName(carMarkName);

            carMarkRepository.add(carMark);
            Specification specification = new FindCarMarkByName(carMarkName);
            List<CarMark> carMarks = carMarkRepository.find(specification);
            carMark = carMarks.get(0);

            Driver driver = new Driver();
            driver.setLogin(login);
            driver.setCarNumber(carNumber);
            driver.setDocumentId(documentId);
            driver.setCarMark(String.valueOf(carMark.getIndex()));
            driverRepository.add(driver);

            List<User> users = userRepository.find(new FindUserByLoginSpecification(login));
            if (!users.isEmpty()) {
                User user = users.get(0);
                user.setType(UserType.DRIVER);
                userRepository.update(user);
            }
            transaction.commit();
            transaction.endTransaction();


        } catch (RepositoryException e) {
            transaction.rollBack();
            throw new ReceiverException(e);
        }


    }
}