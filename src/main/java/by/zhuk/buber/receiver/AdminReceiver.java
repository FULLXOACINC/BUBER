package by.zhuk.buber.receiver;

import by.zhuk.buber.exeption.ReceiverException;
import by.zhuk.buber.exeption.RepositoryException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.model.UserType;
import by.zhuk.buber.repository.RepositoryTransaction;
import by.zhuk.buber.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdminReceiver {

    public void switchBan(User user) throws ReceiverException {
        RepositoryTransaction transaction = new RepositoryTransaction();
        UserRepository repository = new UserRepository();
        try {
            transaction.startTransaction(repository);
            user.setBaned(!user.isBaned());
            repository.update(user);
            transaction.commit();
            transaction.endTransaction();
        } catch (RepositoryException e) {
            transaction.rollBack();
            throw new ReceiverException(e);
        }

    }

    public void switchAdminStatus(User user) throws ReceiverException {
        RepositoryTransaction transaction = new RepositoryTransaction();
        UserRepository repository = new UserRepository();
        try {
            transaction.startTransaction(repository);
            if (user.getType() != UserType.ROOT_ADMIN) {
                if (user.getType() == UserType.USER || user.getType() == UserType.DRIVER) {
                    user.setType(UserType.ADMIN);
                } else {
                    user.setType(UserType.USER);
                }
                repository.update(user);
            }
            transaction.commit();
            transaction.endTransaction();
        } catch (RepositoryException e) {
            transaction.rollBack();
            throw new ReceiverException(e);
        }
    }
}