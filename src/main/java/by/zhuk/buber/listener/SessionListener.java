package by.zhuk.buber.listener;

import by.zhuk.buber.constant.PagesConstant;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Enumeration;

@WebListener
public class SessionListener implements HttpSessionListener {
    private static final String LANG = "lang";
    private static final String RU = "ru";
    private static final String LAST_PAGE = "lastPage";

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(LANG, RU);
        session.setAttribute(LAST_PAGE, PagesConstant.WELCOME_PAGE);
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