package by.zhuk.buber.receiver;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.specification.find.FindSpecification;
import by.zhuk.buber.specification.find.user.FindUserByLoginSpecification;
import by.zhuk.buber.specification.find.user.FindUserByPatternSpecification;
import by.zhuk.buber.specification.find.user.FindUserByPhoneNumberSpecification;

import java.util.List;
import java.util.Optional;

public class UserReceiver {


    public Optional<User> findUserByLogin(String login) throws ReceiverException {
        FindSpecification<User> specification = new FindUserByLoginSpecification(login);
        Finder<User> userFinder = new Finder<>();
        List<User> users = userFinder.findBySpecification(specification);
        Optional<User> user = Optional.empty();
        if (!users.isEmpty()) {
            user = Optional.ofNullable(users.get(0));
        }
        return user;
    }

    public boolean isPhoneNumberExist(String number) throws ReceiverException {
        FindSpecification<User> specification = new FindUserByPhoneNumberSpecification(number);
        Finder<User> userFinder = new Finder<>();
        List<User> users = userFinder.findBySpecification(specification);

        return !users.isEmpty();
    }

    public boolean isUserExist(String login) throws ReceiverException {
        FindSpecification<User> specification = new FindUserByLoginSpecification(login);
        Finder<User> userFinder = new Finder<>();
        List<User> users = userFinder.findBySpecification(specification);
        return !users.isEmpty();
    }
}