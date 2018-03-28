package by.zhuk.buber.receiver;

import by.zhuk.buber.model.Article;
import by.zhuk.buber.repository.ArticleRepository;
import by.zhuk.buber.repository.Repository;
import by.zhuk.buber.specification.Specification;
import by.zhuk.buber.specification.impl.FindArticleByNameSpecification;
import by.zhuk.buber.specification.impl.FindArticlesByFirstSymbolSpecification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArticleReceiver {
    private static Repository<Article> repository = new ArticleRepository();

    public void saveArticle(String name, String intro, String body, String code) {
        Article article = new Article(name, intro, body, code);
        repository.add(article);
    }

    public List<String> findArticlesName() {
        List<String> articlesName = new ArrayList<>();


        Specification specification = new FindArticlesByFirstSymbolSpecification("");
        List<Article> articles = repository.find(specification);
        for (Article article : articles) {
            articlesName.add(article.getName());
        }

        Collections.sort(articlesName);
        return articlesName;
    }

    public Article findArticle(String articleName) {
        Specification specification = new FindArticleByNameSpecification(articleName);
        List<Article> articles = repository.find(specification);
        if(!articles.isEmpty()){
            return articles.get(0);
        }else {
            return new Article("","","","");
        }
    }

    public void delete(String name) {
        Article article = new Article();
        article.setName(name);
        repository.delete(article);
    }

    public Article findFirstArticle() {
        return findArticle(findArticlesName().get(0));
    }

    public Article update(String name, String intro, String body, String code) {
        Article article = new Article(name, intro, body, code);
        repository.update(article);
        return article;
    }
}