package by.zhuk.buber.specification.update;

import by.zhuk.buber.exeption.SpecificationException;
import by.zhuk.buber.specification.Specification;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateUserDiscountSpecification implements Specification {
    private static final String UPDATE_DISCOUNT_FIELD_BY_LOGIN = "UPDATE buber_db.user SET user_discount=? WHERE user_login=?";
    private String login;
    private float discount;

    public UpdateUserDiscountSpecification(String login, float discount) {
        this.login = login;
        this.discount = discount;
    }

    @Override
    public String takePrepareQuery() {
        return UPDATE_DISCOUNT_FIELD_BY_LOGIN;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {
        try {
            statement.setFloat(1, discount);
            statement.setString(2, login);
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }

    }
}