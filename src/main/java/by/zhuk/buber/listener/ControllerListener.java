package by.zhuk.buber.listener;

import by.zhuk.buber.connectionpool.ConnectionPool;
import by.zhuk.buber.mail.MailProperty;
import by.zhuk.buber.oauth.OAuthYandex;
import by.zhuk.buber.signuppool.SingUpPoolCleaner;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ControllerListener implements ServletContextListener {
    private static int SIGN_UP_TIME = 300;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool.getInstance();
        OAuthYandex.getInstance();
        MailProperty.getInstance();
        Thread cleaner = new Thread(new SingUpPoolCleaner(SIGN_UP_TIME));
        cleaner.setDaemon(true);
        cleaner.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().destroyConnections();
    }
}