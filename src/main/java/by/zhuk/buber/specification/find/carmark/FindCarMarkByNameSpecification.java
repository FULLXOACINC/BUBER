package by.zhuk.buber.specification.find.carmark;

import by.zhuk.buber.exception.SpecificationException;
import by.zhuk.buber.model.CarMark;
import by.zhuk.buber.specification.find.FindSpecification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * FindSpecification<CarMark> find car_mark_id,car_mark_name by car_mark_name
 */
public class FindCarMarkByNameSpecification implements FindSpecification<CarMark> {
    private static final String SELECT_BY_CAR_NAME = "SELECT car_mark_id,car_mark_name FROM buber_db.car_mark WHERE car_mark_name=?";
    private String carMarkName;

    public FindCarMarkByNameSpecification(String carMarkName) {
        this.carMarkName = carMarkName;
    }

    @Override
    public String takePrepareQuery() {
        return SELECT_BY_CAR_NAME;
    }

    @Override
    public void setupPreparedStatement(PreparedStatement statement) throws SpecificationException {
        try {
            statement.setString(1, carMarkName);
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
    }

    @Override
    public List<CarMark> createEntities(ResultSet resultSet) throws SpecificationException {
        List<CarMark> carMarks = new ArrayList<>();
        try {
            while (resultSet.next()) {
                CarMark carMark = new CarMark();
                carMark.setIndex(resultSet.getInt(1));
                carMark.setMarkName(resultSet.getString(2));
                carMarks.add(carMark);
            }
        } catch (SQLException e) {
            throw new SpecificationException(e);
        }
        return carMarks;
    }
}