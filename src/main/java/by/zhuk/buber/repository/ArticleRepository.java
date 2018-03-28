package by.zhuk.buber.repository;

import by.zhuk.buber.connectionpool.ConnectionPool;
import by.zhuk.buber.model.Article;
import by.zhuk.buber.specification.SQLSpecification;
import by.zhuk.buber.specification.Specification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleRepository implements Repository<Article> {
    private static Logger logger = LogManager.getLogger(ArticleRepository.class);

    private static final String INSERT_ARTICLE = "INSERT INTO `article`.`article` (`name`, `intro`, `body`, `code_example`) VALUES (?, ?, ?,?);";
    private static final String UPDATE_ARTICLE = "UPDATE `article`.`article` SET `intro`=?, `body`=?, `code_example`=? WHERE `name`= ?";
    private static final String DELETE_ARTICLE = "DELETE FROM `article`.`article` WHERE `name`=?";

    @Override
    public void add(Article article) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.takeConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_ARTICLE);
            statement.setString(1, article.getName());
            statement.setString(2, article.getIntro());
            statement.setString(3, article.getBody());
            statement.setString(4, article.getCodeExample());
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            logger.catching(e);
        }

    }

    @Override
    public void update(Article article) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.takeConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_ARTICLE);

            statement.setString(1, article.getIntro());
            statement.setString(2, article.getBody());
            statement.setString(3, article.getCodeExample());
            statement.setString(4, article.getName());
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.catching(e);
        }
    }

    @Override
    public void delete(Article article) {
        String name = article.getName();
        ConnectionPool pool = ConnectionPool.getInstance();

        try (Connection connection = pool.takeConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ARTICLE)) {
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.catching(e);
        }
    }

    @Override
    public List<Article> find(Specification specification) {
        SQLSpecification sqlSpecification = (SQLSpecification) specification;


        Connection connection = ConnectionPool.getInstance().takeConnection();
        List<Article> articles = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sqlSpecification.takePrepareQuery());
            List<Object> params = sqlSpecification.getPrepareParameters();

            for (int index = 1; index <= params.size(); index++) {
                statement.setObject(index, params.get(index - 1));
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Article article = new Article();
                article.setName(resultSet.getString(1));
                article.setIntro(resultSet.getString(2));
                article.setBody(resultSet.getString(3));
                article.setCodeExample(resultSet.getString(4));
                articles.add(article);
            }
            connection.close();
        } catch (SQLException e) {
            logger.catching(e);
        }
        return articles;
    }
}