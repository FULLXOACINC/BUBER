package by.zhuk.buber.specification.update;

import by.zhuk.buber.exeption.SpecificationException;
import by.zhuk.buber.specification.Specification;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateDriverEarnedMoneySpecification implements Specification {
    private static final String UPDATE_EARNED_BY_LOGIN = "UPDATE buber_db.driver SET driver_earned_money=? WHERE driver_login=?";
    private String login;
    private BigDecimal earnedMoney;


    public UpdateDriverEarnedMoneySpecification(String login, BigDecimal earnedMoney) {
        this.login = login;
        this.earnedMoney = earnedMoney;
    }

    @Override
    public String takePrepareQuery() {
        return UPDATE_EARNED_BY_LOGIN;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {
        try {
            statement.setBigDecimal(1, earnedMoney);
            statement.setString(2, login);
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }

    }
}