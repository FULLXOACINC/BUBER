package by.zhuk.buber.specification.update.user;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.specification.Specification;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateUserProfileSpecification implements Specification {
    private static final String UPDATE_USER_PROFILE_BY_LOGIN = "UPDATE `buber_db`.`user` SET user_name=?, user_second_name=?, user_phone_number=? WHERE user_login=?";
    private String login;
    private String firstName;
    private String lastName;
    private String phoneNumber;


    public UpdateUserProfileSpecification(String login, String firstName, String lastName, String phoneNumber) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String takePrepareQuery() {
        return UPDATE_USER_PROFILE_BY_LOGIN;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {
        try {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, phoneNumber);
            statement.setString(4, login);
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }

    }

}