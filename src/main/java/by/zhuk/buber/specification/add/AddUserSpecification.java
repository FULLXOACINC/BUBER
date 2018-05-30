package by.zhuk.buber.specification.add;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.model.User;
import by.zhuk.buber.specification.Specification;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddUserSpecification implements Specification {

    private static final String INSERT_USER = "INSERT INTO buber_db.user (user_login, user_name, user_second_name, user_password,user_type, user_balance, user_birth_dey, user_phone_number, user_is_ban,user_discount) VALUES ( ? , ? , ? , SHA1(?), ?, ?, ?, ?, '0',0.0)";
    private User user;

    public AddUserSpecification(User user) {
        this.user = user;
    }

    @Override
    public String takePrepareQuery() {
        return INSERT_USER;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {
        try {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getType().name());
            statement.setBigDecimal(6, user.getBalance());
            statement.setDate(7, Date.valueOf(user.getBirthDay()));
            statement.setString(8, user.getPhoneNumber());
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
    }
}