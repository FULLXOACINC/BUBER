package by.zhuk.buber.receiver;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.exeption.RepositoryException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.repository.UserRepository;
import by.zhuk.buber.specification.Specification;
import by.zhuk.buber.specification.impl.FindUserByLoginSpecification;
import by.zhuk.buber.specification.impl.FindUserByPatternSpecification;

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

    public List<User> findAllUser(String findPattern) throws ReceiverException {
        Repository<User> repository = new UserRepository();
        Specification specification = new FindUserByPatternSpecification(findPattern);
        List<User> users;
        try {
            users = repository.find(specification);
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
        return users;
    }
}