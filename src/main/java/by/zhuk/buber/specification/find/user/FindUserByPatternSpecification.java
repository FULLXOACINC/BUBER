package by.zhuk.buber.specification.find.user;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.specification.find.FindSpecification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindUserByPatternSpecification implements FindSpecification<User> {
    private static final String SELECT_BY_PATTERN = "SELECT user_login FROM buber_db.user WHERE user_login LIKE ? OR user_first_name LIKE ? OR user_last_name LIKE ? OR user_phone_number LIKE ?";
    private String loginPattern;
    private String namePattern;
    private String secondNamePattern;
    private String phoneNumberPattern;

    public FindUserByPatternSpecification(String pattern) {
        pattern = "%" + pattern + "%";
        loginPattern = pattern;
        namePattern = pattern;
        secondNamePattern = pattern;
        phoneNumberPattern = pattern;
    }

    @Override
    public String takePrepareQuery() {
        return SELECT_BY_PATTERN;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {

        try {
            statement.setString(1, loginPattern);
            statement.setString(2, namePattern);
            statement.setString(3, secondNamePattern);
            statement.setString(4, phoneNumberPattern);
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
                users.add(user);
            }
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
        return users;
    }
}