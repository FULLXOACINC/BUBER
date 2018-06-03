package by.zhuk.buber.receiver;

import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.specification.find.FindSpecification;
import by.zhuk.buber.specification.find.user.FindUserByLoginAndPasswordSpecification;

import java.util.List;

/**
 * Class include method to interaction with things connection with sing in business logic
 */
public class SignInReceiver {

    /**
     * Method check is password match login
     *
     * @return true if match,else is not match
     * @throws ReceiverException throws when there are problems json and oauth protocol
     * @see FindSpecification,FindUserByLoginAndPasswordSpecification,User
     */
    public boolean checkPassword(String login, String password) throws ReceiverException {
        FindSpecification<User> specification = new FindUserByLoginAndPasswordSpecification(login, password);
        Finder<User> userFinder = new Finder<>();
        List<User> users = userFinder.findBySpecification(specification);
        return !users.isEmpty();

    }
}