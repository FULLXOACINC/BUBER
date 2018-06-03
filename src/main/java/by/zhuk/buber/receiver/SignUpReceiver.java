package by.zhuk.buber.receiver;

import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.exception.RepositoryException;
import by.zhuk.buber.mail.MailProperty;
import by.zhuk.buber.mail.MailThread;
import by.zhuk.buber.model.CarMark;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.model.Mail;
import by.zhuk.buber.model.User;
import by.zhuk.buber.model.UserType;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.repository.RepositoryController;
import by.zhuk.buber.specification.Specification;
import by.zhuk.buber.specification.add.AddDriverSpecification;
import by.zhuk.buber.specification.add.AddUserSpecification;
import by.zhuk.buber.specification.find.user.FindUserByLoginSpecification;
import by.zhuk.buber.specification.update.user.UpdateUserDiscountSpecification;
import by.zhuk.buber.specification.update.user.UpdateUserTypeSpecification;
import by.zhuk.buber.userpool.SignUpUserPool;
import by.zhuk.buber.userpool.UserPoolInfo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Formatter;
import java.util.List;
/**
 * Class include method to interaction with things connection with sing up business logic
 */
public class SignUpReceiver {


    private static final String MAIL_BUNDLE = "properties/signUpMailContent";
    private static final String DEFAULT_CONTENT = "Hello,%s %s , you are greeted by the employees of BUBER, click on the link to confirm registration, all the best.<br/><a href=\"http://localhost:8080/controller?command=sign-up-accept&hash=%s\">Go to confirm</a> ";
    private static final String DEFAULT_HEAD = "BUBER sign up";

    /**
     * Method save user in system
     *
     * @throws ReceiverException throws when there are problems with the database
     * @see Adder,AddUserSpecification
     */
    public void saveUser(String login, String firstName, String lastName, String password, UserType type, BigDecimal balance, LocalDate birthDay, String phoneNumber) throws ReceiverException {
        Specification userAddSpecification = new AddUserSpecification(login, firstName, lastName, password, type, balance, birthDay, phoneNumber);
        Adder adder = new Adder();
        adder.addBySpecification(userAddSpecification);
    }
    /**
     * Method send accept mail to user email(login) with hash login.concat(password).hashCode()
     *
     * @see MailThread,AddUserSpecification,UserPoolInfo
     */
    public void sendAcceptMail(String login, String firstName, String lastName, String password, String birthDay, String phoneNumber, String lang) {
        String hash = String.valueOf(login.concat(password).hashCode());
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);
        MailReceiver mailReceiver = new MailReceiver();
        Mail mail = mailReceiver.createMailFromBundle(MAIL_BUNDLE, lang, DEFAULT_HEAD, DEFAULT_CONTENT);

        formatter.format(mail.getContent(), lastName, firstName, hash);


        MailThread thread = new MailThread(login, mail.getHead(), stringBuilder.toString(), MailProperty.getInstance().getProperties());
        thread.start();
        SignUpUserPool pool = SignUpUserPool.getInstance();
        User user = new User(login, firstName, lastName, password, LocalDate.parse(birthDay), false, phoneNumber, new BigDecimal(0), UserType.USER, new BigDecimal(0));
        UserPoolInfo info = new UserPoolInfo(user, LocalTime.now());
        pool.putInfo(hash, info);
    }

    /**
     * Method save driver to system
     * Has transaction
     * 1)Add driver String login, carNumber, documentId, tariff
     * 2)Add car mark
     * 3)Change user type to driver
     *
     * @throws ReceiverException throws when there are problems with the database
     * @see Adder,AddUserSpecification
     */
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
                Specification updateUserTypeSpecification = new UpdateUserTypeSpecification(user.getLogin(), UserType.DRIVER);
                userRepository.update(updateUserTypeSpecification);
            }
            controller.commit();
            controller.endTransaction();

        } catch (RepositoryException | ReceiverException e) {
            controller.rollBack();
            throw new ReceiverException(e);
        }


    }

}