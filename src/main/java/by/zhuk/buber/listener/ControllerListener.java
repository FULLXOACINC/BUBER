package by.zhuk.buber.listener;

import by.zhuk.buber.connectionpool.ConnectionPool;
import by.zhuk.buber.mail.MailProperty;
import by.zhuk.buber.oauth.OAuthYandex;
import by.zhuk.buber.singleton.SingUpPoolCleaner;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ControllerListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool.getInstance();
        OAuthYandex.getInstance();
        MailProperty.getInstance();
        Thread cleaner = new Thread(new SingUpPoolCleaner());
        cleaner.setDaemon(true);
        cleaner.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().destroyConnections();
    }
}