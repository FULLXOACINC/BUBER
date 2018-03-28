package by.zhuk.buber.listener;

import by.zhuk.buber.constant.PagesConstant;
import by.zhuk.buber.model.Article;
import by.zhuk.buber.receiver.ArticleReceiver;

import javax.naming.ldap.PagedResultsControl;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Enumeration;
import java.util.List;

@WebListener
public class SessionListener implements HttpSessionListener {
    private static final String LANG = "lang";
    private static final String RU = "ru";
    private static final String LAST_PAGE = "lastPage";

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(LANG, RU);
        session.setAttribute(LAST_PAGE, PagesConstant.WELCOM_PAGE);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attribute = attributeNames.nextElement();
            session.removeAttribute(attribute);
        }
    }
}