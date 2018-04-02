package by.zhuk.buber.specification.impl;

import by.zhuk.buber.specification.SQLSpecification;

import java.util.ArrayList;
import java.util.List;

public class FindUserByPhoneNumberSpecification implements SQLSpecification {
    private List<Object> prepareParameters;
    private static final String SELECT_BY_LOGIN = "SELECT user_login,user_name, user_second_name, user_password, user_type, user_balance, user_age, user_phone_number, user_is_ban FROM buber_db.user WHERE user_phone_number=?";

    public FindUserByPhoneNumberSpecification(String number) {
        prepareParameters = new ArrayList<>();
        prepareParameters.add(number);
    }

    @Override
    public String takePrepareQuery() {
        return SELECT_BY_LOGIN;
    }

    @Override
    public List<Object> getPrepareParameters() {
        return new ArrayList<>(prepareParameters);
    }
}