package by.zhuk.buber.specification.impl;

import by.zhuk.buber.specification.SQLSpecification;

import java.util.ArrayList;
import java.util.List;

public class FindArticleByNameSpecification implements SQLSpecification {
    private List<Object> prepareParameters;
    private static final String QUERY ="SELECT name,intro,body,code_example FROM article.article WHERE name=?";

    public FindArticleByNameSpecification(String name) {
        prepareParameters = new ArrayList<>();
        prepareParameters.add(name);
    }

    @Override
    public String takePrepareQuery() {
        return QUERY;
    }

    @Override
    public List<Object> getPrepareParameters() {
        return new ArrayList<>(prepareParameters);
    }
}