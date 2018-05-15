package by.zhuk.buber.specification.find.user;

import by.zhuk.buber.exeption.SpecificationException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.specification.find.FindSpecification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindUserBalanceSpecification implements FindSpecification<User> {
    private static final String SELECT_USER_BALANCE_BY_LOGIN = "SELECT user_balance FROM buber_db.user WHERE user_login=?";
    private String login;

    public FindUserBalanceSpecification(String login) {
        this.login = login;
    }

    @Override
    public String takePrepareQuery() {
        return SELECT_USER_BALANCE_BY_LOGIN;
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
                user.setBalance(resultSet.getBigDecimal(1));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
        return users;
    }
}