package by.zhuk.buber.receiver;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.exeption.RepositoryException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.repository.RepositoryTransaction;
import by.zhuk.buber.specification.find.FindSpecification;
import by.zhuk.buber.specification.find.FindUserByLoginSpecification;
import by.zhuk.buber.specification.find.FindUserByPatternSpecification;
import by.zhuk.buber.specification.find.FindUserByPhoneNumberSpecification;

import java.util.List;
import java.util.Optional;

public class UserReceiver {


    public Optional<User> findUserByLogin(String login) throws ReceiverException {
        FindSpecification<User> specification = new FindUserByLoginSpecification(login);
        List<User> users = findUsersBySpecification(specification);
        Optional<User> user = Optional.empty();
        if (!users.isEmpty()) {
            user = Optional.ofNullable(users.get(0));
        }
        return user;
    }

    public List<User> findUsersByPattern(String findPattern) throws ReceiverException {
        FindSpecification<User> specification = new FindUserByPatternSpecification(findPattern);
        return findUsersBySpecification(specification);
    }

    public boolean isPhoneNumberExist(String number) throws ReceiverException {
        FindSpecification<User> specification = new FindUserByPhoneNumberSpecification(number);
        List<User> users = findUsersBySpecification(specification);
        return !users.isEmpty();
    }

    public List<User> findUsersBySpecification(FindSpecification<User> specification) throws ReceiverException {
        RepositoryTransaction transaction = new RepositoryTransaction();
        Repository<User> repository = new Repository<>();
        List<User> users;
        try {
            transaction.startTransaction(repository);
            users = repository.find(specification);
            transaction.commit();
            transaction.endTransaction();
        } catch (RepositoryException e) {
            transaction.rollBack();
            throw new ReceiverException(e);
        }
        return users;
    }
}