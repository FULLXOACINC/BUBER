package by.zhuk.buber.controller;

import by.zhuk.buber.connectionpool.ConnectionPool;
import by.zhuk.buber.mail.MailProperty;
import by.zhuk.buber.userpool.PoolCleaner;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
/**
 * WebListener ,processes init and destroy action :init conection pool,start pool cleaner
 */
@WebListener
public class ControllerListener implements ServletContextListener {
    private static int SIGN_UP_CLEAR_TIME = 300;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool.getInstance();
        MailProperty.getInstance();
        Thread cleaner = new Thread(new PoolCleaner(SIGN_UP_CLEAR_TIME));
        cleaner.setDaemon(true);
        cleaner.start();

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().destroyConnections();
    }
}