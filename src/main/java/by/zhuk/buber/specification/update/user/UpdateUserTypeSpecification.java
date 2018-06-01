package by.zhuk.buber.specification.update.user;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.model.UserType;
import by.zhuk.buber.specification.Specification;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateUserTypeSpecification implements Specification {
    private static final String UPDATE_USER_TYPE_FIELD_BY_LOGIN = "UPDATE buber_db.user SET user_type=? WHERE user_login=?";
    private UserType type;
    private String login;

    public UpdateUserTypeSpecification(String login, UserType type) {
        this.type = type;
        this.login = login;
    }

    @Override
    public String takePrepareQuery() {
        return UPDATE_USER_TYPE_FIELD_BY_LOGIN;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {
        try {
            statement.setString(1, type.name());
            statement.setString(2, login);
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }

    }
}