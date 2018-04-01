package by.zhuk.buber.receiver;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.exeption.RepositoryException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.model.UserType;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.repository.UserRepository;
import by.zhuk.buber.specification.Specification;
import by.zhuk.buber.specification.impl.FindUserByPhoneNumberSpecification;

import java.math.BigDecimal;
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

    public void saveUser(String login, String firstName, String lastName, String password, String age, String phoneNumber) throws ReceiverException {
        Repository<User> repository = new UserRepository();
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

        try {
            repository.add(user);
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
    }
}