package by.zhuk.buber.specification.update.user;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.specification.Specification;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateUserBanStatusSpecification implements Specification {
    private static final String UPDATE_BAN_FIELD_BY_LOGIN = "UPDATE buber_db.user SET user_is_ban=? WHERE user_login=?";
    private String login;
    private boolean isBan;

    public UpdateUserBanStatusSpecification(String login, boolean isBan) {
        this.login = login;
        this.isBan = isBan;
    }

    @Override
    public String takePrepareQuery() {
        return UPDATE_BAN_FIELD_BY_LOGIN;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {
        try {
            int isBaned = isBan ? 1 : 0;
            statement.setInt(1, isBaned);
            statement.setString(2, login);
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }

    }
}