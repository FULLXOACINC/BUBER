package by.zhuk.buber.receiver;

import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.exception.RepositoryException;
import by.zhuk.buber.mail.MailProperty;
import by.zhuk.buber.mail.MailThread;
import by.zhuk.buber.model.CarMark;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.model.User;
import by.zhuk.buber.model.UserType;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.repository.RepositoryController;
import by.zhuk.buber.signuppool.SignUpUserInfo;
import by.zhuk.buber.signuppool.SignUpUserPool;
import by.zhuk.buber.specification.Specification;
import by.zhuk.buber.specification.add.AddDriverSpecification;
import by.zhuk.buber.specification.add.AddUserSpecification;
import by.zhuk.buber.specification.find.user.FindUserByLoginSpecification;
import by.zhuk.buber.specification.update.user.UpdateUserTypeSpecification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class SignUpReceiver {
    private static Logger logger = LogManager.getLogger(SignUpReceiver.class);
    private static final String MAIL_BUNDLE = "properties/signUpMailContent";
    private static final String HEAD = "head";
    private static final String CONTENT = "content";

    private static final String DEFAULT_CONTENT = "Hello,%s %s , you are greeted by the employees of BUBER, click on the link to confirm registration, all the best.<br/><a href=\"http://localhost:8080/controller?command=sign-up-accept&hash=%s\">Go to confirm</a> ";
    private static final String DEFAULT_HEAD = "BUBER sign up";
    private static final String PROPERTIES_EXTENSION = ".properties";


    public void saveUser(User user) throws ReceiverException {
        Specification userAddSpecification = new AddUserSpecification(user);
        Adder<User> adder = new Adder<>();
        adder.addBySpecification(userAddSpecification);
    }

    public void sendAcceptMail(String login, String firstName, String lastName, String password, String birthDay, String phoneNumber, String lang) {
        String hash = String.valueOf(login.concat(password).hashCode());
        Locale locale = new Locale(lang);

        String propsPath = this.getClass().getClassLoader().getResource(".").getPath();
        File file = new File(propsPath, MAIL_BUNDLE + PROPERTIES_EXTENSION);
        String head;
        String content;
        if (file.exists()) {
            ResourceBundle bundle = ResourceBundle.getBundle(MAIL_BUNDLE, locale);
            head = bundle.getString(HEAD);
            content = bundle.getString(CONTENT);
        } else {
            head = DEFAULT_HEAD;
            content = DEFAULT_CONTENT;
            logger.log(Level.WARN, "bundle not found + " + MAIL_BUNDLE);
        }

        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);

        formatter.format(content, lastName, firstName, hash);


        MailThread thread = new MailThread(login, head, stringBuilder.toString(), MailProperty.getInstance().getProperties());
        thread.start();
        SignUpUserPool pool = SignUpUserPool.getInstance();
        User user = new User(login, firstName, lastName, password, LocalDate.parse(birthDay), false, phoneNumber, new BigDecimal(0), UserType.USER, (float) 0.0);
        SignUpUserInfo info = new SignUpUserInfo(user, LocalTime.now());
        pool.putInfo(hash, info);
    }

    public void saveDriver(String login, String carNumber, String documentId, String carMarkName, BigDecimal tariff) throws ReceiverException {

        Repository<Driver> driverRepository = new Repository<>();
        Repository<CarMark> carMarkRepository = new Repository<>();
        Repository<User> userRepository = new Repository<>();
        RepositoryController controller = new RepositoryController(driverRepository, carMarkRepository, userRepository);
        try {
            controller.startTransaction();
            CarMarkReceiver carMarkReceiver = new CarMarkReceiver();
            CarMark carMark = carMarkReceiver.saveCarMark(carMarkName, carMarkRepository);

            Driver driver = new Driver();
            driver.setLogin(login);
            driver.setCarNumber(carNumber);
            driver.setDocumentId(documentId);
            driver.setCarMark(carMark);
            driver.setTariff(tariff);
            Specification driverAddSpecification = new AddDriverSpecification(driver);
            driverRepository.add(driverAddSpecification);


            List<User> users = userRepository.find(new FindUserByLoginSpecification(login));
            if (!users.isEmpty()) {
                User user = users.get(0);
                user.setType(UserType.DRIVER);
                Specification updateUserTypeSpecification = new UpdateUserTypeSpecification(user);
                userRepository.update(updateUserTypeSpecification);
            }
            controller.commit();
            controller.endTransaction();


        } catch (RepositoryException e) {
            controller.rollBack();
            throw new ReceiverException(e);
        }


    }
}