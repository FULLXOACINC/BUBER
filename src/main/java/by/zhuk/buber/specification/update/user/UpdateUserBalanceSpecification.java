package by.zhuk.buber.specification.update.user;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.specification.Specification;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Specification update user balance  by login: user_balance=balance
 */
public class UpdateUserBalanceSpecification implements Specification {
    private static final String UPDATE_BALANCE_BY_LOGIN = "UPDATE buber_db.user SET user_balance=? WHERE user_login=?";
    private String login;
    private BigDecimal balance;


    public UpdateUserBalanceSpecification(String login, BigDecimal balance) {
        this.login = login;
        this.balance = balance;
    }

    @Override
    public String takePrepareQuery() {
        return UPDATE_BALANCE_BY_LOGIN;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {
        try {
            statement.setBigDecimal(1, balance);
            statement.setString(2, login);
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }

    }
}