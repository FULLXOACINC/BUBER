package by.zhuk.buber.specification.impl;

import by.zhuk.buber.exeption.SpecificationException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.specification.Specification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindUserByPhoneNumberSpecification implements Specification<User> {
    private static final String SELECT_BY_LOGIN = "SELECT user_login FROM buber_db.user WHERE user_phone_number=?";
    private String phoneNumber;
    public FindUserByPhoneNumberSpecification(String phoneNumber) {
        this.phoneNumber=phoneNumber;
    }

    @Override
    public String takePrepareQuery() {
        return SELECT_BY_LOGIN;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {

        try {
            statement.setString(1, phoneNumber);
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