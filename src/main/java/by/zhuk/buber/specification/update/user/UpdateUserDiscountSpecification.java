package by.zhuk.buber.specification.update.user;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.specification.Specification;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Specification update user discount by login: user_discount=discount
 */
public class UpdateUserDiscountSpecification implements Specification {
    private static final String UPDATE_DISCOUNT_FIELD_BY_LOGIN = "UPDATE buber_db.user SET user_discount=? WHERE user_login=?";
    private String login;
    private BigDecimal discount;

    public UpdateUserDiscountSpecification(String login, BigDecimal discount) {
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
            statement.setBigDecimal(1, discount);
            statement.setString(2, login);
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }

    }
}