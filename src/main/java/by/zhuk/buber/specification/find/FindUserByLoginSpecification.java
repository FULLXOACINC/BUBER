package by.zhuk.buber.specification.find;

import by.zhuk.buber.exeption.SpecificationException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.model.UserType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindUserByLoginSpecification implements FindSpecification<User> {
    private static final String SELECT_BY_LOGIN = "SELECT user_name, user_second_name, user_password, user_type, user_balance, user_birth_dey, user_phone_number, user_is_ban, user_discount FROM buber_db.user WHERE user_login=?";
    private String login;

    public FindUserByLoginSpecification(String login) {
        this.login = login;
    }

    @Override
    public String takePrepareQuery() {
        return SELECT_BY_LOGIN;
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
                user.setPassword(resultSet.getString(3));
                UserType type = UserType.valueOf(resultSet.getString(4).toUpperCase());
                user.setType(type);
                user.setBalance(resultSet.getBigDecimal(5));
                user.setBirthDay(resultSet.getDate(6).toLocalDate());
                user.setPhoneNumber(resultSet.getString(7));
                boolean isBaned = resultSet.getBoolean(8);
                user.setBaned(isBaned);
                user.setDiscount(resultSet.getFloat(9));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
        return users;
    }
}