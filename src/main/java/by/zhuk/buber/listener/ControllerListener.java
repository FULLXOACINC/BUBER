package by.zhuk.buber.listener;

import by.zhuk.buber.connectionpool.ConnectionPool;
import by.zhuk.buber.mail.MailProperty;
import by.zhuk.buber.oauth.OAuthYandex;
import by.zhuk.buber.signuppool.SingUpPoolCleaner;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.InputStreamReader;

@WebListener
public class ControllerListener implements ServletContextListener {
    private static int SIGN_UP_CLEAR_TIME = 300;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool.getInstance();
        OAuthYandex.getInstance();
        MailProperty.getInstance();
        Thread cleaner = new Thread(new SingUpPoolCleaner(SIGN_UP_CLEAR_TIME));
        cleaner.setDaemon(true);
        cleaner.start();

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().destroyConnections();
    }
}