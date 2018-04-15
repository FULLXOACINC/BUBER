package by.zhuk.buber.repository;

import by.zhuk.buber.connectionpool.ConnectionPool;
import by.zhuk.buber.exeption.RepositoryException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.model.UserType;
import by.zhuk.buber.specification.SQLSpecification;
import by.zhuk.buber.specification.Specification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Repository<User> {
    private static Logger logger = LogManager.getLogger(UserRepository.class);

    private static final String INSERT_USER = "INSERT INTO buber_db.user (user_login, user_name, user_second_name, user_password,user_type, user_balance, user_birth_dey, user_phone_number, user_is_ban) VALUES ( ? , ? , ? , SHA1(?), ?, ?, ?, ?, '0')";
    private static final String UPDATE_USER = "UPDATE buber_db.user SET user_name=?, user_second_name=?, user_password=?, user_type=?, user_balance=?, user_birth_dey=?, user_phone_number=?, user_is_ban=? WHERE user_login= ?";
    private static final String DELETE_USER = "DELETE FROM buber_db.user WHERE user_login= ?";

    @Override
    public void add(User user) throws RepositoryException {
        ConnectionPool pool = ConnectionPool.getInstance();

        try (Connection connection = pool.takeConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {

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
        ConnectionPool pool = ConnectionPool.getInstance();

        try (Connection connection = pool.takeConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {


            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getType().name());
            statement.setBigDecimal(5, user.getBalance());
            statement.setDate(6, Date.valueOf(user.getBirthDay()));
            statement.setString(7, user.getPhoneNumber());

            int isBanes = user.isBaned() ? 1 : 0;
            statement.setInt(8, isBanes);

            statement.setString(9, user.getLogin());
            statement.executeUpdate();
            logger.log(Level.INFO, user + " update successful");
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void delete(User user) throws RepositoryException {
        String login = user.getLogin();
        ConnectionPool pool = ConnectionPool.getInstance();

        try (Connection connection = pool.takeConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {

            statement.setString(1, login);
            statement.executeUpdate();
            logger.log(Level.INFO, user + " delete successful");
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<User> find(Specification specification) throws RepositoryException {
        SQLSpecification sqlSpecification = (SQLSpecification) specification;


        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().takeConnection();
             PreparedStatement statement = connection.prepareStatement(sqlSpecification.takePrepareQuery())) {
            List<Object> params = sqlSpecification.getPrepareParameters();

            for (int index = 1; index <= params.size(); index++) {
                statement.setObject(index, params.get(index - 1));
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setLogin(resultSet.getString(1));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setPassword(resultSet.getString(4));

                UserType type = UserType.valueOf(resultSet.getString(5).toUpperCase());
                user.setType(type);
                user.setBalance(resultSet.getBigDecimal(6));
                user.setBirthDay(resultSet.getDate(7).toLocalDate());
                user.setPhoneNumber(resultSet.getString(8));
                boolean isBaned = resultSet.getBoolean(9);
                user.setBaned(isBaned);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return users;
    }
}