package by.zhuk.buber.receiver;

import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.exception.RepositoryException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.model.UserType;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.repository.RepositoryController;
import by.zhuk.buber.specification.Specification;
import by.zhuk.buber.specification.find.FindSpecification;
import by.zhuk.buber.specification.find.user.FindUserByPatternSpecification;
import by.zhuk.buber.specification.update.user.UpdateUserBanStatusSpecification;
import by.zhuk.buber.specification.update.user.UpdateUserDiscountSpecification;
import by.zhuk.buber.specification.update.user.UpdateUserTypeSpecification;

import java.util.List;

public class AdminReceiver {

    public void switchBan(User user) throws ReceiverException {
        Repository<User> repository = new Repository<>();
        RepositoryController controller = new RepositoryController(repository);
        try {
            user.setBaned(!user.isBaned());
            Specification updateSpecification = new UpdateUserBanStatusSpecification(user);
            repository.update(updateSpecification);
            controller.end();
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }

    }

    public void switchAdminStatus(User user) throws ReceiverException {
        Repository repository = new Repository();
        RepositoryController controller = new RepositoryController(repository);
        try {
            if (user.getType() != UserType.ROOT_ADMIN) {
                if (user.getType() == UserType.USER || user.getType() == UserType.DRIVER) {
                    user.setType(UserType.ADMIN);
                } else {
                    user.setType(UserType.USER);
                }
                Specification updateSpecification = new UpdateUserTypeSpecification(user);

                repository.update(updateSpecification);
            }
            controller.end();
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
    }

    public void changeDiscount(float discount, String login) throws ReceiverException {
        Repository<User> repository = new Repository<>();
        RepositoryController controller = new RepositoryController(repository);
        try {
            Specification updateSpecification = new UpdateUserDiscountSpecification(login, discount);
            repository.update(updateSpecification);
            controller.end();
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
    }

    public List<User> findUsersByPattern(String findPattern) throws ReceiverException {
        FindSpecification<User> specification = new FindUserByPatternSpecification(findPattern);
        Finder<User> userFinder = new Finder<>();
        return userFinder.findBySpecification(specification);
    }
}