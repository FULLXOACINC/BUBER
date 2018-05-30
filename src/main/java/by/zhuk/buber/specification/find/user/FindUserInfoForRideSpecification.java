package by.zhuk.buber.specification.find.user;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.specification.find.FindSpecification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindUserInfoForRideSpecification implements FindSpecification<User> {
    private static final String SELECT_USER_RIDE_INFO_LOGIN = "SELECT user_name,user_second_name,user_phone_number FROM buber_db.user WHERE user_login=?";
    private String login;

    public FindUserInfoForRideSpecification(String login) {
        this.login = login;
    }

    @Override
    public String takePrepareQuery() {
        return SELECT_USER_RIDE_INFO_LOGIN;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {
        try {
            statement.setString(1, login);
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
                user.setLogin(login);
                user.setFirstName(resultSet.getString(1));
                user.setLastName(resultSet.getString(2));
                user.setPhoneNumber(resultSet.getString(3));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
        return users;
    }
}