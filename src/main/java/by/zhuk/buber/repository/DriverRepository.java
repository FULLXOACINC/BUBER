package by.zhuk.buber.repository;

import by.zhuk.buber.exeption.RepositoryException;
import by.zhuk.buber.model.Driver;
import by.zhuk.buber.specification.Specification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DriverRepository implements TransactionRepository<Driver> {

    private static Logger logger = LogManager.getLogger(DriverRepository.class);
    private Connection connection;

    private static final String INSERT_DRIVER = "INSERT INTO buber_db.driver (driver_login, driver_car_number, driver_document_id, driver_car_mark, driver_current_lat_coordinate, driver_current_lng_coordinate, driver_is_working,driver_achieve_fast_and_furious, driver_achieve_narrator, driver_achieve_clear_car ,driver_achieve_bad) VALUES (?, ?, ? , ?, 54.238991, 35.238991, 0, 0, 0, 0, 0);";
    private static final String UPDATE_DRIVER = "UPDATE buber_db.driver SET user_name=?, user_second_name=?, user_password=?, user_type=?, user_balance=?, user_birth_dey=?, user_phone_number=?, user_is_ban=? WHERE user_login= ?";
    private static final String DELETE_DRIVER = "DELETE FROM buber_db.driver WHERE driver_login= ?";

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Driver driver) throws RepositoryException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_DRIVER)) {

            statement.setString(1, driver.getLogin());
            statement.setString(2, driver.getCarNumber());
            statement.setString(3, driver.getDocumentId());
            statement.setInt(4, Integer.parseInt(driver.getCarMark()));

            statement.executeUpdate();

            logger.log(Level.INFO, driver + " add successful");
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void update(Driver driver) throws RepositoryException {

    }

    @Override
    public void delete(Driver driver) throws RepositoryException {

    }

    @Override
    public List<Driver> find(Specification specification) throws RepositoryException {
        return null;
    }


}