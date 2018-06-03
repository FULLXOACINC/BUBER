package by.zhuk.buber.receiver;

import by.zhuk.buber.exception.ReceiverException;
import by.zhuk.buber.exception.RepositoryException;
import by.zhuk.buber.model.Complaint;
import by.zhuk.buber.model.User;
import by.zhuk.buber.model.UserType;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.repository.RepositoryController;
import by.zhuk.buber.specification.Specification;
import by.zhuk.buber.specification.find.FindSpecification;
import by.zhuk.buber.specification.find.complaint.FindUserComplaintsSpecification;
import by.zhuk.buber.specification.find.user.FindUserBalanceSpecification;
import by.zhuk.buber.specification.find.user.FindUserByLoginSpecification;
import by.zhuk.buber.specification.find.user.FindUserByPatternSpecification;
import by.zhuk.buber.specification.find.user.FindUserByPhoneNumberSpecification;
import by.zhuk.buber.specification.find.user.FindUserDiscountSpecification;
import by.zhuk.buber.specification.find.user.FindUserInfoForRideSpecification;
import by.zhuk.buber.specification.update.user.UpdateUserBalanceSpecification;
import by.zhuk.buber.specification.update.user.UpdateUserBanStatusSpecification;
import by.zhuk.buber.specification.update.user.UpdateUserDiscountSpecification;
import by.zhuk.buber.specification.update.user.UpdateUserPasswordSpecification;
import by.zhuk.buber.specification.update.user.UpdateUserProfileSpecification;
import by.zhuk.buber.specification.update.user.UpdateUserTypeSpecification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Class include method to interaction with things connection with user business logic
 */
public class UserReceiver {
    private static Logger logger = LogManager.getLogger(UserReceiver.class);
    private static final BigDecimal MAX_USER_BALANCE = new BigDecimal("100000.00");

    /**
     * Method find user by login
     *
     * @return user if exist ,else Optional.empty()
     * @throws ReceiverException throws when there are problems with the database
     * @see FindUserByLoginSpecification,Finder
     */
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

    /**
     * Method check is exist user with phone number
     *
     * @return true if exist, else false
     * @throws ReceiverException throws when there are problems with the database
     * @see FindUserByPhoneNumberSpecification,Finder
     */
    public boolean isPhoneNumberExist(String number) throws ReceiverException {
        FindSpecification<User> specification = new FindUserByPhoneNumberSpecification(number);
        Finder<User> userFinder = new Finder<>();
        List<User> users = userFinder.findBySpecification(specification);

        return !users.isEmpty();
    }

    /**
     * Method check is exist user with login
     *
     * @return true is exist, else false
     * @throws ReceiverException throws when there are problems with the database
     * @see FindUserByLoginSpecification,Finder
     */
    public boolean isLoginExist(String login) throws ReceiverException {
        FindSpecification<User> specification = new FindUserByLoginSpecification(login);
        Finder<User> userFinder = new Finder<>();
        List<User> users = userFinder.findBySpecification(specification);
        return !users.isEmpty();
    }

    /**
     * Method fill up balance
     * 1)Find balance
     * 2)Add moneyAmount to balance
     *
     * @return true if exist, else false
     * @throws ReceiverException throws when there are problems with the database
     * @see FindUserByLoginSpecification,Finder,Repository,RepositoryController,UpdateUserBalanceSpecification
     */
    public void fillUpBalance(String login, String moneyAmount) throws ReceiverException {
        BigDecimal money = new BigDecimal(moneyAmount);
        FindSpecification<User> specification = new FindUserBalanceSpecification(login);
        Finder<User> userFinder = new Finder<>();
        List<User> users = userFinder.findBySpecification(specification);
        if (users.isEmpty()) {
            logger.log(Level.WARN, "Not found users with login :" + login);
            return;
        }
        User user = users.get(0);
        user.fillUpBalance(money);
        Repository<User> repository = new Repository<>();
        RepositoryController repositoryController = new RepositoryController(repository);
        try {
            Specification updateBalanceSpecification = new UpdateUserBalanceSpecification(login, user.getBalance());
            repository.update(updateBalanceSpecification);
            repositoryController.end();
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
    }

    /**
     * Method check is user balance full(<= 100000.00) if add to balance moneyAmount
     *
     * @return true if full, else false
     * @throws ReceiverException throws when there are problems with the database
     * @see FindUserBalanceSpecification,Finder,BigDecimal
     */
    public boolean isUserBalanceFull(String login, String moneyAmount) throws ReceiverException {
        BigDecimal money = new BigDecimal(moneyAmount);
        FindSpecification<User> specification = new FindUserBalanceSpecification(login);
        Finder<User> userFinder = new Finder<>();
        List<User> users = userFinder.findBySpecification(specification);
        if (users.isEmpty()) {
            logger.log(Level.WARN, "Not found users with login :" + login);
            return false;
        }
        User user = users.get(0);
        user.fillUpBalance(money);
        return user.getBalance().compareTo(MAX_USER_BALANCE) < 0;
    }

    /**
     * Method find all user complaint
     *
     * @return list of complaint
     * @throws ReceiverException throws when there are problems with the database
     * @see FindUserComplaintsSpecification,Finder
     */
    public List<Complaint> findUserComplaints(String login) throws ReceiverException {
        FindSpecification<Complaint> specification = new FindUserComplaintsSpecification(login);
        Finder<Complaint> complaintFinder = new Finder<>();
        return complaintFinder.findBySpecification(specification);
    }

    /**
     * Method check is user balance negative or empty(equel 0.00)
     *
     * @return true if negative or empty, else false
     * @throws ReceiverException throws when there are problems with the database
     * @see FindUserBalanceSpecification,Finder
     */
    public boolean isBalanceNegativeOrEmpty(String login) throws ReceiverException {
        FindSpecification<User> specification = new FindUserBalanceSpecification(login);
        Finder<User> userFinder = new Finder<>();
        List<User> users = userFinder.findBySpecification(specification);
        if (users.isEmpty()) {
            return false;
        }
        BigDecimal balance = users.get(0).getBalance();
        return balance.signum() <= 0;
    }

    /**
     * Method find user discount
     *
     * @return discount if user exist,else Optional.empty()
     * @throws ReceiverException throws when there are problems with the database
     * @see FindUserDiscountSpecification,Finder
     */
    public Optional<BigDecimal> findUserDiscount(String login) throws ReceiverException {
        FindSpecification<User> specification = new FindUserDiscountSpecification(login);
        Finder<User> userFinder = new Finder<>();
        List<User> users = userFinder.findBySpecification(specification);
        Optional<BigDecimal> discount = Optional.empty();
        if (!users.isEmpty()) {
            discount = Optional.of(users.get(0).getDiscount());
        }
        return discount;
    }

    /**
     * Method find user ride info
     *
     * @return User if user exist,else Optional.empty()
     * @throws ReceiverException throws when there are problems with the database
     * @see FindUserInfoForRideSpecification,Finder
     */
    public Optional<User> findUserInfoForRide(String login) throws ReceiverException {
        FindSpecification<User> specification = new FindUserInfoForRideSpecification(login);
        Finder<User> finder = new Finder<>();
        List<User> users = finder.findBySpecification(specification);
        Optional<User> user = Optional.empty();
        if (!users.isEmpty()) {
            user = Optional.ofNullable(users.get(0));
        }
        return user;
    }

    /**
     * Method update user profile: first name,last name phone number
     *
     * @throws ReceiverException throws when there are problems with the database
     * @see UpdateUserProfileSpecification,Repository,RepositoryController
     */
    public void updateUser(String login, String firstName, String lastName, String phoneNumber) throws ReceiverException {
        Repository<User> repository = new Repository<>();
        RepositoryController controller = new RepositoryController(repository);

        Specification userUpdateSpecification = new UpdateUserProfileSpecification(login, firstName, lastName, phoneNumber);
        try {
            repository.update(userUpdateSpecification);
            controller.end();
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
    }

    /**
     * Method change user password
     *
     * @throws ReceiverException throws when there are problems with the database
     * @see UpdateUserPasswordSpecification,Repository,RepositoryController
     */
    public void changePassword(String login, String password) throws ReceiverException {
        Repository<User> repository = new Repository<>();
        RepositoryController controller = new RepositoryController(repository);

        Specification userUpdateSpecification = new UpdateUserPasswordSpecification(login, password);
        try {
            repository.update(userUpdateSpecification);
            controller.end();
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
    }
    /**
     * Method switch user ban status
     *
     * @throws ReceiverException throws when there are problems with the database
     * @see UpdateUserBanStatusSpecification,Repository,RepositoryController
     */
    public void switchBan(String login, boolean isBanned) throws ReceiverException {
        Repository<User> repository = new Repository<>();
        RepositoryController controller = new RepositoryController(repository);
        try {
            Specification updateSpecification = new UpdateUserBanStatusSpecification(login, !isBanned);
            repository.update(updateSpecification);
            controller.end();
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }

    }
    /**
     * Method switch user ban status
     *
     * @throws ReceiverException throws when there are problems with the database
     * @see UpdateUserBanStatusSpecification,Repository,RepositoryController
     */
    public void switchAdminStatus(String login, UserType type, boolean isExDriver) throws ReceiverException {
        Repository repository = new Repository();
        RepositoryController controller = new RepositoryController(repository);

        try {
            if (type != UserType.ROOT_ADMIN) {
                if (type == UserType.USER || type == UserType.DRIVER) {
                    type = UserType.ADMIN;
                } else {
                    if (isExDriver) {
                        type = UserType.DRIVER;
                    } else {
                        type = UserType.USER;
                    }

                }
                Specification updateSpecification = new UpdateUserTypeSpecification(login, type);

                repository.update(updateSpecification);
            }
            controller.end();
        } catch (RepositoryException e) {
            throw new ReceiverException(e);
        }
    }
    /**
     * Method update user discount
     *
     * @throws ReceiverException throws when there are problems with the database
     * @see UpdateUserDiscountSpecification,Repository,RepositoryController
     */
    public void changeDiscount(BigDecimal discount, String login) throws ReceiverException {
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
    /**
     * Method find user by pattern
     *
     * @throws ReceiverException throws when there are problems with the database
     * @see FindUserByPatternSpecification,Finder
     */
    public List<User> findUsersByPattern(String findPattern) throws ReceiverException {
        FindSpecification<User> specification = new FindUserByPatternSpecification(findPattern);
        Finder<User> userFinder = new Finder<>();
        return userFinder.findBySpecification(specification);
    }
}