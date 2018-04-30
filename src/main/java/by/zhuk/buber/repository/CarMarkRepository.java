package by.zhuk.buber.repository;

import by.zhuk.buber.exeption.RepositoryException;
import by.zhuk.buber.model.CarMark;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CarMarkRepository extends AbstractRepository<CarMark> {
    private static Logger logger = LogManager.getLogger(CarMarkRepository.class);
    private Connection connection;

    private static final String INSERT_CAR_MARK = "INSERT INTO buber_db.car_mark (car_mark_name) VALUES (?);";

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(CarMark carMark) throws RepositoryException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_CAR_MARK)) {

            statement.setString(1, carMark.getMarkName());

            statement.executeUpdate();

            logger.log(Level.INFO, carMark + " add successful");
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void update(CarMark carMark) throws RepositoryException {

    }

    @Override
    public void delete(CarMark carMark) throws RepositoryException {

    }

}