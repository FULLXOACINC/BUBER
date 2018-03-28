package by.zhuk.buber.specification.impl;

import by.zhuk.buber.specification.SQLSpecification;

import java.util.ArrayList;
import java.util.List;

public class FindArticlesByFirstSymbolSpecification implements SQLSpecification{
    private List<Object> prepareParameters;
    private static final String QUERY ="SELECT name,intro,body,code_example FROM article.article WHERE name LIKE ?";

    public FindArticlesByFirstSymbolSpecification(String firstSymbols) {
        prepareParameters = new ArrayList<>();
        prepareParameters.add(firstSymbols + "%");
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