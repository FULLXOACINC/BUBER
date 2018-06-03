package by.zhuk.buber.specification.update.user;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.specification.Specification;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Specification change user password  by login: user_password=SHA1(password)
 */
public class UpdateUserPasswordSpecification implements Specification {
    private static final String UPDATE_DISCOUNT_FIELD_BY_LOGIN = "UPDATE buber_db.user SET user_password=SHA1(?) WHERE user_login=?";
    private String login;
    private String password;

    public UpdateUserPasswordSpecification(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String takePrepareQuery() {
        return UPDATE_DISCOUNT_FIELD_BY_LOGIN;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {
        try {
            statement.setString(1, password);
            statement.setString(2, login);
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }

    }
}