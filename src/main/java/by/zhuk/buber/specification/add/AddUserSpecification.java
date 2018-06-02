package by.zhuk.buber.specification.add;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.model.UserType;
import by.zhuk.buber.specification.Specification;
import sun.util.resources.LocaleData;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddUserSpecification implements Specification {

    private static final String INSERT_USER = "INSERT INTO buber_db.user (user_login, user_name, user_second_name, user_password,user_type, user_balance, user_birth_dey, user_phone_number, user_is_ban,user_discount) VALUES ( ? , ? , ? , SHA1(?), ?, ?, ?, ?, '0',0.0)";
    private User user;
    private String login;
    private String firstName;
    private String lastName;
    private String password;
    private UserType type;
    private BigDecimal balance;
    private LocalDate birthDay;
    private String phoneNumber;

    public AddUserSpecification(User user) {
        this.user = user;
    }

    public AddUserSpecification(String login, String firstName, String lastName, String password, UserType type, BigDecimal balance, LocalDate birthDay, String phoneNumber) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.type = type;
        this.balance = balance;
        this.birthDay = birthDay;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String takePrepareQuery() {
        return INSERT_USER;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {
        try {
            statement.setString(1, login);
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            statement.setString(4, password);
            statement.setString(5, type.name());
            statement.setBigDecimal(6, balance);
            statement.setDate(7, Date.valueOf(birthDay));
            statement.setString(8, phoneNumber);
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
    }
}