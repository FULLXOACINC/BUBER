package by.zhuk.buber.receiver;

import by.zhuk.buber.mail.MailProperty;
import by.zhuk.buber.mail.MailThread;
import by.zhuk.buber.model.User;
import by.zhuk.buber.userpool.RestorePasswordUserPool;
import by.zhuk.buber.userpool.UserPoolInfo;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.LocalTime;
import java.util.Formatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class RestorePasswordReceiver {
    private static Logger logger = LogManager.getLogger(RestorePasswordReceiver.class);
    private static final String HEAD = "head";
    private static final String CONTENT = "content";

    private static final String RESTORE_PASSWORD_MAIL_BUNDLE = "properties/restorePasswordMailContent";
    private static final String RESTORE_PASSWORD_DEFAULT_CONTENT = "To confirm the password recovery, please follow the link.<br/><a href=\"http://localhost:8080/controller?command=restore-password-accept&hash=%s\">Restore password</a>";
    private static final String RESTORE_PASSWORD_DEFAULT_HEAD = "BUBER restore password";

    private static final String PROPERTIES_EXTENSION = ".properties";


    public void sendRestorePasswordMail(String login, String password, String lang) {
        String hash = String.valueOf(login.concat(password).hashCode());
        Locale locale = new Locale(lang);

        String propsPath = this.getClass().getClassLoader().getResource(".").getPath();
        File file = new File(propsPath, RESTORE_PASSWORD_MAIL_BUNDLE + PROPERTIES_EXTENSION);
        String head;
        String content;
        if (file.exists()) {
            ResourceBundle bundle = ResourceBundle.getBundle(RESTORE_PASSWORD_MAIL_BUNDLE, locale);
            head = bundle.getString(HEAD);
            content = bundle.getString(CONTENT);
        } else {
            head = RESTORE_PASSWORD_DEFAULT_HEAD;
            content = RESTORE_PASSWORD_DEFAULT_CONTENT;
            logger.log(Level.WARN, "bundle not found + " + RESTORE_PASSWORD_MAIL_BUNDLE);
        }

        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);

        formatter.format(content, hash);


        MailThread thread = new MailThread(login, head, stringBuilder.toString(), MailProperty.getInstance().getProperties());
        thread.start();
        RestorePasswordUserPool pool = RestorePasswordUserPool.getInstance();
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        UserPoolInfo info = new UserPoolInfo(user, LocalTime.now());
        pool.putInfo(hash, info);
    }
}