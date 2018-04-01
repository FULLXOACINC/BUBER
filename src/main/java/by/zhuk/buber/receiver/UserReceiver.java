package by.zhuk.buber.receiver;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.exeption.RepositoryException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.model.UserType;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.repository.UserRepository;
import by.zhuk.buber.specification.Specification;
import by.zhuk.buber.specification.impl.FindUserByLoginSpecification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class UserReceiver {

    public Optional<User> findUserByLogin(String login) throws ReceiverException {
        Repository<User> repository = new UserRepository();
        Specification specification = new FindUserByLoginSpecification(login);
        List<User> users;
        try {
            users = repository.find(specification);
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
        Optional<User> user = Optional.empty();
        if (!users.isEmpty()) {
            user = Optional.ofNullable(users.get(0));
        }
        return user;
    }

    public void saveUser(String login){
        User user = new User();
        user.setLogin("testLogin");
        user.setFirstName("testName");
        user.setLastName("testLastName");
        user.setPassword("password");
        user.setType(UserType.USER);
        user.setBalance(new BigDecimal(99.89));
        user.setAge(21);
        user.setPhoneNumber("+375291713229");
        Repository<User> repository = new UserRepository();
        try {
            repository.add(user);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }
}