package by.zhuk.buber.receiver;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.exeption.RepositoryException;
import by.zhuk.buber.mail.MailProperty;
import by.zhuk.buber.mail.MailThread;
import by.zhuk.buber.model.User;
import by.zhuk.buber.model.UserType;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.repository.UserRepository;
import by.zhuk.buber.singleton.SignUpUserInfo;
import by.zhuk.buber.singleton.SignUpUserPool;
import by.zhuk.buber.specification.Specification;
import by.zhuk.buber.specification.impl.FindUserByPhoneNumberSpecification;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

public class SignUpReceiver {

    public boolean isPhoneNumberExist(String number) throws ReceiverException {

        Repository<User> repository = new UserRepository();
        Specification specification = new FindUserByPhoneNumberSpecification(number);
        List<User> users;
        try {
            users = repository.find(specification);
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
        return !users.isEmpty();
    }

    public void saveUser(User user) throws ReceiverException {
        Repository<User> repository = new UserRepository();
        try {
            repository.add(user);
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
    }

    public void sendAcceptMail(String login, String firstName, String lastName, String password, String age, String phoneNumber) {
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
        user.setAge(Integer.parseInt(age));
        SignUpUserInfo info = new SignUpUserInfo(user, LocalTime.now());
        pool.putInfo(hash,info);
    }
}