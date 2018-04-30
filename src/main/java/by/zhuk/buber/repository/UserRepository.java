package by.zhuk.buber.repository;

import by.zhuk.buber.exeption.RepositoryException;
import by.zhuk.buber.model.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRepository extends AbstractRepository<User> {
    private static Logger logger = LogManager.getLogger(UserRepository.class);

    private static final String INSERT_USER = "INSERT INTO buber_db.user (user_login, user_name, user_second_name, user_password,user_type, user_balance, user_birth_dey, user_phone_number, user_is_ban,user_discount) VALUES ( ? , ? , ? , SHA1(?), ?, ?, ?, ?, '0',0.0)";
    private static final String UPDATE_USER = "UPDATE buber_db.user SET user_name=?, user_second_name=?, user_password=?, user_type=?, user_balance=?, user_birth_dey=?, user_phone_number=?, user_is_ban=?, user_discount=? WHERE user_login= ?";
    private static final String DELETE_USER = "DELETE FROM buber_db.user WHERE user_login= ?";

    @Override
    public void add(User user) throws RepositoryException {

        try (PreparedStatement statement = getConnection().prepareStatement(INSERT_USER)) {

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getType().name());
            statement.setBigDecimal(6, user.getBalance());
            statement.setDate(7, Date.valueOf(user.getBirthDay()));
            statement.setString(8, user.getPhoneNumber());

            statement.executeUpdate();

            logger.log(Level.INFO, user + " add successful");
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

    }

    @Override
    public void update(User user) throws RepositoryException {


        try (PreparedStatement statement = getConnection().prepareStatement(UPDATE_USER)) {

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getType().name());
            statement.setBigDecimal(5, user.getBalance());
            statement.setDate(6, Date.valueOf(user.getBirthDay()));
            statement.setString(7, user.getPhoneNumber());

            int isBanes = user.isBaned() ? 1 : 0;
            statement.setInt(8, isBanes);

            statement.setFloat(9, user.getDiscount());

            statement.setString(10, user.getLogin());
            statement.executeUpdate();
            logger.log(Level.INFO, user + " update successful");
        } catch (SQLException e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw new RepositoryException(e);
        }
    }

    @Override
    public void delete(User user) throws RepositoryException {
        String login = user.getLogin();

        try (PreparedStatement statement = getConnection().prepareStatement(DELETE_USER)) {

            statement.setString(1, login);
            statement.executeUpdate();
            logger.log(Level.INFO, user + " delete successful");
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

}