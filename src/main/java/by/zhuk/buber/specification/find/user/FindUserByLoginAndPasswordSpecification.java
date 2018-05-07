package by.zhuk.buber.specification.find.user;

import by.zhuk.buber.exeption.SpecificationException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.specification.find.FindSpecification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindUserByLoginAndPasswordSpecification implements FindSpecification<User> {
    private static final String SELECT_BY_LOGIN_AND_PASSWORD = "SELECT user_login,user_name FROM buber_db.user WHERE user_login=? AND user_password=SHA1(?)";
    private String login;
    private String password;

    public FindUserByLoginAndPasswordSpecification(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String takePrepareQuery() {
        return SELECT_BY_LOGIN_AND_PASSWORD;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {
        try {
            statement.setString(1, login);
            statement.setString(2, password);
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }

    }

    @Override
    public List<User> createEntities(ResultSet resultSet) throws SpecificationException {
        List<User> users = new ArrayList<>();
        try {
            while (resultSet.next()) {

                User user = new User();
                user.setLogin(resultSet.getString(1));
                user.setFirstName(resultSet.getString(2));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
        return users;
    }
}