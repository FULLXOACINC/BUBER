package by.zhuk.buber.specification.impl;

import by.zhuk.buber.specification.SQLSpecification;

import java.util.ArrayList;
import java.util.List;

public class FindCarMarkByName implements SQLSpecification {
    private List<Object> prepareParameters;
    private static final String SELECT_BY_CAR_NAME = "SELECT car_mark_id,car_mark_name FROM buber_db.car_mark WHERE car_mark_name=?";

    public FindCarMarkByName(String carMarkName) {
        prepareParameters = new ArrayList<>();
        prepareParameters.add(carMarkName);
    }


    @Override
    public String takePrepareQuery() {
        return SELECT_BY_CAR_NAME;
    }

    @Override
    public List<Object> getPrepareParameters() {
        return new ArrayList<>(prepareParameters);
    }
}