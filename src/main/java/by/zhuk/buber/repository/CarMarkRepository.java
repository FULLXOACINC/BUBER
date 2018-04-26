package by.zhuk.buber.repository;

import by.zhuk.buber.connectionpool.ConnectionPool;
import by.zhuk.buber.exeption.RepositoryException;
import by.zhuk.buber.model.CarMark;
import by.zhuk.buber.model.User;
import by.zhuk.buber.model.UserType;
import by.zhuk.buber.specification.SQLSpecification;
import by.zhuk.buber.specification.Specification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarMarkRepository implements Repository<CarMark> {
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

    @Override
    public List<CarMark> find(Specification specification) throws RepositoryException {
        SQLSpecification sqlSpecification = (SQLSpecification) specification;

        List<CarMark> carMarks = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sqlSpecification.takePrepareQuery())) {
            List<Object> params = sqlSpecification.getPrepareParameters();

            for (int index = 1; index <= params.size(); index++) {
                statement.setObject(index, params.get(index - 1));
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CarMark carMark = new CarMark();
                carMark.setIndex(resultSet.getInt(1));
                carMark.setMarkName(resultSet.getString(2));
                carMarks.add(carMark);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return carMarks;
    }
}