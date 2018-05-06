package by.zhuk.buber.receiver;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.exeption.RepositoryException;
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
import by.zhuk.buber.specification.add.AddCarMarkSpecification;
import by.zhuk.buber.specification.add.AddDriverSpecification;
import by.zhuk.buber.specification.add.AddUserSpecification;
import by.zhuk.buber.specification.find.FindCarMarkByNameSpecification;
import by.zhuk.buber.specification.find.FindSpecification;
import by.zhuk.buber.specification.find.FindUserByLoginSpecification;
import by.zhuk.buber.specification.update.UpdateUserTypeSpecification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SignUpReceiver {
    private static String MAIL_BUNDLE = "";

    public void saveUser(User user) throws ReceiverException {
        Repository<User> repository = new Repository<>();
        RepositoryController controller = new RepositoryController(repository);
        try {
            Specification carAddSpecification = new AddUserSpecification(user);
            repository.add(carAddSpecification);
            controller.end();
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
    }

    public void sendAcceptMail(String login, String firstName, String lastName, String password, String birthDay, String phoneNumber) {
        String hash = String.valueOf(login.concat(password).hashCode());

        MailThread thread = new MailThread(login, "test", "<a href=\"http://localhost:8080/controller?command=sign-up-accept&hash=" + hash + "\">Go to accept</a> ", MailProperty.getInstance().getProperties());
        thread.start();
        SignUpUserPool pool = SignUpUserPool.getInstance();
        User user = new User(login, firstName, lastName, password, LocalDate.parse(birthDay), false, phoneNumber, new BigDecimal(0), UserType.USER, (float) 0.0);
        SignUpUserInfo info = new SignUpUserInfo(user, LocalTime.now());
        pool.putInfo(hash, info);
    }

    public void saveDriver(String login, String carNumber, String documentId, String carMarkName) throws ReceiverException {

        Repository<Driver> driverRepository = new Repository<>();
        Repository<CarMark> carMarkRepository = new Repository<>();
        Repository<User> userRepository = new Repository<>();
        RepositoryController controller = new RepositoryController(driverRepository, carMarkRepository, userRepository);
        try {
            controller.startTransaction();
            Specification carAddSpecification = new AddCarMarkSpecification(carMarkName);
            carMarkRepository.add(carAddSpecification);
            FindSpecification<CarMark> specification = new FindCarMarkByNameSpecification(carMarkName);
            List<CarMark> carMarks = carMarkRepository.find(specification);
            CarMark carMark = carMarks.get(0);

            Driver driver = new Driver();
            driver.setLogin(login);
            driver.setCarNumber(carNumber);
            driver.setDocumentId(documentId);
            driver.setCarMark(String.valueOf(carMark.getIndex()));
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